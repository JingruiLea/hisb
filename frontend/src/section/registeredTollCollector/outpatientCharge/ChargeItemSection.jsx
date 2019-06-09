import React from 'react';
import {Card,Descriptions,Tabs,Input,Button,Spin, Table} from 'antd';

const { TabPane } = Tabs;


class ChargeItemSection extends React.Component {

  chargeItemColumn = [
    {
      title:"病历号",
      dataIndex:'medical_record_id'
    },{
      title:"缴费名称",
      dataIndex:'item_name'
    },{
      title:"规格",
      dataIndex:'format'
    },{
      title:"单价",
      dataIndex:'price'
    },{
      title:"数量",
      dataIndex:'mount'
    },{
      title:"执行科室",
      dataIndex:'excute_department'
    },{
      title:"费用类型",
      dataIndex:'expense_classification'
    },{
      title:"创建时间",
      dataIndex:'create_time'
    },{
      title:"状态",
      dataIndex:'status'
    }
  ]

  chargedItemColumn = [{
    title:"病历号",
    dataIndex:'medical_record_id'
  },{
    title:"缴费名称",
    dataIndex:'item_name'
  },{
    title:"规格",
    dataIndex:'format'
  },{
    title:"单价",
    dataIndex:'price'
  },{
    title:"数量",
    dataIndex:'mount'
  },{
    title:"执行科室",
    dataIndex:'excute_department'
  },{
    title:"费用类型",
    dataIndex:'expense_classification'
  },{
    title:"创建时间",
    dataIndex:'create_time'
  },{
    title:"状态",
    dataIndex:'status'
  },{
    title:"操作",
    render:(data)=>(<Button type="primary">退费</Button>)
  }]
  


  render() {
    return(
    <Tabs defaultActiveKey="1">
      <TabPane tab="待缴费" key="1">
        <Table dataSource={this.props.chargeItems} columns={this.chargeItemColumn}/>
      </TabPane>
      <TabPane tab="历史记录" key="2">
        <Table dataSource={this.props.chargedItems} columns={this.chargedItemColumn}/>
      </TabPane>
    </Tabs>)
  }
}


export default ChargeItemSection;
