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
            },//根据病例号，获得挂号信息(同上)
            getRegistrationInfo:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbea13200007800ccd406'//apiServerPrefix+'/outpatientCharge/registrationByRecordId'
            },//交费
            charge:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/outpatientCharge/charge'
            },//退费
            withdraw:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/outpatientCharge/withdraw'
            },//通过病历号, 获取收费项目列表（已缴费）,
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
            },//搜索一个病人的挂号信息
            registrationInfo:{
                url:'http://www.mocky.io/v2/5cffbab83200006100eac96a',//apiServerPrefix+'/medicalRecord/medicalRecordHistory',
                method:'post'
            },//历史病历
            historyMedicalRecordList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d03185330000051001f4a44',//apiServerPrefix+'/medicalRecord/allHisMedicalRecord'
            },//获取（创建）病历
            get:{
                method:'post',
                url:'http://www.mocky.io/v2/5d037b2530000024001f4d42',//apiServerPrefix+'/medicalRecord/getMedicalRecord'
            },//更新(暂存)病历
            update:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/medicalRecord/updateMedicalRecord'
            },//保存病历
            save:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/medicalRecord/saveMedicalRecord'
            },//确诊提交病历
            confirm:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a',//apiServerPrefix+'/medicalRecord/confirmMedicalRecord'
            }
        },
        //病历模板
        medicalRecordTemplate:{
            //获取全部病历模板
            list:{
                url:'http://www.mocky.io/v2/5d030d5c30000055001f4a1f',//apiServerPrefix+'/medicalRecordTemplate/list'
                method:'post'
            },//获得模板详细
            detail:{
                url:'http://www.mocky.io/v2/5d030bc630000060001f4a1e',//apiServerPrefix+'/medicalRecordTemplate/detail'
                method:'post'
            },//创建模板
            create:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/medicalRecordTemplate/create'
                method:'post'
            },//更新模板
            update:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/medicalRecordTemplate/update'
                method:'post'
            },//删除模板
            delete:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/medicalRecordTemplate/delete'
                method:'post'
            }
        },
        //诊断模板
        diagnoseTemplate:{
            //获取全部诊断模板
            list:{
                url:'http://www.mocky.io/v2/5d05088d3200004b00d78c1b',//apiServerPrefix+'/diagnoseTemplate/list'
                method:'post'
            },//获得模板详细
            detail:{
                url:'http://www.mocky.io/v2/5d0506743200001400d78c18',//apiServerPrefix+'/diagnoseTemplate/detail'
                method:'post'
            },//创建模板
            create:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/diagnoseTemplate/create'
                method:'post'
            },//更新模板
            update:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/diagnoseTemplate/update'
                method:'post'
            },//删除模板
            delete:{
                url:'http://www.mocky.io/v2/5cfe5c3c320000660045f04b',//apiServerPrefix+'/diagnoseTemplate/delete'
                method:'post'
            }
        },
        //检查 检验 处置
        IAD:{
            //根据类型获取所有的可选项目
            allItems:{
                url:' http://www.mocky.io/v2/5d078bcb30000086000521a5',//apiServerPrefix+'/exam/all',
                method:'post'
            },//创建
            create:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/create',
                method:'post'
            },//删除
            delete:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/delete',
                method:'post'
            },//更新
            update:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/update',
                method:'post'
            },//发送
            send:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/update',
                method:'post'
            },
            //作废
            cancel:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/cancel',
                method:'post'
            },//获取当前开具的列表
            list:{
                url:'http://www.mocky.io/v2/5d0794253400007c005d937e',//apiServerPrefix+'/exam/list',
                method:'post'
            }
        },
        //检查 检验 处置的组套（模板）
        IADTemplate:{
            //创建
            create:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/create',
                method:'post'
            },//删除
            delete:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/delete',
                method:'post'
            },//更新
            update:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/exam/update',
                method:'post'
            },//获取当前全部组套
            all:{
                url:'http://www.mocky.io/v2/5d0795f13400000e005d938a',//apiServerPrefix+'/exam/list',
                method:'post'
            },//获取详细信息
            detail:{
                url:'http://www.mocky.io/v2/5d0885a73400004d005d978f',//apiServerPrefix+'/exam/detail',
                method:'post'
            }
        },
        //处方(成药 草药 医技补录)
        prescription:{
            //获取所有的药品
            allDrugs:{
                url:'http://www.mocky.io/v2/5d089c6d34000059005d989d',//apiServerPrefix+'prescription/allDrugs'
                method:'post',
            },//获取全部的处方
            allPrescription:{
                url:'http://www.mocky.io/v2/5d08f5a43400000e00d82cb6',//apiServerPrefix+'/prescription/allPrescription',
                method:'post'
            },//创建处方
            create:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/create',
                method:'post'
            },//暂存（更新）处方
            update:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/create',
                method:'post'
            },//发送处方
            send:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/submit',
                method:'post'
            },//删除处方
            delete:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/delete',
                method:'post'
            },//作废处方
            cancel:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/cancel',
                method:'post'
            }
        },
        prescriptionTemplate:{
            //获取全部的组套
            list:{
                url:'http://www.mocky.io/v2/5d08b2e434000064985d996e',//apiServerPrefix+'/prescriptionTemplate/list',
                method:'post'
            },//创建组套
            create:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescriptionTemplate/create',
                method:'post'
            },//组套详情
            detail:{
                url:'http://www.mocky.io/v2/5d08b4313400007a005d997c',//apiServerPrefix+'/prescription/detail',
                method:'post'
            },//删除组套
            delete:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescription/delete',
                method:'post'
            },//更新组套
            update:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/prescriptionTemplate/update',
                method:'post'
            }
        },
        //患者费用查询
        patientFee:{
            //获取全部费用
            historyChargeItems:{
                url:'http://www.mocky.io/v2/5d099fe53400005e29d82f11',//apiServerPrefix+'/outpatientCharge/historyChargeItems',
                method:'post'
            }
        }
    },
    //医技科室
    doctorOfTechnology:{
        //其他见上文 处方管理 
        //执行检查 检验 处置
        IADExcute:{
            //登记
            register:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/examExcute/register',
                method:'post'
            },//录入结果
            submitResult:{
                url:'http://www.mocky.io/v2/5d078c1f300000a1530521a7',//apiServerPrefix+'/examExcute/submitResult',
                method:'post'
            },//获取结果
            getResult:{
                url:'http://www.mocky.io/v2/5d09f14f3400001129d8313d',//apiServerPrefix+'/examExcute/getResult',
                method:'post'
            }
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
            },//删除药品信息
            delete:{
                method:'post',
                url:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5'//apiServerPrefix+'/drugInfoManagement/delete'
            },//更新药品信息
            update:{
                method:'post',
                url:'http://www.mocky.io/v2/5cff0e4b3200004d0045f2d5'//apiServerPrefix+'/drugInfoManagement/update'
            },//全部药品信息
            all:{
                method:'post',
                url:'http://www.mocky.io/v2/5cff0f0a3200004e0045f2d9'//:apiServerPrefix+'/drugInfoManagement/all'
            },//根据药品名字模糊搜索药品
            getDrugInfoByName:{
                method:'post',
                url:apiServerPrefix+'/drugInfoManagement/getDrugInfoByName'
            }
        },
        //收发药
        drugDispatcher:{
            //可发药表
            dispenseList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d021de03100003400ab2c85'//apiServerPrefix+'/drugDispense/list'
            },//可退药表
            withdrawableList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d021de03100003400ab2c85'//apiServerPrefix+'/drugWithdrawal/list'
            },//已经退药表
            withdrawedList:{
                method:'post',
                url:'http://www.mocky.io/v2/5d021de03100003400ab2c85'//apiServerPrefix+'/drugWithdrawal/list'
            },//发药
            dispenseSubmit:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a'//apiServerPrefix+'/drugDispense/submit'
            },//退药
            withdrawSubmit:{
                method:'post',
                url:'http://www.mocky.io/v2/5cfcbf4b3200005900ccd40a'//apiServerPrefix+'/drugWithdrawal/submit'
            }
        }
    },
    //财务处统计
    financialAdmin:{
        //收费项目管理
        expenseClassification:{
            all:{//获得全部的收费项目
                method:'get', url:'http://www.mocky.io/v2/5d09dc833400001229d8303f',//apiServerPrefix+'/expenseClassificationManage/all'
            },
            add:{//添加收费项目
                method:'post',url:'http://www.mocky.io/v2/5d09dca13400005e29d83040',//apiServerPrefix+'/expenseClassificationManage/add'
            },
            delete:{//删除收费项目
                method:'post',url:'http://www.mocky.io/v2/5d09dca13400005e29d83040',//apiServerPrefix+'/expenseClassificationManage/delete'
            },
            update:{//更新收费项目
                method:'post',url:'http://www.mocky.io/v2/5d09dca13400005e29d83040',//apiServerPrefix+'/expenseClassificationManage/update'
            }
        },
    },


    //请求
    request:(api,reqData={})=>{
        return new Request(api,reqData);
    }
}

export default API;

