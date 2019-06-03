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

const proxyOptions = {
  target: 'http://localhost:8080', // 目标主机
  changeOrigin: true,               // 需要虚拟主机站点
  ws:true,
  pathRewrite: {
    '^/api' : '/'
  }
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

const hash=()=>{
  const obj=crypto.createHash('sha256'); 
  obj.update('jinitaimei');
  const str=obj.digest('hex');
  return str;
}

const server = express();
server.use(cookieSession({
  name:'session',
  keys:['boy ♂ next ♂ door','thank ♂ you ♂ sir','deep ♂ dark ♂ fantasy'],
  maxAge:400*60*100
}));

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

server.use('/api', apiProxy);//对地址为/api的请求全部转发







server.use(bodyParser.json({limit: '50MB'}));
server.use(bodyParser.urlencoded({limit: '50MB', extended: true}));
server.all("*",function(req,res,next){
  res.header("Access-Control-Allow-Origin","*");
  res.header("Access-Control-Allow-Headers","content-type,x-requested-with"); 
  res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
  next();
});

server.post('/login',(req,res)=>{
  const username = req.body.username;
  const password = req.body.password;
  DB.query(`select password from user where username='${username}'`,(err,data)=>{
    if(err) {
      console.log(err);
      res.json({code:500,msg:"服务器错误"}).end();
    } else {
      if(data.length!=1 || hash(data[0])!=password)  
        res.json({code:403,msg:"用户名或者密码错误"}).end();
      else {
        req.session['uid'] = data[0].id;
        res.json({code:200}).end();
      }
    }
  })
});

server.listen(8081)