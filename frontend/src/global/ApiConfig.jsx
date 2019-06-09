import Request from "./Request";
const server = 'http://localhost:8081'
const apiServerPrefix = server+'/api'

const API = {
    //登录
    login:{method:'post',url:server+'/login'},
    //退出
    logout:{method:'post',url:server+'/logout'},
    //个人中心
    me:{
        myInfo:{ //获取我的个人信息
            method:'post',url:apiServerPrefix+'/user/info'
        },
        updateInfo:{//更新我的个人信息
            method:'get',url:apiServerPrefix+'/user/updateInfo'
        }   
    },
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
    //门诊缴费挂号工作站
    outpatientWorkstation:{
        //门诊挂号
        registration:{
            //初始化面板信息
            init:{
                method:'get',
                url:apiServerPrefix+"/outpatientRegistration/init"
            },//同步医生信息
            syncDoctorList:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/syncDoctorList'
            },//计算费用
            calculateFee:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/calculateFee'
            },//确认挂号
            confirmRegistration:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/confirm'
            },//退号
            withdrawNumber:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/withdrawNumber'
            },//搜索用户挂号信息
            searchRegistration:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/registrationByRecordId'
            }
        },
        //门诊收费
        charge:{
            //通过病历号, 获取收费项目列表（待缴费）,
            getChargeItems:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbc143200006500ccd404'//apiServerPrefix+'/outpatientRegistration/info'
            },
            //根据病例号，获得挂号信息
            getRegistrationInfo:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbea13200007800ccd406'//apiServerPrefix+'/outpatientRegistration/registrationByRecordId'
            },
            //交费
            charge:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/outpatientRegistration/collect'
            },
            //通过病历号, 获取收费项目列表（已缴费）,
            getChargedItems:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbc143200006500ccd404',//apiServerPrefix+'/outpatientRegistration/historyInfo'
            }
        },
        //门诊日结
        dailyCollect:{
            //初始化面板
            getHistoryList:{
                method:'get',
                url:'http://www.mocky.io/v2/5cfcc22d3200006500ccd412',////apiServerPrefix+'/dailyCollect/list'
            },
            //日结详细信息
            dailyCollectDetail:{
                method:'post',
                url:apiServerPrefix+'/dailyCollect/detail'
            },
            //日结请求
            dailyCollectRequest:{
                method:'post',
                url:apiServerPrefix+'/dailyCollect/collect'
            }
        },
    },


    //请求
    request:(api,reqData={})=>{
        return new Request(api,reqData);
    }
}

export default API;

