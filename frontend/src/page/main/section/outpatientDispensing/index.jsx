import React from 'react';
import {Row,Col,Layout,Card,Typography,Pagination, Form, Table, Divider} from 'antd'
import { Tree,Input } from 'antd';

const {Content, Footer} = Layout;

const siderColumn = [
  {
    title:'病历号',
    key:'case_number',
    dataIndex:'case_number'
  },{
    title:'姓名',
    key:'name',
    dataIndex:'name'
  },{
    title:'发票号',
    key:'invoice_number',
    dataIndex:'invoice_number'
  },
]

const data1 = [
  {
    case_number:'0120321000',
    name:'刘金星',
    invoice_number:'1928310381903'
  }
]

const mainColumn = [
  {
    title:'处方号',
    key:1,
    dataIndex:'prescription_number',
    render: (value, row, index) => {
      const obj = {
        children: value,
        props: {},
      };
      if(index===0) obj.props.rowSpan = 3;
      else obj.props.rowSpan=0
      // These two are merged into above cell
     
      return obj;
    },
  },{
    title:'开单医生',
    key:2,
    dataIndex:'billing_doctor'
  },{
    title:'药品名称',
    key:3,
    dataIndex:'drug_name'
  },{
    title:'单价',
    key:4,
    dataIndex:'unit_price'
  },{
    title:'发药数量',
    key:5,
    dataIndex:'dispensing_quantity'
  },{
    title:'可退数量',
    key:6,
    dataIndex:'returnable_quantity'
  },{
    title:'单位',
    key:7,
    dataIndex:'unit'
  },{
    title:'退药数量',
    key:8,
    dataIndex:'withdraw_quantity'
  },{
    title:'退药金额',
    key:9,
    dataIndex:'refund_amount'
  },{
    title:'发药状态',
    key:10,
    dataIndex:'drug_delivery_status'
  }
]

const data2=[{
  prescription_number:361855,
  billing_doctor:'开诊医生',
  drug_name:'脑残片',
  unit_price:16.00,
  dispensing_quantity:1,
  returnable_quantity:1,
  unit:'盒',
  withdraw_quantity:1,
  refund_amount:16.00,
  drug_delivery_status:'已发'
},{
  prescription_number:361855,
  billing_doctor:'开诊医生',
  drug_name:'脑残片',
  unit_price:16.00,
  dispensing_quantity:1,
  returnable_quantity:1,
  unit:'盒',
  withdraw_quantity:1,
  refund_amount:16.00,
  drug_delivery_status:'已发'
},{
  prescription_number:361855,
  billing_doctor:'开诊医生',
  drug_name:'脑残片',
  unit_price:16.00,
  dispensing_quantity:1,
  returnable_quantity:1,
  unit:'盒',
  withdraw_quantity:1,
  refund_amount:16.00,
  drug_delivery_status:'已发'
}]
/*
class OutpatientDispensingForm extends React.Component {
  
}*/

class OutpatientDispensingSection extends React.Component {

  render() {
    return(
      <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Card title="待退药列表" style={{overflow:'scroll',minWidth:'100px',height:'400px'}} >
            
            </Card>

            <Card title="退药列表" style={{overflow:'scroll',minWidth:'100px',height:'400px'}} >
              <Table columns={siderColumn} dataSource={data1}/>
            </Card>
          </Col>

          <Col span={18}>
            <Card title="退药明细信息" style={{minHeight:'800px'}}>
              <Typography.Title style={{textAlign:'center'}}>门诊退药</Typography.Title>
              <Input addonBefore="病例号" disabled style={{width:'30%'}}/>
              <Input addonBefore="患者姓名" disabled  style={{width:'30%'}}/>
              <Input addonBefore="年龄" disabled  style={{width:'30%'}}/>

              <Input addonBefore="阶段类别" disabled  style={{width:'30%'}}/>
              <Input addonBefore="就诊科室" disabled  style={{width:'30%'}}/>
              <Input addonBefore="处方状态" disabled  style={{width:'30%'}}/>

              <Input addonBefore="发药日期" disabled  style={{width:'45%'}}/>
              <Input addonBefore="农合卡号" disabled  style={{width:'45%'}}/>
              <Divider/>
              <Table columns={mainColumn} dataSource={data2}/>
            </Card>
          </Col>
        </Row>
      </Content>)
  }

}

export default OutpatientDispensingSection;