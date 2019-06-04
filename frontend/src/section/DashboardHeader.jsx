import React from 'react';
import {Layout ,Avatar} from 'antd';
import { Menu, Dropdown, Icon,Modal,Descriptions,Drawer,Button,Input ,Form} from 'antd';
import axios from 'axios';
import API from '../global/ApiConfig';
import Status from '../global/Status';
import Message from '../global/Message';

const { Header } = Layout;
const confirm = Modal.confirm;

class DashboardHeader extends React.Component {

  state={
    modalVisible:false,
    drawerVisible:false
  }

   //确认关闭
   showExitConfirm() {
    const _this = this;
    confirm({
      title: '退出',
      content: `你确定要退出吗？`,
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {_this.exit();},
      onCancel() {console.log('Cancel clicked');},
    });
  }

  //退出
  exit=async ()=>{
    await axios({
      method: API.logout.method,
      url: API.logout.url,
      data: {},
      crossDomain: true
    }).then((res)=>{
      const data = res.data;
      console.log('receive',data);
    });
    window.location.href = "/login"
  }


   //修改密码成功 重新重新
   showInfoUpdataSuccessConfirm() {
    const _this = this;
    confirm({
      title: '退出',
      content: `个人信息已更改，请重新登录`,
      okText: '退出',
      okType: 'danger',
      onOk() {_this.exit();},
    });
  }

  //提交密码修改
  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
        axios({
          method: API.me.updateInfo.method,
          url: API.me.updateInfo.url,
          data: values,
          crossDomain: true
        }).then((res)=>{
          const data = res.data;
          console.log('receive',data);
          if(data.code===Status.Ok) {
            this.showInfoUpdataSuccessConfirm();
          } else if(data.code===Status.PermissionDenied) {
              Message.showAuthExpiredMessage();
          } else {
              Message.showConfirm('错误',data.msg)
          }}).catch((err)=>{
              Message.showNetworkErrorMessage();
          });
      }
    });
  };

  handleModalOpen=()=>{
    this.setState({modalVisible:true})
  }

  handleModalCancel=()=>{
    this.setState({modalVisible:false})
  }

  handleModalOk=()=>{
    this.setState({modalVisible:false})
  }

  handleDrawerOpen=()=>{
    this.setState({drawerVisible:true})
  }

  handleDrawerClose=()=>{
    this.setState({drawerVisible:false})
  }

  dropdownMenu = (
    <Menu>
      <Menu.Item>
        <a target="_blank" rel="noopener noreferrer" href="javascript:;" onClick={this.handleDrawerOpen.bind(this)}>
          我的信息
        </a>
      </Menu.Item>
      <Menu.Item>
        <a target="_blank" rel="noopener noreferrer" href="javascript:;" onClick={this.handleModalOpen.bind(this)}>
          修改信息
        </a>
      </Menu.Item>
      <Menu.Item>
        <a style={{color:'red'}} target="_blank" rel="noopener noreferrer" href="javascript:;" onClick={this.showExitConfirm.bind(this)}>
          退出
        </a>
      </Menu.Item>
    </Menu>
  );

  render() {
    const state = this.state;
    const me = this.props.me;
    const { getFieldDecorator } = this.props.form;
    const form = this.props.form;
    return (
    <Header style={{ background: '#fff', padding: 0 }} >
      <div style={{float:'left',paddingLeft:10}}>
        <h1>{me.real_name}</h1>
      </div>
      <div style={{float:'left',paddingLeft:10}}>
        <h1>{me.role_name}</h1>
      </div>
      <div style={{float:'left',paddingLeft:10}}>
        <h1>{me.department_name}</h1>
      </div>
      <div style={{float:'left',paddingLeft:10}}>
        <h1>{me.title}</h1>
      </div>
      <div style={{float:'right',paddingRight:10}}>
        <Avatar style={{ backgroundColor: '#87d068' }}>{me.real_name[0]}</Avatar>
        &nbsp;&nbsp;
        <Dropdown overlay={this.dropdownMenu}>
          <a className="ant-dropdown-link" href="#">
            {me.username}<Icon type="down" />
          </a>
        </Dropdown>
      </div>

      <Drawer
        title="我的信息"
        placement="right"
        closable={false}
        onClose={this.handleDrawerClose.bind(this)}
        visible={this.state.drawerVisible}
        width={'600px'}
      >
        <Descriptions title="详情信息" bordered column={2}>
          <Descriptions.Item label="用户名">{me.username}</Descriptions.Item>
          <Descriptions.Item label="真实姓名">{me.real_name}</Descriptions.Item>
          <Descriptions.Item label="科室">{me.department_name}</Descriptions.Item>
          <Descriptions.Item label="角色">{me.role_name}</Descriptions.Item>
          <Descriptions.Item label="职称">{me.title}</Descriptions.Item>
        </Descriptions>
      </Drawer>

      <Modal
        title="修改信息"
        destroyOnClose
        footer={null}
        visible={this.state.modalVisible}
        onOk={this.handleModalOk.bind(this)}
        onCancel={this.handleModalCancel.bind(this)}
      >
        <Form onSubmit={this.handleSubmit} className="login-form">
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: '输入用户名' }],
            initialValue:me.username
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="输入用户名"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('password1', {
            rules: [{ required: true, message: '输入密码' }],
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="输入密码" type="password"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: '两次密码不一致', 
            validator:(rule, value, callback)=>{
              if(value===null || value==="")
                callback("输入密码！");
              if(form.getFieldValue('password1')!=value)
                callback('两次密码不一致');
              else 
                callback();
            }}],
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="再次输入密码" type="password"
            />
          )}
        </Form.Item>
        <Button htmlType="submit" type="primary">修改</Button>
        </Form>
      </Modal>
    </Header>)
  }
}

export default Form.create({ name: 'me_form' })(DashboardHeader);