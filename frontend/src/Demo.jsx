import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Divider, Row,Col,Upload,Icon,Input, Button, Form, InputNumber} from 'antd';
const { Title } = Typography;

class Demo extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log(values)
      }
    });
  };

  render(){
    const { getFieldDecorator } = this.props.form;

    return (<Form onSubmit={this.handleSubmit.bind(this)}>
       <Form.Item label="科室编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入科室编号' }]
        })(
          <InputNumber
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="重要字段 请谨慎填写"
          />
        )}
      </Form.Item>
      <Button type="primary" htmlType="submit">submit</Button>
    </Form>)
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

export default Form.create({name:'ss22ss3'})(Demo)