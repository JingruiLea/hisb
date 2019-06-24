import React from 'react';
import {Collapse,Card,Typography, Tabs, Table, Divider, Button, Modal, InputNumber} from 'antd';
//import Sider from './sider';
import { Descriptions } from 'antd';

//import API from '../../../global/ApiConfig';
//import Message from '../../../global/Message';

const {Panel} = Collapse;

class PrescriptionDisplay extends React.Component {

  state={
    withdrawModalVisible:false,
    withdrawModalData:{drug_item:{}},
    withdrawModalNum:0
  }

  showWithdrawModal=(record)=>{
    this.setState({
      withdrawModalVisible:true,
      withdrawModalData:record
    })
  }

  hideWithdrawModal=()=>this.setState({withdrawModalVisible:false})

  render() {
    const {withdrawPrescripton,dispensePrescription}= this.props;
    const {dispenseList,withdrawableList,withdrawedList,registration} = this.props;
    dispenseList.forEach(prescription => {
      prescription.prescription_item_list.forEach(item=>item.key = item.id)
    });
    withdrawableList.forEach(prescription => {
      prescription.prescription_item_list.forEach(item=>item.key = item.id)
    });
    withdrawedList.forEach(prescription => {
      prescription.prescription_item_list.forEach(item=>item.key = item.id)
    });

    return (
    <Card title="用户处方明细" style={{minHeight:'800px'}}>
      {registration===null?null:
      <Descriptions bordered title="基本信息" border >
        <Descriptions.Item label="病例号">{registration.medical_record_id}</Descriptions.Item>
        <Descriptions.Item label="患者姓名">{registration.patient_name}</Descriptions.Item>
        <Descriptions.Item label="年龄">{registration.age}</Descriptions.Item>
        <Descriptions.Item label="就诊科室">{registration.registration_department}</Descriptions.Item>
        <Descriptions.Item label="医保诊断">{registration.medical_insurance_diagnosis}</Descriptions.Item>
        <Descriptions.Item label="挂号类型">{registration.registration_category}</Descriptions.Item>
      </Descriptions>}

      <Divider/>

      <Tabs>
        <Tabs.TabPane tab="可取药" key="1" disabled={dispenseList===null}>
            <Typography.Title level={4}>
              共有{dispenseList.length}个处方
            </Typography.Title>
            <Collapse accordion>
              {dispenseList.map(dispense=>(
                <Panel header={
                  <span>
                  <span style={{marginRight:'20px'}}><b>处方号码:</b>{dispense.id}</span>
                  <span style={{marginRight:'20px'}}><b>子目数:</b>{dispense.prescription_item_list.length}</span>
                  <span style={{marginRight:'20px'}}><b>创建日期:</b>{dispense.create_time}</span>
                  <span style={{marginRight:'20px'}}><b>状态:</b>{dispense.status}</span>
                  </span>
                  } key="1">
                    <Table 
                      size="small"
                      columns={
                      [
                        {
                          title:"药品编码",
                          dataIndex:'drug_item.code'
                        },{
                          title:"药品名称",
                          dataIndex:'drug_item.name'
                        },{
                          title:"药品规格",
                          dataIndex:'drug_item.format'
                        },{
                          title:"药品单位",
                          dataIndex:'drug_item.unit'
                        },{
                          title:"药品类型",
                          dataIndex:'drug_item.type'
                        },{
                          title:"数量",
                          dataIndex:'amount'
                        },{
                          title:"剂量",
                          dataIndex:'dosage'
                        },{
                          title:"使用频率",
                          dataIndex:'frequency'
                        },{
                          title:"状态",
                          dataIndex:"status"
                        },
                        {
                          title:"操作",
                          dataIndex:'id',
                          render:(id,record,index)=>(
                            <Button 
                              size="small"
                              type={record.status==="未取药"?"primary":"danger"}
                              disabled={record.status==="已退药"}
                              onClick={()=>{
                                //未取药, 已取药, 已退药
                                if(record.status==="未取药") {
                                  dispensePrescription({prescription_item_id:[id]});
                                } else if(record.status==="已取药") {
                                  this.showWithdrawModal(record)
                                } 
                              }} >
                                {record.status==="未取药"?"取药":"退药"}
                              </Button>
                          )
                        }
                      ]
                    } dataSource={dispense.prescription_item_list}/>
                </Panel>
              ))}
            </Collapse>
        </Tabs.TabPane>

        <Tabs.TabPane tab="可退药" key="2" disabled={withdrawableList===null}>
        <Typography.Title level={4}>
              共有{withdrawedList.length}个处方
            </Typography.Title>
            <Collapse accordion>
              {withdrawableList.map(dispense=>(
                <Panel header={
                  <span>
                  <span style={{marginRight:'20px'}}><b>处方号码:</b>{dispense.id}</span>
                  <span style={{marginRight:'20px'}}><b>子目数:</b>{dispense.prescription_item_list.length}</span>
                  <span style={{marginRight:'20px'}}><b>创建日期:</b>{dispense.create_time}</span>
                  <span style={{marginRight:'20px'}}><b>状态:</b>{dispense.status}</span>
                  </span>
                  } key="1">
                    <Table 
                      size="small"
                      columns={
                      [
                        {
                          title:"药品编码",
                          dataIndex:'drug_item.code'
                        },{
                          title:"药品名称",
                          dataIndex:'drug_item.name'
                        },{
                          title:"药品规格",
                          dataIndex:'drug_item.format'
                        },{
                          title:"药品单位",
                          dataIndex:'drug_item.unit'
                        },{
                          title:"药品类型",
                          dataIndex:'drug_item.type'
                        },{
                          title:"数量",
                          dataIndex:'amount'
                        },{
                          title:"剂量",
                          dataIndex:'dosage'
                        },{
                          title:"使用频率",
                          dataIndex:'frequency'
                        },{
                          title:"状态",
                          dataIndex:"status"
                        },
                        {
                          title:"操作",
                          dataIndex:'id',
                          render:(id,record,index)=>(
                            <Button 
                              size="small"
                              type={record.status==="未取药"?"primary":"danger"}
                              disabled={record.status==="已退药"}
                              onClick={()=>{
                                //未取药, 已取药, 已退药
                                if(record.status==="未取药") {
                                  dispensePrescription({prescription_item_id:[id]});
                                } else if(record.status==="已取药") {
                                  this.showWithdrawModal(record)
                                } 
                              }} >
                                {record.status==="未取药"?"取药":"退药"}
                              </Button>
                          )
                        }
                      ]
                    } dataSource={dispense.prescription_item_list}/>
                </Panel>
              ))}
            </Collapse>
        </Tabs.TabPane>

        <Tabs.TabPane tab="已退药" key="3" disabled={withdrawedList===null}>
        <Typography.Title level={4}>
              共有{withdrawedList.length}个处方
            </Typography.Title>
            <Collapse accordion>
              {withdrawedList.map(dispense=>(
                <Panel header={
                  <span>
                  <span style={{marginRight:'20px'}}><b>处方号码:</b>{dispense.id}</span>
                  <span style={{marginRight:'20px'}}><b>子目数:</b>{dispense.prescription_item_list.length}</span>
                  <span style={{marginRight:'20px'}}><b>创建日期:</b>{dispense.create_time}</span>
                  <span style={{marginRight:'20px'}}><b>状态:</b>{dispense.status}</span>
                  </span>
                  } key="1">
                    <Table 
                      size="small"
                      columns={
                      [
                        {
                          title:"药品编码",
                          dataIndex:'drug_item.code'
                        },{
                          title:"药品名称",
                          dataIndex:'drug_item.name'
                        },{
                          title:"药品规格",
                          dataIndex:'drug_item.format'
                        },{
                          title:"药品单位",
                          dataIndex:'drug_item.unit'
                        },{
                          title:"药品类型",
                          dataIndex:'drug_item.type'
                        },{
                          title:"数量",
                          dataIndex:'amount'
                        },{
                          title:"剂量",
                          dataIndex:'dosage'
                        },{
                          title:"使用频率",
                          dataIndex:'frequency'
                        },{
                          title:"状态",
                          dataIndex:"status"
                        },
                        {
                          title:"操作",
                          dataIndex:'id',
                          render:(id,record,index)=>(
                            <Button 
                              size="small"
                              type={record.status==="未取药"?"primary":"danger"}
                              disabled={record.status==="已退药"}
                              onClick={()=>{
                                //未取药, 已取药, 已退药
                                if(record.status==="未取药") {
                                  dispensePrescription({prescription_item_id:[id]});
                                } else if(record.status==="已取药") {
                                  this.showWithdrawModal(record)
                                } 
                              }} >
                                {record.status==="未取药"?"取药":"退药"}
                              </Button>
                          )
                        }
                      ]
                    } dataSource={dispense.prescription_item_list}/>
                </Panel>
              ))}
            </Collapse>
        </Tabs.TabPane>
      </Tabs>
      
      <Modal
        title="退药"
        visible={this.state.withdrawModalVisible}
        closable destroyOnClose
        onCancel={this.hideWithdrawModal.bind(this)}
        footer={false}
      >
        <Descriptions>
          <Descriptions.Item label="编码">{this.state.withdrawModalData.drug_item.code}</Descriptions.Item>
          <Descriptions.Item label="名称">{this.state.withdrawModalData.drug_item.name}</Descriptions.Item>
          <Descriptions.Item label="单位">{this.state.withdrawModalData.drug_item.unit}</Descriptions.Item>
        </Descriptions>
        <InputNumber
          style={{marginRight:'20px'}}
          placeholder="退药数量"
          value={this.state.withdrawModalNum} 
          onChange={v=>{
            //console.log(v)
            this.setState({withdrawModalNum:v})
          }}/>
        
        <Button 
          onClick={()=>{
            const id = this.state.withdrawModalData.id;
            const amount = this.state.withdrawModalNum
            withdrawPrescripton({
              prescription_items:[
                {
                  id,
                  amount
                }
              ]}
            )
            this.hideWithdrawModal()
          }}
          type="primary" 
          disabled={this.state.withdrawModalNum<=0 || this.state.withdrawModalNum>this.state.withdrawModalData.amount}>
            退药</Button>
      </Modal>
    </Card>
    )
  }

}

export default PrescriptionDisplay;