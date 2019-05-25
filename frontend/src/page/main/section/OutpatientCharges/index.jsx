import React from 'react';
import {Card,Form,Layout,Row,Col, Table} from 'antd'
import ChargeForm from './ChargeForm'

const {Content} = Layout;


const column = [
  {
    title:"项目名称",
    dataIndex:"name"
  },{
    title:"规格",
    dataIndex:"specifications"
  },{
    title:"单价",
    dataIndex:"single_price"
  },{
    title:"数量",
    dataIndex:"quantity"
  },{
    title:"单位",
    dataIndex:"unit"
  },{
    title:"付款状态",
    dataIndex:"status"
  },{
    title:"金额",
    dataIndex:"cost"
  },{
    title:"执行科室",
    dataIndex:"excute_department"
  }
]

const data = [
  {
    code:"099023",
    key:"6"
  }
]

class OutpatientCharges extends React.Component {
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

export default OutpatientCharges;