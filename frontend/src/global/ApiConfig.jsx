import Request from "./Request";
import Config  from './Config'

const server = Config.server;
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
        //医生排班管理
    schedulingInfoManagement:{
        //获取班次信息
        getShiftInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/all'
        },
        //添加班次信息
        addShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/add'
        },
        //删除班次信息
        deleteShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/delete'
        },
        //更新班次信息
        updateShiftInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingShiftManagement/update'
        },
        //获取人员信息
        getPersonnelInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorSchedulingManagement/all'
        },
        //添加人员信息
        addPersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/add'
        },
        //删除人员信息
        deletePersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/delete'
        },
        //更新人员信息
        updatePersonnelInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/update'
        },
        //查找AddForm信息
        getPersonnelAddInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addPersonnelTable'
        },
        //查找AddForm信息
        getPersonnelAddNameInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorSchedulingManagement/addNamePersonnelTable'
        },
        //选择排班人员
        chooseDoctor:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/choose'
        },
        //获取排班信息
        getScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/all'
        },
        //更新排班信息
        updateScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/update'
        },
        //添加排班信息
        addScheduleInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/add'
        },
        //获取全部排班
        getAllScheduleInfo:{
            method:'get',
            url:apiServerPrefix+'/doctorWorkforceManagement/getAll'
        },
        //查找时间冲突
        findTimeConflict:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/time'
        },
        //添加一行排班信息
        addScheduleRowInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRow'
        },
        //查找addRow冲突
        findAddRowConflict:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addRowConflict'
        },
        //删除人员信息
        deleteScheduleRowInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/delete'
        },
        //查找AddForm信息
        getAddInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/addTable'
        },
        //覆盖排班信息
        overwriteScheduleInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/overwrite'
        },
        //删除覆盖的排班信息
        deleteOverwriteInfo:{
            method:'post',
            url:apiServerPrefix+'/doctorWorkforceManagement/deleteOverwrite'
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
                url:apiServerPrefix+'/outpatientRegistration/confirm'//"http://www.mocky.io/v2/5cfb42df300000f6030a8afb"
            },//退号
            withdrawNumber:{
                method:'post',
                url:apiServerPrefix+'/outpatientRegistration/withdrawNumber'
            },//搜索用户挂号信息
            searchRegistration:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbea13200007800ccd406'//apiServerPrefix+'/outpatientCharge/registrationByRecordId'
            }
        },
        //门诊收费
        outpatientCharge:{
            //通过病历号, 获取收费项目列表（待缴费）,
            getChargeItems:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfe6431320000660045f07a',//apiServerPrefix+'/outpatientCharge/getChargeItems'
            },
            //根据病例号，获得挂号信息(同上)
            getRegistrationInfo:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbea13200007800ccd406'//apiServerPrefix+'/outpatientCharge/registrationByRecordId'
            },
            //交费
            charge:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/outpatientCharge/charge'
            },
            //退费
            withdraw:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/outpatientCharge/withdraw'
            },
            //通过病历号, 获取收费项目列表（已缴费）,
            getChargedItems:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfe6431320000660045f07a',//apiServerPrefix+'/outpatientCharge/getHistoryChargeItems'
            }
        },
        //门诊日结
        dailyCollect:{
            //初始化面板
            getHistoryList:{
                method:'get',
                url:'http://www.mocky.io/v2/5cfe7ce9320000660045f137',////apiServerPrefix+'/dailyCollect/list'
            },
            //日结详细信息
            dailyCollectDetail:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfe5bfc3200004f0045f047',//apiServerPrefix+'/dailyCollect/detail'
            },
            //日结请求
            dailyCollectRequest:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/dailyCollect/collect'
            }
        },
    },
    //门诊医生
    outpatientDoctor:{
        //门诊病历管理
        medicalRecord:{
            //同步病人列表
            getPatientList:{
                url:'http://www.mocky.io/v2/5d008c143200005f00f9d582',//apiServerPrefix+'/medicalRecord/getPatientList',
                method:'post'
            },
            //搜索一个病人的挂号信息
            registrationInfo:{
                url:'http://www.mocky.io/v2/5cffbab83200006100eac96a',//apiServerPrefix+'/medicalRecord/medicalRecordHistory',
                method:'post'
            },
            //历史病历
            searchHistory:{
                method:'post',
                url:'http://www.mocky.io/v2/5cffc3e83200005300eac9c6',//apiServerPrefix+'/medicalRecord/medicalRecordHistory'
            },
            //获取（创建）病历
            get:{
                method:'post',
                url:'http://www.mocky.io/v2/5d009be63200000f00f9d5dd',//apiServerPrefix+'/medicalRecord/getMedicalRecord'
            },
            //更新病历
            update:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/medicalRecord/updateMedicalRecord'
            }
        },
        //病历模板
        medicalRecordTemplate:{
            //获取全部病历
            all:{
                url:'http://www.mocky.io/v2/5d01ce963100003400ab29fb',
                method:'post'
            },
        }
    },
    //药房
    pharmacyWorkStation:{
        //药品目录管理
        drugInfoManagement:{
            //增加药品信息
            add:{
                method:'post',
                url:' http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5',//apiServerPrefix+'/drugInfoManagement/post'
            },
            //删除药品信息
            delete:{
                method:'post',
                url:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5'//apiServerPrefix+'/drugInfoManagement/delete'
            },
            //更新药品信息
            update:{
                method:'post',
                url:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5'//apiServerPrefix+'/drugInfoManagement/update'
            },
            //全部药品信息
            all:{
                method:'get',
                url:'http://www.mocky.io/v2/5cff0f0a3200004e0045f2d9'//:apiServerPrefix+'/drugInfoManagement/all'
            },
            //根据药品名字模糊搜索药品
            getDrugInfoByName:{
                method:'post',
                url:apiServerPrefix+'/drugInfoManagement/getDrugInfoByName'
            }
        },
        //收发药
        drugDispatcher:{
            //发药表
            dispenseList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d021de03100003400ab2c85'//apiServerPrefix+'/drugDispense/list'
            },
            //退药表
            withdrawList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d021de03100003400ab2c85'//apiServerPrefix+'/drugWithdrawal/list'
            },
            //发药
            dispenseSubmit:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a'//apiServerPrefix+'/drugWithdrawal/list'
            },
            //退药
            withdrawSubmit:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a'//apiServerPrefix+'/drugWithdrawal/submit'
            }
        }

    },


    //请求
    request:(api,reqData={})=>{
        return new Request(api,reqData);
    }
}

export default API;

