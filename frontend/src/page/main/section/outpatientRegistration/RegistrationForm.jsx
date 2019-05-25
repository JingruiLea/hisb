import React from 'react';
import {Typography,Form,Input,Icon,Col, Select, Row,DatePicker,Switch,Button} from 'antd'

const { Option } = Select;


class RegistrationForm extends React.Component {
  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Receivedvalues of form: ', values);
        console.log(values)
      }
    });
  };


  render() {
    var ageArr = [0];for(var i=0;i<150;i++) ageArr.push(i);
    const {getFieldDecorator} = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    };

    return(<Form onSubmit={this.handleSubmit.bind(this)}>
      <Row style={{textAlign:'center'}}>
        <Col span={6} style={{display:'block'}}>
          <Form.Item label="病历号" {...formItemLayout}>
            {getFieldDecorator('medical_record_number', {
              rules: [{ required: true, message: '输入病历号' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="病历号"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="姓名" {...formItemLayout}>
            {getFieldDecorator('name', {
              rules: [{ required: true, message: '输入姓名' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="姓名"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="性别" {...formItemLayout}>
            {getFieldDecorator('gender', {
              rules: [{ required: true, message: '选择性别' }],
            })(
              <Select>
                <Option value="male">男</Option>
                <Option value="female">女</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="年龄" {...formItemLayout}>
            {getFieldDecorator('age', {
              rules: [{ required: true, message: '输入年龄' }],
            })(
              <Select>
                {ageArr.map(i=>(<Option value={i} key={i}>{i}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
      </Row>
      
      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="医疗类别" {...formItemLayout}>
            {getFieldDecorator('medical_category', {
              rules: [{ required: true, message: '选择医疗类别' }],
            })(
              <Select>
                <Option value="？？">？？</Option>
                <Option value="？？？">？？？</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="医疗证号" {...formItemLayout}>
            {getFieldDecorator('medical_certificate_number', {
              rules: [{ required: true, message: '输入医疗证号' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="医疗证号"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="身份证号" {...formItemLayout}>
            {getFieldDecorator('id_number', {
              rules: [{ required: true, message: '输入身份证号' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="身份证号"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="家庭住址" {...formItemLayout}>
            {getFieldDecorator('address', {
              rules: [{ required: true, message: '输入家庭住址' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="家庭住址"
              />
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="结算类别" {...formItemLayout}>
            {getFieldDecorator('settlement_category', {
              rules: [{ required: true, message: '选择挂号来源' }],
            })(
              <Select>
                <Option value="窗口挂号">窗口挂号</Option>
                <Option value="？？挂号">？？挂号</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="号别" {...formItemLayout}>
            {getFieldDecorator('registration_category', {
              rules: [{ required: true, message: '选择好别' }],
            })(
              <Select>
                <Option value="普通号">普通号</Option>
                <Option value="专家号">专家号</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号科室" {...formItemLayout}>
            {getFieldDecorator('registration_department', {
              rules: [{ required: true, message: '选择挂号科室' }],
            })(
              <Select>
                <Option value="？？？">？？？</Option>
                <Option value="？？">？？</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="门诊医生" {...formItemLayout}>
            {getFieldDecorator('outpatent_doector', {
              rules: [{ required: true, message: '选择门诊医生' }],
            })(
              <Select>
                <Option value="？？？？？">？？？</Option>
                <Option value="？？">？？</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="出生日期" {...formItemLayout}>
            {getFieldDecorator('birthday', {
              rules: [{ required: true, message: '输入出生日期' }],
            })(
              <DatePicker
                placeholder="出生日期"       
              />,
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="看诊日期" {...formItemLayout}>
            {getFieldDecorator('consultation_date', {
              rules: [{ required: true, message: '输入看诊日期' }],
            })(
              <DatePicker
                placeholder="看诊日期"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号来源" {...formItemLayout}>
            {getFieldDecorator('medical_record_number', {
              rules: [{ required: true, message: '选择挂号来源' }],
            })(
              <Select>
                <Option value="窗口挂号">窗口挂号</Option>
                <Option value="？？挂号">？？挂号</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="医保诊断" {...formItemLayout}>
            {getFieldDecorator('medical_insurance_diagnosis', {
              rules: [{ required: true, message: '输入医保诊断' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="医保诊断"
              />
            )}
          </Form.Item>
        </Col>
      </Row>

      <div>
        <Button htmlType="submit" type="primary" style={{float:'right',marginTop:10,marginLeft:30}} size="large">挂号</Button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <Switch checkedChildren="要病例本" unCheckedChildren="不要病例本" style={{float:'right',marginTop:20,marginLeft:30}}/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <Typography.Title style={{float:'right'}}>
          ￥{300.00}元
        </Typography.Title>
        <span style={{float:'right',marginTop:20}}>应收：</span>
      </div>

    </Form>)
  }
}

export default Form.create({name:'registration_form'})(RegistrationForm);