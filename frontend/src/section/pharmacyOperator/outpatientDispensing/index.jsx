import React from 'react';
import {Row,Col,Layout,Card,Typography, Input, Table, Divider} from 'antd';
import Sider from './sider';
import { Descriptions } from 'antd';

const {Content, Footer} = Layout;



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
  prescription_number:361856,
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


class OutpatientDispensingSection extends React.Component {

  state = {
    uid:1
  }

  render() {
    return(
      <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Sider/>
          </Col>

          <Col span={18}>
            <Card title="用户处方明细" style={{minHeight:'800px'}}>
  
              <Descriptions bordered title="基本信息" border >
                <Descriptions.Item label="病例号">{}</Descriptions.Item>
                <Descriptions.Item label="患者姓名">Prepaid</Descriptions.Item>
                <Descriptions.Item label="年龄">18:00:00</Descriptions.Item>
                <Descriptions.Item label="就诊科室">$80.00</Descriptions.Item>
                <Descriptions.Item label="处方状态">$20.00</Descriptions.Item>
                <Descriptions.Item label="发票号码">$60.00</Descriptions.Item>
                <Descriptions.Item label="发药日期">$60.00</Descriptions.Item>
              </Descriptions>

              <Divider/>

              <Table 
                title={()=>(<span style={{fontSize:'20px'}}><b>处方信息</b></span>)} 
                columns={mainColumn} 
                dataSource={data2}/>
            </Card>
          </Col>
        </Row>
      </Content>)
  }

}

export default OutpatientDispensingSection;