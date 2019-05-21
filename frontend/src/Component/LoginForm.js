import React from 'react';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import axios from 'axios';
import API from '../Config/ApiConfig'

class LoginForm extends React.Component {

  state = {errorMsg:''}

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received login values of form: ', values);
        axios({
          method: 'post',
          url: API.login,
          data: values,
          crossDomain: true
        }).then((res)=>{
          const data = res.data;
          console.log(data)
          if(data.ok) {
            window.location.href = "/main"
          } else {
            form.setState({errorMsg:data.msg})
          }
        });
      }
    });
  };

  handleChange = (e)=> {
    this.setState({errorMsg:''})
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} onChange={this.handleChange} className="login-form">
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: '输入用户名' }],
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="用户名"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: '输入密码' }],
          })(
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="密码"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('remember', {
            valuePropName: 'checked',
            initialValue: true,
          })(<Checkbox>自动登录</Checkbox>)}
          &nbsp;&nbsp;
          <a className="login-form-forgot" href="">
            忘记密码
          </a>
          <br/>
          <span style={{color:'red'}}>{this.state.errorMsg}</span>
          <Button type="primary" block htmlType="submit" className="login-form-button">
            登录
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

const WrappedLoginForm = Form.create({ name: 'normal_login' })(LoginForm);

export default WrappedLoginForm;