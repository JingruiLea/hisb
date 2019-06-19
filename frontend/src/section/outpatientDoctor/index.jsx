import React from 'react';
import {Row,Col,Layout,Card,Tabs} from 'antd'

import API from '../../global/ApiConfig';
import SiderPatientSelector from './SiderPatientSelector';
import MedicalRecordHome from './medicalRecord';
import InspectionSection from './inspection';
import AnalysisSection from './analysis';
import DisposalSection from './disposal';
import PatentMedcinePrescription from './patentMedicinePrescription';
import HerbalMedcinePrescription from './herbalMedcinePrescription';
import patientFee from './patientFee';
import PatientFee from './patientFee';

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
    //currentPatient:{medicalRecord:{id:1},registration:{}},//used to debug
    patientList:{waiting:[],pending:[]},
    siderLoading:true
  }

  componentDidMount=()=>{console.log('mounted');this.initPatientsList()}

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
      this.MedicalRecordHome.syncAllHistoryMedicalRecord();
      this.MedicalRecordHome.applyMedialRecordData(currentPatient.medicalRecord)
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

  render() {
    const {state} = this;
    const {currentPatient} = state;
    const disabled = currentPatient.registration===null || currentPatient.registration===undefined;
  
    return (
      <Content style={{ margin: '0 16px',paddingTop:3}}>
        <Row>
          <Col span={5}>
            <SiderPatientSelector 
              patientList={state.patientList}
              currentPatient={state.currentPatient}
              selectPatient={this.selectPatient.bind(this)}
              loading={this.state.siderLoading}/>
          </Col>

          <Col span={19}>
            <Card>
              <Tabs size="small" defaultActiveKey={'1'}>
                <Tabs.TabPane tab="门诊病历" key="1" forceRender>
                  <MedicalRecordHome  
                    onRef={(ref)=>{this.MedicalRecordHome=ref}}
                    currentPatient={state.currentPatient}
                    refreshPatientMedicalRecord={this.refreshPatientMedicalRecord.bind(this)} />
                </Tabs.TabPane>

                <Tabs.TabPane tab="检查申请" key="2" disabled={disabled}>
                  <InspectionSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={state.currentPatient} />
                </Tabs.TabPane>

                <Tabs.TabPane tab="检验申请" key="3" disabled={disabled}>
                  <AnalysisSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={state.currentPatient} />  
                </Tabs.TabPane>

                <Tabs.TabPane tab="处置申请" key="4" disabled={disabled}>
                  <DisposalSection
                    onRef={(ref)=>{this.InspectionSection=ref}}
                    currentPatient={state.currentPatient} />  
                </Tabs.TabPane>

                <Tabs.TabPane tab="成药处方" key="5" disabled={disabled}>
                  <PatentMedcinePrescription
                    onRef={(ref)=>{this.PatentMedcinePrescription=ref}}
                    currentPatient={state.currentPatient} />  
                </Tabs.TabPane> 

                <Tabs.TabPane tab="草药处方" key="6" disabled={disabled}>
                  <HerbalMedcinePrescription
                    onRef={(ref)=>{this.PatentMedcinePrescription=ref}}
                    currentPatient={state.currentPatient} /> 
                </Tabs.TabPane>

                <Tabs.TabPane tab="费用查询" key="7">
                  <PatientFee
                      onRef={(ref)=>{this.PatientFee=ref}}
                      currentPatient={state.currentPatient} /> 
                </Tabs.TabPane>
              </Tabs>
            </Card>
          </Col>
        </Row>
      </Content>)
  }
}

export default DiagnoseSection;