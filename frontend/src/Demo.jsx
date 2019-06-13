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
    chief_complaint:'',
    present_illness_histroy:'',
    past_history:'',
    physical_examination:'',
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


class Example extends React.Component {
  render() {
    return (
      <div>
        <ReactToPrint
          trigger={() => <a href="#">Print this out!</a>}
          content={() => this.componentRef}
        />
        <ComponentToPrint ref={el => (this.componentRef = el)} />


        <Demo2/>
      </div>
    );
  }
}

export default Example