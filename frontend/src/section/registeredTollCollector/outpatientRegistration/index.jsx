import React from 'react';
import {Card,Form,Layout,Row,Col, Table} from 'antd'
import RegistrationForm from './RegistrationForm'

const {Content} = Layout;


const column = [
  {
    title:"病例号",
    dataIndex:"code"
  },{
    title:"姓名",
    dataIndex:"name"
  },{
    title:"性别",
    dataIndex:"gender"
  },{
    title:"出生日期",
    dataIndex:"birthday"
  },{
    title:"身份证号",
    dataIndex:"patient_id"
  },{
    title:"发票号",
    dataIndex:"c_"
  },{
    title:"结算类型",
    dataIndex:"type"
  },{
    title:"挂号级别",
    dataIndex:"level"
  },{
    title:"挂号日期",
    dataIndex:"time"
  },{
    title:"看诊日期",
    dataIndex:"yes"
  },{
    title:"是否已诊",
    dataIndex:"wd"
  },{
    title:"是否收取病历本",
    dataIndex:"codqwde"
  },{
    title:"状态",
    dataIndex:"qwdwed"
  },{
    title:"实收费用",
    dataIndex:"cogede"
  },{
    title:"看诊科室",
    dataIndex:"cvfdvode"
  },
]

const data = [
  {
    code:"099023",
    key:"6"
  }
]

class OutpatientRegistration extends React.Component {
  render() {
    return(<Content style={{ margin: '0 16px',paddingTop:5 }}>
      <Card title="挂号信息">
        <RegistrationForm/>
      </Card>
      <Card title="挂号信息列表">
        <Table columns={column} dataSource={data}/>
      </Card>
    </Content>)
  }
}

export default OutpatientRegistration;