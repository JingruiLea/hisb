const server = 'http://localhost:8081'
const apiServerPrefix = server+'/api'

const API = {
    //登录
    login:{method:'post',url:server+'/login'},
    //同步个人信息
    info:{ method:'post',url:apiServerPrefix+'/user/info'},
    //基本信息管理
    bacisInfoManagement:{
            //科室管理
        department:{
            all:{//获得全部的科室信息
                method:'get', url:apiServerPrefix+'/departmentManage/getAll'
            },
            add:{//添加科室
                method:'post',url:apiServerPrefix+'/departmentManage/add'
            },
            delete:{//删除科室
                method:'post',url:apiServerPrefix+'/departmentManage/delete'
            },
            update:{//更新科室
                method:'post',url:apiServerPrefix+'/departmentManage/update'
            },
            import:{//批量导入
                method:'post',url:apiServerPrefix+'/departmentManage/import'
            }
        }, //用户管理
        user:{
            add:{ //添加用户
                method:'post',url:apiServerPrefix+'/userManagement/add'
            },
            delete:{ //删除用户
                method:'post',url:apiServerPrefix+'/userManagement/delete'
            },
            update:{ //更新用户
                method:'post',url:apiServerPrefix+'/userManagement/update'
            },
            all:{ //获取全部用户
                method:'get', url:apiServerPrefix+'/userManagement/all'
            },
            import:{//批量导入
                method:'post',url:apiServerPrefix+'/userManagement/import'
            }
        }, //结算方式管理
        settlement: {
            all:{ //获取结算方式
                method:'get', url:apiServerPrefix+'/settlementCategoryManagement/all'
            },
            add:{ //增加结算方式
                method:'post',url:apiServerPrefix+'/settlementCategoryManagement/add'
            },
            delete:{ //删除结算方式
                method:'post',url:apiServerPrefix+'/settlementCategoryManagement/delete'
            },
            update:{ //修改结算方式
                method:'post',url:apiServerPrefix+'/settlementCategoryManagement/update'
            },
        },//挂号等级管理
        registrationLevel: {
            all:{  //获取所有挂号等级
                method:'get', url:apiServerPrefix+'/registrationLevelManagement/all'
            },
            add:{  //增加挂号等级
                method:'post',url:apiServerPrefix+'/registrationLevelManagement/add'
            },
            delete:{  //删除挂号等级
                method:'post',url:apiServerPrefix+'/registrationLevelManagement/delete'
            },
            update:{ //修改挂号等级
                method:'post',url:apiServerPrefix+'/registrationLevelManagement/update'
            }
        },//非药品项目管理
        nonDrugItem: {
            all:{ //获得所有的非药品项目
                method:'get', url:apiServerPrefix+'/nonDrugChargeItemManagement/all'
            },
            add:{  //增加非药品项目
                method:'post',url:apiServerPrefix+'/nonDrugChargeItemManagement/add'
            },
            delete:{  //删除非药品项目
                method:'post',url:apiServerPrefix+'/nonDrugChargeItemManagement/delete'
            },
            update:{ //修改非药品项目
                method:'post',url:apiServerPrefix+'/nonDrugChargeItemManagement/update'
            },
            import:{//批量导入
                method:'post',url:apiServerPrefix+'/nonDrugChargeItemManagement/import'
            }
        },
        //诊断目录管理
        diagnoseDirectory: {
            allClassification:{//获得全部的分类
                method:'get',url:apiServerPrefix+'/diagnoseDirectoryManagement/allClassification'
            },
            searchAllByClassificationId:{//根据分类的id获取所有疾病
                method:'post',url:apiServerPrefix+'/diagnoseDirectoryManagement/searchAllByClassificationId'
            },
            update:{//更新
                method:'post',url:apiServerPrefix+'/diagnoseDirectoryManagement/update'
            },
            add:{//添加
                method:'post',url:apiServerPrefix+'/diagnoseDirectoryManagement/add'
            },
            delete:{//删除
                method:'post',url:apiServerPrefix+'/diagnoseDirectoryManagement/delete'
            },
            import:{//批量导入
                method:'post',url:apiServerPrefix+'/diagnoseDirectoryManagement/import'
            }
        }
    },
    //门诊挂号
    outpatientWorkstation:{
        registration:{
            //初始化面板信息
            init:{
                method:'get',
                url:apiServerPrefix+"/outpatientRegistration/init"
            },
            //同步医生信息
            syncDoctorList:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/syncDoctorList'
            },
            //计算费用
            calculateFee:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/calculateFee'
            },
            confirmRegistration:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/confirm'
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