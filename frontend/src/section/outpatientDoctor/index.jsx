import React from 'react';
import {Layout, Divider, Input, Modal} from 'antd'
import {Row,Col,Table,Card,Typography,Pagination} from 'antd'
import { Tabs } from 'antd';
import { Tree } from 'antd';

import MedicalRecord from './medicalRecord';
import SiderPatientSelector from './SiderPatientSelector';
import API from '../../global/ApiConfig';

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;
const TabPane = Tabs.TabPane;
const {Content,Header} = Layout;

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

  componentDidMount=()=>{this.initPatientsList()}

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
      this.initPatientsList();
    })
  }

  getMedicalRecord=(medical_record_id,callback)=>{
    API.request(API.outpatientDoctor.medicalRecord.get,{medical_record_id})
    .ok(medicalRecord=>{
      callback(medicalRecord)
    }).submit()
  }

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
              <Tabs size="small">
                <Tabs.TabPane tab="门诊病历" key="1">
                  <MedicalRecord currentPatient={state.currentPatient} />
                </Tabs.TabPane>
                <Tabs.TabPane tab="成药处方" key="2">
                  
                </Tabs.TabPane> 
                <Tabs.TabPane tab="检验申请" key="3">
                  
                </Tabs.TabPane>
              </Tabs>
            </Card>
          </Col>
        </Row>
      </Content>)
  }
}

export default DiagnoseSection;