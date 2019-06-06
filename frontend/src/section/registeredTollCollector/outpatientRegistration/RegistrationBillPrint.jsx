import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Layout, Divider, Row,Col, Button} from 'antd';
const { Title } = Typography;


class RegistrationBill extends React.Component {

  render() {
    //bill from props

    const bill={
      "medical_record_id" : 10000002, //病历号
      "name" : 'www', //操作员,
      "department_name" : "菜徐坤",
      "cost": 100,
      "create_time": "2019-6-4 14:10"
    }

    return (
      <Card style={{margin:20,width:'300px'}}>
        <div style={{margin:20}}>
        <Title style={{textAlign:'center'}}  level={3}>门诊挂号单</Title>
        <Divider/>
          <p><b>病例号：</b>{bill.medical_record_id}</p>
          <p><b>姓  名：</b>{bill.name}</p>
          <p><b>科  室：</b>{bill.department_name}</p>
          <p><b>金  额：</b>{bill.cost}</p>
          <p><b>日  期：</b>{bill.create_time}</p>
        <Divider style={{marginTop:0,marginBottom:10}}/>
        </div>
      </Card>
    );
  }
}

class RegistrationBillPrint extends React.Component {
  render() {
    return (
      <div>
        <ReactToPrint
          trigger={() => <Button type="primary">打印</Button>}
          content={() => this.componentRef}
        />
        <RegistrationBill ref={el => (this.componentRef = el)} />
      </div>
    );
  }
}

export default RegistrationBillPrint;