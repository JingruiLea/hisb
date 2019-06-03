require('dotenv').config()
const mysql = require('mysql')
const express = require('express')
const cookieSession = require('cookie-session');
const bodyParser = require('body-parser');
const crypto = require('crypto');
const proxy = require('http-proxy-middleware');

const DB = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database:process.env.DB_NAME
})

var restream = (proxyReq, req, res, options)=>{
  //除非是文件上传 其余都要重新设置json
  if (!req.url.endsWith('import') && !req.url.endsWith('upload') && req.body) {
      let bodyData = JSON.stringify(req.body);
      // incase if content-type is application/x-www-form-urlencoded -> we need to change to application/json
      proxyReq.setHeader('Content-Type','application/json');
      proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
      // stream the content
      proxyReq.write(bodyData);
  }
}

const proxyOptions = {
  target: process.env.API_SERVER, // 目标主机
  changeOrigin: true,               // 需要虚拟主机站点
  ws:true,
  pathRewrite: {
    '^/api' : '/'
  },
  onProxyReq:restream,
};
const apiProxy = proxy(proxyOptions);  //开启代理功能，并加载配置

const Roles = {
  HospitialAdmin:1,
  RegisteredTollCollector:2,
  OutpatientDoctor:3,
  DoctorOfTechnology:4,
  PharmacyOperator:5,
  FinancialAdmin:6,
}

const hash=(pwd)=>{
  const obj=crypto.createHash('md5'); 
  obj.update(pwd);
  const str=obj.digest('hex');
  return str;
}

const server = express();
server.use(cookieSession({
  name:'session',
  keys:['boy ♂ next ♂ door','thank ♂ you ♂ sir','deep ♂ dark ♂ fantasy'],
  maxAge:400*60*100
}));

server.use(bodyParser.json({limit: '50MB'}));
server.use(bodyParser.urlencoded({limit: '50MB', extended: true}));

//cors
server.all("*",function(req,res,next){
  res.header("Access-Control-Allow-Origin","*");
  res.header("Access-Control-Allow-Headers","content-type,x-requested-with"); 
  res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
  next();
});
/*
//6大角色路由过滤
server.use('/api',(req,res,next)=>{
  if(req.session['uid']==null) 
    res.json({code:403,msg:"登录已过期"}).end();
  else next();
});

server.use('/api/hospitalAdmin/',(req,res,next)=>{
  if(res.session['role']!=Roles.HospitialAdmin)
    res.json({code:403}).end();
  else next();
});

server.use('/api/registeredTollCollector/',(req,res,next)=>{
  if(res.session['role']!=Roles.RegisteredTollCollector)
    res.json({code:403}).end();
  else next();
});

server.use('/api/outpatientDoctor/',(req,res,next)=>{
  if(res.session['role']!=Roles.OutpatientDoctor)
    res.json({code:403}).end();
  else next();
});

server.use('/api/doctorOfTechnology/',(req,res,next)=>{
  if(res.session['role']!=Roles.DoctorOfTechnology)
    res.json({code:403}).end();
  else next();
});

server.use('/api/pharmacyOperator/',(req,res,next)=>{
  if(res.session['role']!=Roles.PharmacyOperator)
    res.json({code:403}).end();
  else next();
});

server.use('/api/financialAdmin/',(req,res,next)=>{
  if(res.session['role']!=Roles.FinancialAdmin)
    res.json({code:403}).end();
  else next();
});

//带有uid的检测，未避免歧义，用来跟踪poster的uid为_uid
server.use('/api',(req,res,next)=>{
  const _uid = req.body._uid;
  if(_uid) {
    if(_uid!==req.session['_uid']) 
        res.json({code:403,msg:"你没有此权限！"}).end();
    else next();
  }
});
*/
server.use('/api', apiProxy);//对地址为/api的请求全部转发
//server.use('/',(req,res,next)=>{req.body._uid = 22;next();})

/******************************* 最末层 不受proxy影响 *********************************** */

//提交登录
server.post('/login',(req,res)=>{
  const username = req.body.username;
  const password = req.body.password;
  console.log('login',{username:username,password:password});
  DB.query(`select password from user where username='${username}'`,(err,data)=>{
    if(err) {
      console.log(err);
      res.json({code:500,msg:"服务器错误"}).end();
    } else {
      if(data.length!=1 || hash(password)!=data[0].password)  {
        //console.log(hash(data[0].password),password)
        res.json({code:403,msg:"用户名或者密码错误"}).end();
      } else {
        req.session['uid'] = data[0].id;
        res.json({code:200}).end();
      }
    }
  })
});

server.listen(process.env.PORT)