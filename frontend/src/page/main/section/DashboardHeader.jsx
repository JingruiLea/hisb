import React from 'react';
import {Layout ,Avatar} from 'antd';
import { Menu, Dropdown, Icon } from 'antd';

const { Header } = Layout;
const dropdownMenu = (
  <Menu>
    <Menu.Item>
      <a target="_blank" rel="noopener noreferrer" href="http://www.alipay.com/">
        1st menu item
      </a>
    </Menu.Item>
    <Menu.Item>
      <a target="_blank" rel="noopener noreferrer" href="http://www.taobao.com/">
        2nd menu item
      </a>
    </Menu.Item>
    <Menu.Item>
      <a target="_blank" rel="noopener noreferrer" href="http://www.tmall.com/">
        3rd menu item
      </a>
    </Menu.Item>
  </Menu>
);



class DashboardHeader extends React.Component {
   
  state = {
    username:'loading'
  }

  render() {
    const state = this.state;

    return (
    <Header style={{ background: '#fff', padding: 0 }} >
      <div style={{float:'left',paddingLeft:10}}>
        <h1>东软医院管理系统</h1>
      </div>
      <div style={{float:'right',paddingRight:10}}>
        <Avatar style={{ backgroundColor: '#87d068' }} icon="user" />
        &nbsp;&nbsp;
        <Dropdown overlay={dropdownMenu}>
          <a className="ant-dropdown-link" href="#">
            {state.username}<Icon type="down" />
          </a>
        </Dropdown>
      </div>
    </Header>)
  }
}

export default DashboardHeader;