import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Divider, Row,Col,Upload,Icon,Modal, Button} from 'antd';
const { Title } = Typography;
 
class Demo extends React.Component{
  state={
    report:{
      result:`wjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkj
      webfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwe
      bfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjw
      ebfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkj
      webfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkj
      webfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkj
      webfkjwebfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkj
      webfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkek
      dbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwebfkjwbfkwefwjekdbwjed`,
      advice:`wjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkj
      webfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwe
      bfkjwbfkwefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwbfkjwbfk
      wefwjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwebfkjwbfkwef
      wjekdbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwebfkjwbfkwefwjek
      dbwjedbwkedbwekjdbwekjdbkjewbfjkwebfkwejbfkjwebfkjwebfkjwbfkwefwjekdbwjed`,
      images:[

        {name:'wedwdew',url:'http://localhost:8082/files/a8d042f4b223c1ebe0eadbea4e546289.jpeg'},
        {name:'23e23e2',url:'http://localhost:8082/files/a8d042f4b223c1ebe0eadbea4e546289.jpeg'},
        {name:'23e23e2',url:'http://localhost:8082/files/a8d042f4b223c1ebe0eadbea4e546289.jpeg'},
        {name:'23e23e2',url:'http://localhost:8082/files/a8d042f4b223c1ebe0eadbea4e546289.jpeg'}
      ]
    }
  }

  render() {
    const {report} = this.state;
    return(
      <div>
        <Card style={{width:'800px'}}>
          <div style={{textAlign:"center"}}>
            <Typography.Title level={2}>XXX检查报告</Typography.Title>
            <Divider/>
            <span style={{marginRight:'20px'}}>操作员工号：<b>{3423}</b></span>
            <span style={{marginRight:'20px'}}>日期：<b>{"2020-2-2"}</b></span>
            <span>患者：<b>{"CXK"}</b></span>
          </div>

          <Typography.Title level={4}>检查结果：</Typography.Title>
          <Typography.Paragraph>{report.result}</Typography.Paragraph>
          {report.images.map(image=>(
            <img src={image.url} alt={image.name} key={image.url} style={{width:'50%',padding:'5px'}}></img>
          ))}

          <Typography.Title level={4}>建议：</Typography.Title>
          <Typography.Paragraph>{report.advice}</Typography.Paragraph>
        
        </Card>
      </div>
    )
  }
}










class Demo2 extends React.Component {
  state = {
    settlement_category: '自费',
    date: '2015-2-23',
    name: '蔡徐坤',
    gender: '女',
    age: 20,
    outpatient_service: '0000023',
    prescription_number: '234242',
    department: '门诊',
    diagnose: '喉痛',
    cost:'56.62'
  }
 
 
  render() {
    const state = this.state
    const items = [
      {
        name: '酚麻美敏混悬液[100ml]',
        specification: '2瓶',
        unit: '100',
        way: '口服',
        times: '一天两次',
      },
      {
        name: '胆舒胶囊[0.45g*30粒]',
        specification: '1瓶',
        unit: '0.45',
        way: '静脉可注',
        times: '一天一次',
      },
    ];
 
    const listItems = items.map((item) =>
      <div>
        <Row>
          <Col span={6}>
            <b>{item.name}</b>
          </Col>
          <Col span={6}></Col>
          <Col span={6}></Col>
          <Col span={6}>
            {item.specification}
          </Col>
        </Row>
        <Row>
          <Col span={6}>
            {item.unit}
          </Col>
          <Col span={6}></Col>
          <Col span={6}>
            {item.way}
          </Col>
          <Col span={6}>
            {item.times}
          </Col>
        </Row>
      </div>
    );
 
    return (
      <Card style={{ margin: 20, width: '100vh' }}>
        <div style={{ margin: 20 }}>
          <Title style={{ textAlign: 'center' }} level={3}>NEU XXXXXXX 医院 成药处方</Title>
          <Typography.Paragraph >
            <Row >
              <Col span={2}>
                <b>费别：</b>
              </Col>
              <Col span={3}>
                {state.settlement_category}
              </Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}>
                <b>开具日期：</b>
              </Col>
              <Col span={3}>
                {state.date}
              </Col>
            </Row>
            <Row>
              <Col span={2}>
                <b>姓名：</b>
              </Col>
              <Col span={3}>
                {state.name}
              </Col>
              <Col span={3}></Col>
              <Col span={2}>
                <b>性别：</b>
              </Col>
              <Col span={4}>
                {state.gender}
              </Col>
              <Col span={3}></Col>
              <Col span={3}>
                <b>门诊号：</b>
              </Col>
              <Col span={3}>
                {state.outpatient_service}
              </Col>
            </Row>
            <Row>
              <Col span={2}>
                <b>年龄：</b>
              </Col>
              <Col span={3}>
                {state.age}
              </Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}></Col>
              <Col span={3}>
                <b>处方号：</b>
              </Col>
              <Col span={3}>
                {state.prescription_number}
              </Col>
            </Row>
            <Row>
              <Col span={2}>
                <b>科室：</b>
              </Col>
              <Col span={3}>
                {state.department}
              </Col>
              <Col span={3}></Col>
              <Col span={2}>
                <b>诊断：</b>
              </Col>
              <Col span={3}>
                {state.diagnose}
              </Col>
              <Col span={3}>
              </Col>
            </Row>
          </Typography.Paragraph>
 
          <Divider style={{ marginTop: 0, marginBottom: 10 }} />
          <Row >
            <Col>
              {listItems}
            </Col>
          </Row>
          <Divider />
 
          <Typography.Paragraph >
            <Row>
              <Col span={2}>
            <b>医师：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            <Col span={2}>
            <b>审核：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            <Col span={3}>
            <b>药品金额：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{state.cost}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            </Row>
            <Row>
            <Col span={2}>
            <b>调配：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            <Col span={2}>
            <b>核对：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            <Col span={3}>
            <b>发药：</b>
            </Col>
            <Col span={4}>
              <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u>
            </Col>
            </Row>
          </Typography.Paragraph>
        </div>
      </Card>
    );
  }
}

export default Demo