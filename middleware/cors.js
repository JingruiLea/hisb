module.exports = (req,res,next)=>{
  res.header("Access-Control-Allow-Origin","http://localhost:3000");
  res.header("Access-Control-Allow-Headers","content-type,x-requested-with"); 
  res.header('Access-Control-Allow-Credentials','true');
  res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
  next();
};