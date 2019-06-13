import React from 'react';
import {Layout, Divider} from 'antd'
import {Row,Col,Table,Card,Button,Spin} from 'antd'
import { Tabs } from 'antd';
import { Tree } from 'antd';
import Message from '../../../global/Message';

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;
const TabPane = Tabs.TabPane;
const {Content,Header} = Layout;


class MedicalRecordTemplate extends React.Component {

  state = {
    selectedTemplate:null
  }

  //分类
  resolveMedicalTemplateClassification=(allMedicalRecordTemplate)=>{
    if(allMedicalRecordTemplate===null) return;
    const medicalTemplate = {}
    medicalTemplate.personal = allMedicalRecordTemplate.filter(x=>x.type===0)
    medicalTemplate.department = allMedicalRecordTemplate.filter(x=>x.type===1)
    medicalTemplate.hospital = allMedicalRecordTemplate.filter(x=>x.type===2)
    return medicalTemplate;
  }

  onSelect=(selectkeys)=>{
    const {allMedicalRecordTemplate} = this.props;
    const id = parseInt(selectkeys[0]);
    const selectedTemplate = allMedicalRecordTemplate.filter(x=>x.id===id)[0];
    this.setState({selectedTemplate})
    //Message.showConfirm('使用模板','你确定要使用改')
  }

  //引用模板
  handleApplyTemplate=()=>{
    const {selectedTemplate} = this.state;
    if(selectedTemplate===null) return;
    Message.showConfirm('病例模板','你确定要使用该模板吗?',()=>{
      this.props.applyTemplate(selectedTemplate);
    })
  }

  //删除模板
  handleDeleteTemplate=()=>{
    const {selectedTemplate} = this.state;
    if(selectedTemplate===null) return;
    Message.showConfirm('病例模板','你确定要删除该模板吗?',()=>{
      alert('等待api链接(设置selected为null,刷新病例列表)')
    })
  }

  render() {
    const {allMedicalRecordTemplate} = this.props;
    const {selectedTemplate} = this.state;
    const medicalTemplate = this.resolveMedicalTemplateClassification(allMedicalRecordTemplate);
  
    return (
    <div>
      <Card style={{minHeight:'470px'}}>
      {allMedicalRecordTemplate===null?<Spin style={{textAlign:'center'}}>载入中</Spin>:
        <Tabs defaultActiveKey="1" onChange={console.log}>
          <TabPane tab="病例模板" key="1">
            <DirectoryTree multiple defaultExpandAll onExpand={this.onExpand} showSearch onSelect={this.onSelect.bind(this)} >
              <TreeNode title="我的模板" key="0-0" selectable={false}>
                {medicalTemplate.personal.map(x=>(<TreeNode title={x.name} key={x.id} isLeaf/>))}
              </TreeNode>
              <TreeNode title="科室模板" key="0-1" selectable={false}>
                {medicalTemplate.department.map(x=>(<TreeNode title={x.name} key={x.id} isLeaf />))}
              </TreeNode>
              <TreeNode title="全院模板" key="0-2" selectable={false}>
                {medicalTemplate.hospital.map(x=>(<TreeNode title={x.name} key={x.id} isLeaf />))}
              </TreeNode>
            </DirectoryTree>
          </TabPane>
          <TabPane tab="常用诊断" key="2">
            常用诊断
          </TabPane>
          <TabPane tab="历史病例" key="3">
            历史病例
          </TabPane>
        </Tabs>}
      </Card>
      
      <Card style={{height:'400px'}} title={
        <div>
          <span style={{float:'left'}}>模板内容</span>
          <Button 
            style={{float:'right'}} 
            icon="delete" type="danger" size="small"
            onClick={this.handleDeleteTemplate.bind(this)} 
            disabled={selectedTemplate===null}>删除</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="check" type="primary" size="small"
            onClick={this.handleApplyTemplate.bind(this)} 
            disabled={selectedTemplate===null}>应用</Button>
        </div>
      }>
        {selectedTemplate===null?null:
        <div>
            <Row><Col span={10}><b>主诉：</b></Col><Col span={14}>{selectedTemplate.chief_complain}</Col></Row>
            <Row><Col span={10}><b>现病史：</b></Col><Col span={14}>{selectedTemplate.current_medical_history}</Col></Row>
            <Row><Col span={10}><b>现病治疗情况：</b></Col><Col span={14}>{selectedTemplate.current_treatment_situation}</Col></Row>
            <Row><Col span={10}><b>既往史：</b></Col><Col span={14}>{selectedTemplate.past_history}</Col></Row>
            <Row><Col span={10}><b>过敏史：</b></Col><Col span={14}>{selectedTemplate.allergy_history}</Col></Row>
            <Row><Col span={10}><b>体格诊断</b></Col><Col span={14}>{selectedTemplate.physical_examination}</Col></Row>
            
        </div>}
      </Card>
    </div>)
  }
}

export default MedicalRecordTemplate;
