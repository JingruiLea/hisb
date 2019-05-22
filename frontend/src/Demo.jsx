import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Layout, Divider, Row,Col} from 'antd';
const { Title } = Typography;


class ComponentToPrint extends React.Component {
  state = {
    name:'蔡徐坤',
    gender:'女',
    age:20,
    data_of_onset:'2015-2-23',
    outpatient_service:'0000023',
    prescription_number:'234242',
    chief_complaint:'白带异常',
    present_illness_histroy:'白带异常',
    past_history:'白带异常',
    physical_examination:'白带异常',
    depuy_examination:'无',
    preliminary_diagnosis:'哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈',
    handling_opinions:[
      'hhhhhhh',
      'hhhhhh'
    ]
  }

  render() {
    const state = this.state

    return (
      <Card style={{margin:20,width:'100vh'}}>
        <div style={{margin:20}}>
        <Title style={{textAlign:'center'}}  level={3}>NEU XXXXXXX 医院</Title>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>姓名：</b>{state.name}<span style={{marginLeft:70}}/>
          <b>性别：</b>{state.gender}<span style={{marginLeft:70}}/>
          <b>年龄：</b>{state.age}
        </Typography.Paragraph>
        <Divider style={{marginTop:0,marginBottom:10}}/>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>发病日期：</b>{state.data_of_onset}<span style={{marginLeft:70}}/>
          <b>门诊号：</b>{state.outpatient_service}<span style={{marginLeft:70}}/>
          <b>处方号：</b>{state.prescription_number}
        </Typography.Paragraph>
        <Divider style={{marginTop:0,marginBottom:10}}/>

        <Row>
          <Col span={4}><b>主诉：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.chief_complaint}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>现病史：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.present_illness_histroy}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>既往史：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.past_history}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>体格检查：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.physical_examination}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>辅助检查：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.depuy_examination}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>初步诊断：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.preliminary_diagnosis}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>处理意见：</b></Col>
          <Col span={20}><Typography.Paragraph>{state.handling_opinions.map((x)=>(<Typography.Paragraph>{x}</Typography.Paragraph>))}</Typography.Paragraph></Col>
        </Row>
        <Divider/>
        <Typography.Paragraph><b>以下空白</b></Typography.Paragraph>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>患者签字：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></b>
          <b>医生签字：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></b>
        </Typography.Paragraph>
        <Typography><b>注意：治疗期间出现新症状或病情加重请及时复诊，病情平稳，请按医生要求复诊！</b></Typography>
        </div>
      </Card>
    );
  }
}

class Example extends React.Component {
  render() {
    return (
      <div>
        <ReactToPrint
          trigger={() => <a href="#">Print this out!</a>}
          content={() => this.componentRef}
        />
        <ComponentToPrint ref={el => (this.componentRef = el)} />
      </div>
    );
  }
}

export default Example;