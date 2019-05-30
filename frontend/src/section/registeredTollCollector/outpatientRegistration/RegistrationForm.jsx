import React from 'react';
import {Typography,Form,Input,Icon,Col, Select, Row,DatePicker,Radio,Button, AutoComplete} from 'antd'
import moment from 'moment';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import axios from 'axios'

const { Option } = Select;
const RadioGroup = Radio.Group;

class RegistrationForm extends React.Component {

  state = {
    //初始化加载
   /* departments:[ //所有科室
      {name:"骨科",id:1},
      {name:"脑壳",id:2}
    ],
    defaultRegistrationLevel:{ //默认的挂号等级
      name:"普通号",id:2,price:10
    },
    registrationLevel:[ //所有挂号等级 
      {name:"专家号",id:1,price:20},
      {name:"普通号",id:2,price:10}
    ],
    settlementCategory:[ //所有支付方式
      {name:"现金",id:1},
      {name:"农行卡",id:2},
      {name:"支付宝",id:3}
    ],
    */

    outPatientDoctor:[],//[{name:"lijinrui",id:10}], //自动同步
    cost:0
  }

  componentDidMount=()=>{
    //console.log(moment())
  }

  selectDepartment = async(value)=>{
    console.log('select',value)
    await this.setState({departmentId:value})
    this.syncDoctorList();
  }

  handleRegistrationLevelChange = async (registrationLevelId) =>{
    await this.setState({registrationLevelId})
    console.log('registrationLevelId',registrationLevelId)
    this.syncDoctorList()
  }

  //同步医生列表
  syncDoctorList=async()=>{
    console.log(this.state.departmentId,this.state.registrationLevelId)
    var registrationLevelId = 0;
    if(this.props.defaultRegistrationLevel!==null) 
      await this.setState({registrationLevelId:this.props.defaultRegistrationLevel.id})
    if(this.state.departmentId===undefined || this.state.registrationLevelId===undefined)
      return;
    const data={
      department_id:this.state.departmentId,
      registration_level_id:this.state.registrationLevelId
    }
    console.log('post sync doctor',data)
    this.props.syncDoctorList(data);
  }

  handleAgeChange = age=>{
    const now = moment();
    const birthday = now.subtract(age,'years')
    console.log(birthday)
    this.props.form.setFieldsValue({birthday})
  }

  handleBirthdayChange = birthday=>{
    const birthYear = birthday.year();
    const nowYear = moment().year();
    const age = nowYear - birthYear;
    this.props.form.setFieldsValue({age}) 
  }

