require('dotenv').config()
const mysql = require('mysql')
const express = require('express')
const cookieSession = require('cookie-session');
const bodyParser = require('body-parser');
const crypto = require('crypto');
const cors = require('./cors')
const auth = require('./auth')
const apiProxy = require('./proxy')
const inject = require('./uidInject')

const DB = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database:process.env.DB_NAME
})

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
server.all('*',cors)

//提交登录
server.post('/login',(req,res)=>{
  const username = req.body.username;
  const password = req.body.password;
  console.log('login',{username:username,password:password});
  DB.query(`select * from user,user_info where username='${username}' and user.id=user_info.uid`,(err,data)=>{
    if(err) {
      console.log(err);
      res.json({code:500,msg:"服务器错误"}).end();
    } else {
      if(data.length!=1 || hash(password)!=data[0].password)  {
        console.log(data)
        console.log(hash(password))
        //console.log(hash(data[0].password),password)
        res.json({code:403,msg:"用户名或者密码错误"}).end();
      } else {
        req.session['uid'] = data[0].id;
        req.session['role_id'] = data[0].role_id;
        console.log('login success,uid:',data[0].uid,'role_id:',data[0].role_id);
        res.json({code:200}).end();
      }
    }
  })
});

//退出
server.post('/logout',(req,res)=>{
  req.session['uid']=null;
  req.session['role_id'] = null;
  res.json({code:200}).end();
});

//AuthFilter
server.all('*',auth)
//uid inject
server.all('*',inject)
//reverse Proxy
server.use('/api', apiProxy);

//error api
server.all('*',(req,res)=>{res.json({code:404,msg:'uknown api'}).end()})
server.listen(process.env.PORT)

