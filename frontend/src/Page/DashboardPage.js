import React from 'react';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';

import DashboardRouter from '../Router/DashboardRouter'

const { Header, Content, Footer, Sider } = Layout;
const SubMenu = Menu.SubMenu;

class DashboardPage extends React.Component {
  state = {
    collapsed: false,
  };

  onCollapse = collapsed => {
    console.log(collapsed);
    this.setState({ collapsed });
  };

  render() {
    return (
      <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
          <div style={{height:'32px',margin:'16px'}}>
          <h1 style={{color:'white',textAlign:'center'}}>HIS</h1>
          </div>

          <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
            <Menu.Item key="1">
              <Icon type="pie-chart" />
              <span>科室管理</span>
            </Menu.Item>
            <Menu.Item key="2">
              <Icon type="desktop" />
              <span>用户管理</span>
            </Menu.Item>
            <SubMenu
              key="sub1"
              title={
                <span>
                  <Icon type="user" />
                  <span>挂号级别管理</span>
                </span>
              }
            >
              <Menu.Item key="3">哈哈哈哈</Menu.Item>
              <Menu.Item key="4">哈哈哈哈</Menu.Item>
              <Menu.Item key="5">哈哈哈哈</Menu.Item>
            </SubMenu>
            <SubMenu
              key="sub2"
              title={
                <span>
                  <Icon type="team" />
                  <span>哈哈哈哈</span>
                </span>
              }
            >
              <Menu.Item key="6">哈哈哈哈 1</Menu.Item>
              <Menu.Item key="8">哈哈哈哈 2</Menu.Item>
            </SubMenu>
            <Menu.Item key="9">
              <Icon type="file" />
              <span>哈哈哈</span>
            </Menu.Item>
          </Menu>
        </Sider>

        <DashboardRouter/>

      </Layout>
    );
  }
}

export default DashboardPage;