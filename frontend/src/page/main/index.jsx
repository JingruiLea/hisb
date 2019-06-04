import React from 'react';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';

import DashboardSection from './DashboardSection'
import DashboardHeader from '../../section/DashboardHeader'
import axios from "axios";
import API from '../../global/ApiConfig';
import Status from '../../global/Status';
import Message from '../../global/Message';

const { Header, Content, Footer, Sider } = Layout;
const SubMenu = Menu.SubMenu;

class DashboardPage extends React.Component {
  state = {
    collapsed: false,
    sectionKey:"0",
    openKey:"sub3",
    me:{
      username:"XUranus",
      real_name:"李井瑞",
      department_id:1,
      department_name:"Google操作系统部",
      role_id:1,
      role_name:"Google CEO",
      title:"Fuchsia总设计师"
    }
  };

  onCollapse = collapsed => {
    this.setState({ collapsed });
  };

  onOpenChange = selected => {
    for(var key of selected) {
      if(key!=this.state.openKey) {
        this.setState({openKey:key})
        return;
      }
    }
  }

  changeSection = e =>{
    this.setState({
      sectionKey:e.key,
    })
  }

  _componentDidMount() {
    const _this = this;
    axios({
        method: API.me.myInfo.method,
        url: API.me.myInfo.url,
        crossDomain: true
      }).then((res)=>{
        const data = res.data;
        console.log('receive',data)
        if(data.code===Status.Ok) {
            _this.setState({me:data.data})
        } else if(data.code===Status.PermissionDenied) {
            Message.showAuthExpiredMessage();
        } else {
            Message.showConfirm('错误',data.msg)
        }
    }).catch((err)=>{
        Message.showNetworkErrorMessage();
    });
  }

  render() {
    return (
      <Layout style={{ minHeight: '100vh' }}>
        <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
          <div style={{height:'32px',margin:'16px'}}>
          <h1 style={{color:'white',textAlign:'center'}}>HIS</h1>
          </div>

          <Menu theme="dark" onClick={this.changeSection} onOpenChange={this.onOpenChange} openKeys={[this.state.openKey]} defaultSelectedKeys={['0']} mode="inline">

            <SubMenu
              key="sub3"
              title={
                <span>
                  <Icon type="user" />
                  <span>基础信息维护</span>
                </span>
              }
            >
              <Menu.Item key="3-1">科室管理</Menu.Item>
              <Menu.Item key="3-2">用户管理</Menu.Item>
              <Menu.Item key="3-3">挂号级别管理</Menu.Item>
              <Menu.Item key="3-4">结算类别管理</Menu.Item>
              <Menu.Item key="3-5">诊断目录管理</Menu.Item>
              <Menu.Item key="3-6">非药品收费项目管理</Menu.Item>
              <Menu.Item key="3-7">医生排班管理</Menu.Item>
            </SubMenu>

            <SubMenu
              key="sub4"
              title={
                <span>
                  <Icon type="team" />
                  <span>门诊挂号管理</span>
                </span>
              }
            >
              <Menu.Item key="4-1">现场挂号</Menu.Item>
              <Menu.Item key="4-2">收费</Menu.Item>
              <Menu.Item key="4-3">退号</Menu.Item>
              <Menu.Item key="4-4">退费</Menu.Item>
              <Menu.Item key="4-5">患者费用查询</Menu.Item>
              <Menu.Item key="4-6">收费员日结</Menu.Item>
            </SubMenu>

            <SubMenu
              key="sub5"
              title={
                <span>
                  <Icon type="dashboard" />
                  <span>门诊医生工作站</span>
                </span>
              }
            >
              <Menu.Item key="5-1">门诊病例首页</Menu.Item>
              <Menu.Item key="5-2">检查申请</Menu.Item>
              <Menu.Item key="5-3">检验申请</Menu.Item>
              <Menu.Item key="5-4">门诊确诊</Menu.Item>
              <Menu.Item key="5-5">处置申请</Menu.Item>
              <Menu.Item key="5-6">成药处方</Menu.Item>
              <Menu.Item key="5-7">草药处方</Menu.Item>
              <Menu.Item key="5-8">诊毕</Menu.Item>
              <Menu.Item key="5-9">患者费用明细查询</Menu.Item>
              <Menu.Item key="5-10">病历模板管理</Menu.Item>
              <Menu.Item key="5-11">检查组套管理</Menu.Item>
              <Menu.Item key="5-12">检验组套管理</Menu.Item>
              <Menu.Item key="5-13">处置组套管理</Menu.Item>
              <Menu.Item key="5-14">处方组套管理</Menu.Item>
              <Menu.Item key="5-15">个人工作量统计</Menu.Item>
            </SubMenu>
            
            <SubMenu
              key="sub6"
              title={
                <span>
                  <Icon type="laptop" />
                  <span>门诊医技工作站</span>
                </span>
              }
            >
              <Menu.Item key="6-1">检查\检验登记</Menu.Item>
              <Menu.Item key="6-2">检查\检验结果录入</Menu.Item>
              <Menu.Item key="6-3">个人工作量统计</Menu.Item>
            </SubMenu>

            <SubMenu
              key="sub7"
              title={
                <span>
                  <Icon type="plus-square" />
                  <span>门诊药房工作站</span>
                </span>
              }
            >
              <Menu.Item key="7-1">门诊发药</Menu.Item>
              <Menu.Item key="7-2">门诊退药</Menu.Item>
              <Menu.Item key="7-3">药品目录管理</Menu.Item>
            </SubMenu>

            <SubMenu
              key="sub8"
              title={
                <span>
                  <Icon type="money-collect" />
                  <span>门诊财务管理</span>
                </span>
              }
            >
              <Menu.Item key="8-1">费用科目管理</Menu.Item>
              <Menu.Item key="8-2">门诊日结核对</Menu.Item>
              <Menu.Item key="8-3">科室工作量统计</Menu.Item>
              <Menu.Item key="8-4">个人工作量统计</Menu.Item>
            </SubMenu>

          </Menu>
        </Sider>

        <Layout>
          <DashboardHeader me={this.state.me}/>
          <DashboardSection me={this.state.me} sectionKey={this.state.sectionKey}/>
          <Footer style={{textAlign:'center'}}/>
        </Layout>
        

      </Layout>
    );
  }
}

export default DashboardPage;