  //表单提交
  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const birthday = values.birthday.format('YYYY-MM-DD hh:mm:ss');
        const consultation_date = values.consultation_date.format('YYYY-MM-DD hh:mm:ss')
        values.birthday = birthday;
        values.consultation_date = consultation_date; 
        console.log('submit ',values)
        //非支付状态 请求价格
        if(!this.props.payMode)
          this.props.calculateFee(values)
        //支付状态确认 打印表单
        else 
          this.props.submitRegistration(values)
      }
    });
  };

  cancelPaymentMode=()=>{this.props.setPaymentMode(false);}

  render() {
    //年龄列表
    var ageArr = [];for(var i=0;i<150;i++) ageArr.push(i);
    const {getFieldDecorator,setFieldsValue} = this.props.form;
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
        <Col span={6}>
          <Form.Item label="姓名" {...formItemLayout}>
            {getFieldDecorator('name', {
              rules: [{ required: true, message: '输入姓名' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="姓名" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="性别" {...formItemLayout}>
            {getFieldDecorator('gender', {
              rules: [{ required: true, message: '选择性别' }],
              initialValue:"male"
            })(
              <Select disabled={this.props.payMode}>
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
              validator: (rule, value, callback) =>  {
                if(value>150 || value < 0) callback("不合理的年龄")
              }
            })(
              <Select onChange={this.handleAgeChange.bind(this)} disabled={this.props.payMode}>
                {ageArr.map(i=>(<Option value={i} key={i}>{i}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="出生日期" {...formItemLayout}>
            {getFieldDecorator('birthday', {
              rules: [{ required: true, message: '输入出生日期' }]
            })(
              <DatePicker 
                disabled={this.props.payMode}
                onChange={this.handleBirthdayChange.bind(this)}
                placeholder="出生日期"       
              />,
            )}
          </Form.Item>
        </Col>
      </Row>
      
      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="医疗类别" {...formItemLayout}>
            {getFieldDecorator('medical_category', {
              rules: [{ required: true, message: '选择医疗类别' }],
              initialValue:"default"
            })(
              <Select disabled={this.props.payMode}>
                <Option value="default">就诊</Option>
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
                addonBefore={
                  getFieldDecorator('medical_certificate_number_type',{
                    initialValue:"id"
                  })(
                    <Select style={{ width: 90 }} disabled={this.props.payMode}>
                      <Option value={"id"}>身份证</Option>
                      <Option value={"insurance_card"}>医保卡</Option>
                    </Select>
                  )
                }
                placeholder="医疗证号"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号来源" {...formItemLayout}>
            {getFieldDecorator('registration_source', {
              rules: [{ required: true, message: '选择挂号来源' }],
              initialValue:"local"
            })(
              <Select disabled={this.props.payMode}>
                <Option value="local">窗口挂号</Option>
                <Option value="net">网路挂号</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="家庭住址" {...formItemLayout}>
            {getFieldDecorator('address', {})(
              <Input
                prefix={<Icon type="address" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="家庭住址" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="挂号科室" {...formItemLayout}>
            {getFieldDecorator('department_id', {
              rules: [{ required: true, message: '选择挂号科室' }],
            })(
                <Select
                  onChange={this.selectDepartment.bind(this)}
                  showSearch
                  placeholder="选择挂号科室"
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }
                  disabled={this.props.payMode}
              >
                {this.props.departments.map(x=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号级别" {...formItemLayout}>
            {getFieldDecorator('registration_level_id', {
              rules: [{ required: true, message: '选择挂号级别' }],
              initialValue:this.props.defaultRegistrationLevel.id
            })(
              <Select onChange={this.handleRegistrationLevelChange.bind(this)} disabled={this.props.payMode}>
                {this.props.registrationLevel.map((x)=>
                  (<Option key={x.id} value={x.id}>{x.name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="门诊医生" {...formItemLayout}>
            {getFieldDecorator('outpatent_doctor_id', {
              rules: [{ required: true, message: '选择门诊医生' }],
            })(
              <Select disabled={this.props.outPatientDoctors.length===0} disabled={this.props.payMode}>
                {this.props.outPatientDoctors.map((x)=>
                  (<Option key={x.uid} value={x.uid}>{x.real_name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="结算类别" {...formItemLayout}>
            {getFieldDecorator('settlement_category_id', {
              rules: [{ required: true, message: '选择结算类别' }],
            })(
              <Select disabled={this.props.payMode}>
                {this.props.settlementCategory.map((x)=>
                  (<Option key={x.id} value={x.id}>{x.name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="医保诊断" {...formItemLayout}>
            {getFieldDecorator('medical_insurance_diagnosis', {})(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="医保诊断" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>

        <Col span={6}>
          <Form.Item label="看诊日期" {...formItemLayout}>
            {getFieldDecorator('consultation_date', {
              rules: [{ required: true, message: '输入看诊日期' }],
              initialValue:moment()
            })(
              <DatePicker
                placeholder="看诊日期"
                showToday
                disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>

        <Col span={6}>
          <Form.Item label="病例本" {...formItemLayout}>
            {getFieldDecorator('has_record_book', {
              initialValue:0
            })(
              <RadioGroup disabled={this.props.payMode}>
              <Radio value={1}>要</Radio>
              <Radio value={0}>不要</Radio>
              </RadioGroup>
            )}
          </Form.Item>
        </Col>
      </Row>

      <div>
        <Button 
          style={{float:'right',marginTop:25,marginLeft:30}} size="large"
          hidden={!this.props.payMode}
          onClick={this.cancelPaymentMode.bind(this)}>
          取消
        </Button>
        <Button 
          htmlType="submit" 
          type="primary" 
          hidden={this.props.payMode}
          style={{float:'right',marginTop:25,marginLeft:30}} size="large">
          挂号
        </Button>
        <Button 
          htmlType="submit" 
          type="danger" 
          hidden={!this.props.payMode}
          style={{float:'right',marginTop:25,marginLeft:30}} size="large">
          付款
        </Button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        {this.props.payMode?
        <div>
        <Typography.Title style={{float:'right'}}>
          {this.props.cost}元
        </Typography.Title>
        <span style={{float:'right',marginTop:20}}>应收：</span>
        </div>
        :null}
      </div>

    </Form>)
  }
}

export default Form.create({name:'registration_form'})(RegistrationForm);