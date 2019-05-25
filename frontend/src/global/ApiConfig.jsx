const ServerDomain = "http://localhost:8080";

const API = {
    login:{
        method:'post',
        url:ServerDomain+'/user/login'
    },
    //基本信息管理
    //获得全部的科室信息
    getDepartmentInfo:{
        method:'get',
        url:ServerDomain+'/departmentManage/getAll'
    },
    //添加科室
    addDepartmentInfo:{
        method:'post',
        url:ServerDomain+'/departmentManage/add'
    },
    //删除科室
    deleteDepartmentInfo:{
        method:'post',
        url:ServerDomain+'/departmentManage/delete'
    },
    //更新科室
    updateDepartmentInfo:{
        method:'post',
        url:ServerDomain+'/departmentManage/update'
    },
    //添加用户
    addUserInfo:{
        method:'post',
        url:ServerDomain+'/userManagement/add'
    },
    deleteUserInfo:{
        method:'post',
        url:ServerDomain+'/userManagement/delete'
    },
    updateUserInfo:{
        method:'post',
        url:ServerDomain+'/userManagement/update'
    },
    getAllUserInfo:{
        method:'get',
        url:ServerDomain+'/userManagement/all'
    }
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