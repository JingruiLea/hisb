import React from 'react';
import { Radio, Button,Input,Modal,Form,Icon,Select,DatePicker,message} from 'antd';
import moment from 'moment';

const Option = Select.Option
const RadioGroup = Radio.Group;

const dateFormat = 'YYYY-MM-DD';

function onChange(value) {
  console.log('onOk: ', value);
  value = moment(value).format('YYYY-MM-DD')
}

function disabledDate(current) {
  // Can not select days before today and today
  return current && current < moment().endOf('day');
}

class AddRowForm extends React.Component {

  //初始化加载数据
componentDidMount = ()=>{

}

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    const _this = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        var c = setTimeout(_this.props.addRowConflict(values),1000);
        c = _this.props.conflict;
        //this.props.newScheduleRow(values);
        if(c==0){
          this.props.newScheduleRow(values);
        }else if(c==1){
          this.props.exit();
          message.info('该排班信息已存在，添加失败');
        }else{
          console.log('error',c);
        }
        this.props.exit();
      }
    });
  };

  handleInputChange = (e) =>{
    let value = e.target.value;
    (this.props.getAddTableInfo(value)).then(res=>{
      console.log('res',res);
      window.res = res;
      if(res.length!=0){
      this.props.form.setFieldsValue({
        //department_name: `${res[0].department_name}`,
        id:`${res[0].id}`,
        residue:`${res[0].residue}`,
        valid:"有效",
        department_name:`${res[0].department_name}`,
        shift:`${res[0].shift}`,
        registration_Level:`${res[0].registration_Level}`
      });
    }
    },error=>{
      console.log(error)
    })
    console.log('value',value);
    //console.log('returnData',returnData);
   // this.props.form.setFieldsValue({
   //   department_name: `Hi, ${value === 'xiaoA' ? 'man' : 'lady'}!`,
   // });
  };
  




  render() {
    const { getFieldDecorator } = this.props.form;
    const data = this.props.data;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 },
      },
    };

    const config = {
      rules: [{ type: 'object', required: true, message: 'Please select time!' }],
    };
   

    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
      <Form.Item label="姓名">
       {getFieldDecorator('name', {
         rules: [{ required: true, message: '输入姓名' }],
       })(
         <Input onChange={this.handleInputChange} />,
       )}
     </Form.Item>
     <Form.Item label="ID">
       {getFieldDecorator('id', {
         rules: [{ required: true, message: '输入ID' }],
       })(
         <Input onChange={this.handleInputChange} />,
       )}
     </Form.Item>     
     <Form.Item label="排班时间">
     {getFieldDecorator('schedule_date', config)(
     <DatePicker
      format={dateFormat} 
      disabledDate={disabledDate}
      />)}
        </Form.Item>

     <Form.Item label="午别">
       {getFieldDecorator('shift', {
         rules: [{ required: true, message: '输入午别' }],
       })(
        <Select initialValue="上午" style={{ width: 120 }}>
          <Option value="上午">上午</Option>
          <Option value="下午">下午</Option>
          <Option value="全天">全天</Option>
        </Select>,
       )}
     </Form.Item>
     <Form.Item label="科室">
       {getFieldDecorator('department_name', {
         rules: [{ required: true, message: '输入科室' }],
       })(
         <Input/>,
       )}
     </Form.Item>

     <Form.Item label="挂号级别">
       {getFieldDecorator('registration_Level', {
         rules: [{ required: true, message: '输入挂号级别' }],
       })(
        <Select initialValue="专家号" style={{ width: 120 }}>
          <Option value="专家号">专家号</Option>
        <Option value="普通号">普通号</Option>
        </Select>,
       )}
     </Form.Item>
     <Form.Item label="排班限额">
       {getFieldDecorator('reg_limit', {
         rules: [{ required: true, message: '输入排班限额' }],
       })(
         <Input
           prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
           placeholder="排班限额"
         />,
       )}
     </Form.Item>
     <Form.Item label="剩余号数">
       {getFieldDecorator('residue', {
         rules: [{ required: true, message: '输入剩余号数' }],
       })(
         <Input/>,
       )}
     </Form.Item>
     <Form.Item label="有效状态">
       {getFieldDecorator('valid', {
         rules: [{ required: true, message: '输入有效状态' }],
       })(
         <Input/>,
       )}
     </Form.Item>
     <Button htmlType="submit" type="primary">提交</Button>
     </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);
