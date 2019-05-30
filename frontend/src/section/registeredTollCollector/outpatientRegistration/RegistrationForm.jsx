import React from 'react';
import {Typography,Form,Input,Icon,Col, Select, Row,DatePicker,Radio,Button, AutoComplete} from 'antd'
import moment from 'moment';

const { Option } = Select;
const RadioGroup = Radio.Group;

class RegistrationForm extends React.Component {

  state = {
    //初始化加载
    departments:[ //所有科室
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

    outPatientDoctor:[{name:"lijinrui",id:10}], //自动同步


    registrationLevelId:2
  }

  componentDidMount=()=>{
    //console.log(moment())
  }

  selectDepartment = async(value)=>{
    console.log('select',value)
    var departmentId = 0;
    for(var i=0;i<this.state.departments.length;i++)
      if(this.state.departments[i].name===value) {
        departmentId = this.state.departments[i].id;
        break;
    }
    await this.setState({departmentId})
    this.syncDoctorList();
  }

  handleRegistrationLevelChange = async (registrationLevelId) =>{
    await this.setState({registrationLevelId})
    console.log('registrationLevelId',registrationLevelId)
    this.syncDoctorList()
  }

  syncDoctorList=()=>{
    console.log(this.state.departmentId,this.state.registrationLevelId)
    if(this.state.departmentId===undefined || this.state.registrationLevelId===undefined)
      return;
    const data={
      departmentId:this.state.departmentId,
      registrationLevelId:this.state.registrationLevelId
    }
    console.log('post',data)
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

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        const birthday = values.birthday.format('YYYY-MM-DD hh:mm:ss');
        const consultation_date = values.consultation_date.format('YYYY-MM-DD hh:mm:ss')
        values.birthday = birthday;
        values.consultation_date = consultation_date; 
        console.log(values)
      }
    });
  };


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
                placeholder="姓名"
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
              validator: (rule, value, callback) =>  {
                if(value>150 || value < 0) callback("不合理的年龄")
              }
            })(
              <Select onChange={this.handleAgeChange.bind(this)}>
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
              <Select>
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
                    <Select style={{ width: 90 }}>
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
              <Select>
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
                placeholder="家庭住址"
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
                  showSearch
                  placeholder="选择挂号科室"
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }
              >
                {this.state.departments.map(x=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号级别" {...formItemLayout}>
            {getFieldDecorator('registration_level_id', {
              rules: [{ required: true, message: '选择挂号级别' }],
              initialValue:this.state.defaultRegistrationLevel.id
            })(
              <Select onChange={this.handleRegistrationLevelChange.bind(this)}>
                {this.state.registrationLevel.map((x)=>
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
              <Select disabled={this.state.outPatientDoctor.length===0}>
                {this.state.outPatientDoctor.map((x)=>
                  (<Option key={x.id} value={x.id}>{x.name}</Option>)
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
              <Select>
                {this.state.settlementCategory.map((x)=>
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
                placeholder="医保诊断"
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
              />
            )}
          </Form.Item>
        </Col>

        <Col span={6}>
          <Form.Item label="病例本" {...formItemLayout}>
            {getFieldDecorator('has_record_book', {
              initialValue:0
            })(
              <RadioGroup>
              <Radio value={1}>要</Radio>
              <Radio value={0}>不要</Radio>
              </RadioGroup>
            )}
          </Form.Item>
        </Col>
      </Row>

      <div>
        <Button htmlType="submit" type="primary" style={{float:'right',marginTop:10,marginLeft:30}} size="large">挂号</Button>
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