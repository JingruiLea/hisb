import React from 'react';
import {Layout, Divider} from 'antd'
import {Row,Col,Table,Card,Typography,Pagination} from 'antd'
import { Tabs } from 'antd';
import { Tree } from 'antd';

import MedicalRecordForm from './MedicalRecordForm'
import MedicalRecordTemplate from './MedicalRecordTemplate' 
import API from '../../../global/ApiConfig'

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;
const TabPane = Tabs.TabPane;
const {Content,Header} = Layout;

class DiagnoseSection extends React.Component {

  state={
    allMedicalRecordTemplate:null
  }

  componentDidMount=()=>{
    this.getMedicalRecordTemplateList();
  }

  getMedicalRecordTemplateList=()=>{
    API.request(API.outpatientDoctor.medicalRecordTemplate.all)
    .ok((allMedicalRecordTemplate)=>{
      this.setState({allMedicalRecordTemplate})
    }).submit();
  }

  applyTemplate=(medicalTemplate)=>{
    this.MedicalRecordForm.applyTemplate(medicalTemplate)
  }

  render() {
    const {currentPatient} = this.props;
    const {allMedicalRecordTemplate } = this.state;
     return (
      <div>
        <Row>
          <Col span={16}>
            <MedicalRecordForm
              onRef={(ref)=>{this.MedicalRecordForm = ref;}}
              ref="medicalRecordForm"
              currentPatient={currentPatient}/>
          </Col>

          <Col span={8}>
            <MedicalRecordTemplate 
              applyTemplate={this.applyTemplate.bind(this)}
              allMedicalRecordTemplate={allMedicalRecordTemplate}/>
          </Col>
        </Row>
      </div>)
  }
}

export default DiagnoseSection;