const ServerDomain = "http://localhost:8080";

const API = {
    login:ServerDomain+'/user/login'
}

export default API;


/*
POST /user/login
REQ username:string, password:string remember:bool
RES errMsg:str code:int

POST /user/info
REQ EMPTY
RES errMsg:str code:int data:json

POST /user/updateInfo
REQ data:json
RES errMsg:str code:int data:json

POST /user/resetPwd
REQ password:string
RES errMsg:str code:int data:json



POST /admin/listOffices
REQ 
RES  errMsg:str code:int data:json


*/