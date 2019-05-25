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
    dataIndex:"code"
  },{
    title:"性别",
    dataIndex:"code"
  },{
    title:"出生日期",
    dataIndex:"code"
  },{
    title:"身份证号",
    dataIndex:"code"
  },{
    title:"发票号",
    dataIndex:"code"
  },{
    title:"结算类型",
    dataIndex:"code"
  },{
    title:"挂号级别",
    dataIndex:"code"
  },{
    title:"挂号日期",
    dataIndex:"code"
  },{
    title:"看诊日期",
    dataIndex:"code"
  },{
    title:"是否已诊",
    dataIndex:"code"
  },{
    title:"是否收取病历本",
    dataIndex:"code"
  },{
    title:"状态",
    dataIndex:"code"
  },{
    title:"实收费用",
    dataIndex:"code"
  },{
    title:"看诊科室",
    dataIndex:"code"
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