import React from 'react';
import {Row,Col,Layout,Card,Tabs} from 'antd'

import API from '../../global/ApiConfig';
import Message from '../../global/Message';
import SiderPatientSelector from './SiderPatientSelector';
import MedicalRecordHome from './medicalRecord';
import InspectionSection from './inspection';
import AnalysisSection from './analysis';
import DisposalSection from './disposal';
import PatentMedcinePrescription from './patentMedicinePrescription';
import HerbalMedcinePrescription from './herbalMedcinePrescription';
import PatientFee from './patientFee';
import CompleteDiagnose from './completeDiagnose'
import LogoDisplay from '../../global/LogoDisplay';

const {Content} = Layout;

class DiagnoseSection extends React.Component {

  /*
  门诊医生的主工作站
  currentPatient:{
    registration:
    medicalRecord
  } */
  state = {
    currentPatient:{},
    patientList:{waiting:[],pending:[]},
    siderLoading:true
  }

  componentDidMount=()=>{console.log('mounted');this.initPatientsList()}

  handleTabsChange=(key)=>{
    //如果是结束诊断，需要加载处方，因为处方没有做全局绑定，这里需要在点击tab时手动加载处方信息
    if(key==='8' && this.CompleteDiagnose!==undefined) {
      this.CompleteDiagnose.loadPrescription();
    }
  }

  //置空选中状态
  clearPatientSelect=()=>{
    this.setState({
      currentPatient:{}
    })
    this.initPatientsList();
  }

  //选择当前操作的病人挂号信息
  selectPatient=(registration)=>{
    this.setState({siderLoading:true})
    this.getMedicalRecord(registration.medical_record_id,(medicalRecord)=>{
      const currentPatient = {
        registration:registration,
        medicalRecord:medicalRecord
      }
      console.log('set currentPatient:',currentPatient)
      this.setState({
        currentPatient:currentPatient,
        siderLoading:false
      })
      console.warn('index 62 syncAllHistoryMedicalRecord no params!')
      if(this.MedicalRecordHome) {
        this.MedicalRecordHome.syncAllHistoryMedicalRecord();
        this.MedicalRecordHome.applyMedialRecordData(currentPatient.medicalRecord)
      }
      this.initPatientsList();
    })
  }

  //刷新病人的信息
  refreshPatientMedicalRecord=()=>{
    const {currentPatient} = this.state;
    this.selectPatient(currentPatient.registration)
  }

  //（创建）获取挂号对应的病历信息
  getMedicalRecord=(medical_record_id,callback)=>{
    API.request(API.outpatientDoctor.medicalRecord.get,{medical_record_id})
    .ok(medicalRecord=>{
      medicalRecord.diagnose.western_diagnose.forEach(x=>x.key=x.disease_id)
      medicalRecord.diagnose.chinese_diagnose.forEach(x=>x.key=x.disease_id)
      callback(medicalRecord)
    }).submit()
  }

  //初始化患者列表
  initPatientsList=()=>{
    console.log('loading patient list...')
    API.request(API.outpatientDoctor.medicalRecord.getPatientList)
    .ok((data)=>{
      data.waiting.forEach(element => element.key=element.medical_record_id);
      data.pending.forEach(element => element.key=element.medical_record_id);
      this.setState({
        siderLoading:false,
        patientList:data
      })}
    ).submit();
  }

  //确诊
  completeDiagnose=()=>{
    const {medicalRecord} = this.state.currentPatient;
    API.request(API.outpatientDoctor.medicalRecord.confirm,medicalRecord)
    .ok(data=>{
      Message.success('已确诊');
      this.clearPatientSelect();
    }).submit();
  }

  
  render() {
    const {me} = this.props;
    const {currentPatient,patientList} = this.state;
    //hasSelect表示当前是否有病人选取
    const hasSelect = !(!currentPatient.registration);
    //已提交？
    const submited = currentPatient.medicalRecord && (currentPatient.medicalRecord.status==='已提交' || currentPatient.medicalRecord.status==='诊毕')
    //诊毕？
    const completed = currentPatient.medicalRecord && currentPatient.medicalRecord.status==='诊毕';

    //console.log(hasSelect,submited,completed);

    return (
      <Content style={{ margin: '0 16px',paddingTop:3}}>
        <Row>
          <Col span={5}>
            <SiderPatientSelector 
              patientList={patientList}
              currentPatient={currentPatient}
              selectPatient={this.selectPatient.bind(this)}
              loading={this.state.siderLoading}/>
          </Col>

          <Col span={19}>
          {!hasSelect?<LogoDisplay/>:
            <Card>
              <Tabs size="small" defaultActiveKey={completed?'8':'1'} onChange={this.handleTabsChange.bind(this)}>
                <Tabs.TabPane tab="门诊病历" key="1" disabled={!hasSelect || completed}>
                  <MedicalRecordHome  
                    onRef={(ref)=>{this.MedicalRecordHome=ref}}
                    currentPatient={currentPatient}
                    refreshPatientMedicalRecord={this.refreshPatientMedicalRecord.bind(this)} />
                </Tabs.TabPane>

                <Tabs.TabPane tab="检查申请" key="2" disabled={!hasSelect || !submited || completed}>
                  <InspectionSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={currentPatient} />
                </Tabs.TabPane>

                <Tabs.TabPane tab="检验申请" key="3" disabled={!hasSelect || !submited || completed}>
                  <AnalysisSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={currentPatient} />  
                </Tabs.TabPane>

                <Tabs.TabPane tab="处置申请" key="4" disabled={!hasSelect || !submited || completed}>
                  <DisposalSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={currentPatient} />  
                </Tabs.TabPane>

                <Tabs.TabPane tab="成药处方" key="5" disabled={!hasSelect || !submited || completed}>
                  <PatentMedcinePrescription
                    onRef={(ref)=>{this.PatentMedcinePrescription=ref}}
                    currentPatient={currentPatient} />  
                </Tabs.TabPane> 

                <Tabs.TabPane tab="草药处方" key="6" disabled={!hasSelect || !submited || completed}>
                  <HerbalMedcinePrescription
                    onRef={(ref)=>{this.PatentMedcinePrescription=ref}}
                    currentPatient={currentPatient} /> 
                </Tabs.TabPane>

                <Tabs.TabPane tab="费用查询" key="7" disabled={!hasSelect || !submited}> 
                  <PatientFee
                      onRef={(ref)=>{this.PatientFee=ref}}
                      currentPatient={currentPatient} /> 
                </Tabs.TabPane>

                <Tabs.TabPane tab="结束诊断" key="8" disabled={!hasSelect || !submited}> 
                  <CompleteDiagnose
                      me={me}
                      onRef={(ref)=>{this.CompleteDiagnose=ref}}
                      currentPatient={currentPatient}
                      completeDiagnose={this.completeDiagnose.bind(this)} /> 
                </Tabs.TabPane>
              </Tabs>
            </Card>}
          </Col>
        </Row>
      </Content>)
  }
}

export default DiagnoseSection;