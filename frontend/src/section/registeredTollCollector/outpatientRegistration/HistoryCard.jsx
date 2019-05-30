import React from 'react';
import {Card,Form,Layout,Row,Drawer, Table, Input, Typography, Button, Col} from 'antd';
import DetailDrawer from './DetailDrawer';

class HistoryCard extends React.Component {

  state = { 
    drawerVisible: false,
    drawerData:null
  };

  showDrawer = (data) => {
    console.log('drawer show',data);
    this.setState({
      drawerVisible: true,
      drawerData:data
    });
  };

  hideDrawer = () => {
    this.setState({drawerVisible: false});
  };

  //退号
  withdrawNumber=(id)=>{
    
  }

  column = [
    {
      title:"病例号",
      dataIndex:"id"
    },{
      title:"姓名",
      dataIndex:"name"
    },{
      title:"性别",
      dataIndex:"gender"
    },{
      title:"挂号日期",
      dataIndex:"time"
    },{
      title:"状态",
      dataIndex:"status"
    },{
      title:"实收费用",
      dataIndex:"cost"
    },{
      title:"看诊科室",
      dataIndex:"department"
    },{
      title:"操作",
      render:(data)=>(<Button type="primary" onClick={()=>{this.showDrawer(data)}}>详情</Button>)
    }
  ]

  render() {

    return(<Card title={
        <div>
          <div style={{float:'left',paddingTop:5}}>
            <span>查询历史挂号记录</span>
          </div>
          <div style={{float:'right'}}>
            <Input placeholder="输入病历号" style={{width:'300px'}}></Input>
            <Button type="primary">搜索</Button>
          </div>
        </div>
      }>
        <Table columns={this.column} dataSource={this.props.data}/>
        <DetailDrawer
          visible={this.state.drawerVisible}
          data={this.state.drawerData}
          onClose={this.hideDrawer.bind(this)}
          withdrawNumber={this.withdrawNumber.bind(this)}
        />
      </Card>)
  }
}

export default HistoryCard;