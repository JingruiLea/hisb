const ServerDomain = "http://localhost:8080";

const API = {
    login:{
        method:'post',
        url:ServerDomain+'/user/login'
    },
    //基本信息管理
    bacisInfoManagement:{
            //科室管理
        department:{
            all:{//获得全部的科室信息
                method:'get', url:ServerDomain+'/departmentManage/getAll'
            },
            add:{//添加科室
                method:'post',url:ServerDomain+'/departmentManage/add'
            },
            delete:{//删除科室
                method:'post',url:ServerDomain+'/departmentManage/delete'
            },
            update:{//更新科室
                method:'post',url:ServerDomain+'/departmentManage/update'
            },
        }, //用户管理
        user:{
            add:{ //添加用户
                method:'post',url:ServerDomain+'/userManagement/add'
            },
            delete:{ //删除用户
                method:'post',url:ServerDomain+'/userManagement/delete'
            },
            update:{ //更新用户
                method:'post',url:ServerDomain+'/userManagement/update'
            },
            all:{ //获取全部用户
                method:'get', url:ServerDomain+'/userManagement/all'
            },
        }, //结算方式管理
        settlement: {
            all:{ //获取结算方式
                method:'get', url:ServerDomain+'/settlementCategoryManagement/all'
            },
            add:{ //增加结算方式
                method:'post',url:ServerDomain+'/settlementCategoryManagement/add'
            },
            delete:{ //删除结算方式
                method:'post',url:ServerDomain+'/settlementCategoryManagement/delete'
            },
            update:{ //修改结算方式
                method:'post',url:ServerDomain+'/settlementCategoryManagement/update'
            },
        },//挂号等级管理
        registrationLevel: {
            all:{  //获取所有挂号等级
                method:'get', url:ServerDomain+'/registrationLevelManagement/all'
            },
            add:{  //增加挂号等级
                method:'post',url:ServerDomain+'/registrationLevelManagement/add'
            },
            delete:{  //删除挂号等级
                method:'post',url:ServerDomain+'/registrationLevelManagement/delete'
            },
            update:{ //修改挂号等级
                method:'post',url:ServerDomain+'/registrationLevelManagement/update'
            },
        },//非药品项目管理
        nonDrugItem: {
            all:{ //获得所有的非药品项目
                method:'get', url:ServerDomain+'/nonDrugChargeItemManagement/all'
            },
            add:{  //增加非药品项目
                method:'post',url:ServerDomain+'/nonDrugChargeItemManagement/add'
            },
            delete:{  //删除非药品项目
                method:'post',url:ServerDomain+'/nonDrugChargeItemManagement/delete'
            },
            update:{ //修改非药品项目
                method:'post',url:ServerDomain+'/nonDrugChargeItemManagement/update'
            },
        }
    },
    //门诊挂号
    outpatientWorkstation:{
        registration:{
            //同步医生信息
            syncDoctorList:{
                method:'post',
                url:ServerDomain+'/registration/syncDoctorList'
            },
            //计算费用
            calculateFee:{
                method:'post',
                url:ServerDomain+'/registration/calculateFee'
            }
        },
        
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