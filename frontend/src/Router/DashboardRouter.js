import React from 'react';

import { BrowserRouter, Route} from 'react-router-dom';

import OfficesDisplaySection from '../Component/OfficesDisplaySection'
import DiagnosticDirectoryManagementSection from '../Component/DiagnosticDirectoryManagementSection'
import DiagnoseSection from '../Component/DiagnoseSection'

class DashboardRouter extends React.Component {
 

  render() {
      return (
        <BrowserRouter>
          <Route path="/diagnose" component={DiagnoseSection}/>
          <Route path="/officeManage" component={OfficesDisplaySection}/>
          <Route path="/diagnosticDirectoryManage" component={DiagnosticDirectoryManagementSection}/>
        </BrowserRouter>);
  }
}

export default DashboardRouter;

/*
断（西医）或初步诊断（中医），所有项目必填。
【操作描述】：查询患者、暂存病历首页、提交病历首页、清屏、存为模板、引用模板病历、常用诊断管理、查看历史病历。
1.	查询患者：通过患者病历号或者姓名，可快速查询出对应患者。
2.	暂存病历首页：医生根据对患者的询问，填写上述病历首页内容，为了防止长时间不操作系统异常，可以通过“暂存”功能，病历首页数据会临时保存。
3.	提交病历首页：医生输入完患者的病历首页信息之后，点击“提交”按钮，系统检查所有项目是否填写完整，填写完整之后，系统保存患者病历首页信息。之后医生才可以进行检查、检验、处置、开立处方等操作。
4.	清屏：清空当前页面已填写的病历信息。
5.	存为模板：把现有病历存为模板，输入模板名称、模板类别（分为全院、科室、个人），	全院模板：所有的医生都可以使用；科室模板：只有本科室的医生可以使用；个人模板：只有医生本人可以使用。
6.	引用病历模板：医生可以通过选择“病历模板”选项，从已经维护好的“病历模板”中选择模板，快速完成病历填写，医生可在此基础上进行调整。
7.	常用诊断管理：医生通过选择 “常用诊断”选项，选中需要用的诊断，将诊断信息填写到患者病历首页中，医生可在此基础上进行调整，医生也可以删除自己的常用诊断。
8.	查看历史病历：历史病历是针对一个患者的历次的就诊的病历信息，主要是给医生查看，能够综合的了解病人的疾病史及就诊记录，辅助医生对于患者的诊治。选择 “历史病历”，通过历史病历的查看，为本次写病历提供参考。
*/