const express = require('express')
const auth = express.Router()

const Roles = {
  HospitialAdmin:1,
  RegisteredTollCollector:2,
  FinancialAdmin:3,
  PharmacyOperator:4,
  OutpatientDoctor:5,
  DoctorOfTechnology:6
}


//session验证
auth.use('/api',(req,res,next)=>{
  if(req.session['uid']==null) 
    res.json({code:403,msg:"登录已过期"}).end();
  else next();
});

//角色过滤
auth.use('/api/hospitalAdmin/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.HospitialAdmin)
    res.json({code:403}).end();
  else next();
});

auth.use('/api/registeredTollCollector/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.RegisteredTollCollector)
    res.json({code:403}).end();
  else next();
});

auth.use('/api/outpatientDoctor/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.OutpatientDoctor)
    res.json({code:403}).end();
  else next();
});

auth.use('/api/doctorOfTechnology/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.DoctorOfTechnology)
    res.json({code:403}).end();
  else next();
});

auth.use('/api/pharmacyOperator/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.PharmacyOperator)
    res.json({code:403}).end();
  else next();
});

auth.use('/api/financialAdmin/',(req,res,next)=>{
  if(res.session['role_id']!=Roles.FinancialAdmin)
    res.json({code:403}).end();
  else next();
});

//带有uid的检测，未避免歧义，用来跟踪poster的uid为_uid
auth.use('/api',(req,res,next)=>{
  const _uid = req.body._uid;
  if(_uid) {
    if(_uid!==req.session['_uid']) 
        res.json({code:403,msg:"你没有此权限！"}).end();
    else next();
  }
});

module.exports = auth