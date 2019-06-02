import React from 'react';
import {Button,Layout,Breadcrumb, Table,Drawer} from 'antd';

const { Content } = Layout;

const columns = [
  {
    title:'编码',
    dataIndex:'code',
  },{
    title:'通用名称',
    dataIndex:'name'
  },{
    title:'状态',
    dataIndex:'status'
  },{
    title:'规格',
    dataIndex:'format'
  },{
    title:'厂商',
    dataIndex:'manfacture'
  }
]

var data = [];
for(var i=0;i<100;i++) {
  data.push({
    key:i,
    code:'GG234424',
    name:'菜徐坤菜徐坤菜徐坤菜徐坤',
    status:'有效',
    format:'头',
    manfacture:'ikunikunikunikunikunikunikunikun'
  })
}

class DrugCatalogueManagement extends React.Component {

  state = {
    selectedRows:[],
    loading:false,
    drawVisible:false
  }

  openDrawer=()=>{
    this.setState({drawVisible:true})
  }

  closeDrawer=()=>{
    this.setState({drawVisible:false})
  }

  render() {
    const state = this.state;
    const deleteDisable = state.selectedRows.length===0 || state.loading;
    const updateDisable = state.selectedRows.length!==1 || state.loading;

    return (
      <Content style={{ margin: '0 16px',paddingTop:10 }}>

        <div>
          <Breadcrumb style={{float:'left',paddingTop:10}}>
            <Breadcrumb.Item>HIS</Breadcrumb.Item>
            <Breadcrumb.Item>
              门诊药房
            </Breadcrumb.Item>
            <Breadcrumb.Item>
              药品目录管理
            </Breadcrumb.Item>
          </Breadcrumb>

          <div style={{float:'right',margin:5}}>
            <Button 
              style={{float:'right',marginLeft:'10px'}} 
              type="primary" 
              icon="plus">
                添加
            </Button>
            <Button 
              onClick={this.openDrawer.bind(this)}
              disabled={updateDisable}
              style={{float:'right',marginLeft:'10px'}}  
              icon="edit">
                修改
            </Button>
            <Button 
              disabled={deleteDisable}
              style={{float:'right',marginLeft:'10px'}} 
              type="danger" 
              icon="delete">
                删除
            </Button>
            <Button 
              style={{float:'right',marginLeft:'10px'}} 
              type="dash" 
              icon="upload">
                导入
            </Button>
          </div>
        </div>

        <br/><br/>

        <Table 
          columns={columns} 
          dataSource={data}
          rowSelection={{
            onChange: (selectedRowKeys, selectedRows) => {
              //console.log(selectedRows)
              this.setState({selectedRows});
          }}}
        />

        <Drawer
          title="修改药品信息"
          placement="right"
          closable={true}
          onClose={this.closeDrawer.bind(this)}
          width={800}
          visible={this.state.drawVisible}
        >
          <p>Some contents...</p>
          <p>Some contents...</p>
          <p>Some contents...</p>
        </Drawer>
      </Content>)
  }
}

export default DrugCatalogueManagement;