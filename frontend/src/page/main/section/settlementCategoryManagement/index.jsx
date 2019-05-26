import React from 'react';
import { Layout, Button,Input,Icon, Table, Divider, Tag,Spin,Typography} from 'antd';
import axios from 'axios';
import ToolBar from './ToolBar';
import API from '../../../../global/ApiConfig';
import Status from '../../../../global/Status';
import Message from '../../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;

/*
const tableData = [];
for (let i = 0; i < 10; i+=2) {
  tableData.push({
    key:i,
    uid: i,
    name:"ABC"
  })
}
*/

class SettlementCategoryManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        settlementCategory:[],//表格数据
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    /***************************************  数据交互   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        const _this = this;
        this.setState({loading:true})
        axios({
            method: API.bacisInfoManagement.getSettlementCatrgoryInfo.method,
            url: API.bacisInfoManagement.getSettlementCatrgoryInfo.url,
            data: {},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                var settlementCategory = data.data;
                for(var d of settlementCategory) {d.key = d.id;}
                this.setState({
                    settlementCategory:settlementCategory,
                    loading:false
                });
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
            Message.showNetworkErrorMessage();
        });
    }

    updateRow=(data)=>{
        const _this = this;
        axios({
            method: API.bacisInfoManagement.updateSettlementCatrgoryInfo.method,
            url: API.bacisInfoManagement.updateSettlementCatrgoryInfo.url,
            data: data,
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                _this.setState({selectedRows:[]})
                this.reloadData();
                Message.success("修改成功")
                //this.setState({tableData:data.data,loading:true})
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
            Message.showNetworkErrorMessage();
        });
    }

    newRow=(data)=>{
        const _this = this;
        axios({
            method: API.bacisInfoManagement.addSettlementCatrgoryInfo.method,
            url: API.bacisInfoManagement.addSettlementCatrgoryInfo.url,
            data: data,
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                this.reloadData();
                Message.success("添加数据成功");
               // this.setState({tableData:data.data,loading:false})
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
           Message.showNetworkErrorMessage();
        });
    }

    deleteRow=(data)=>{
        const _this = this;
        axios({
            method: API.bacisInfoManagement.deleteSettlementCatrgoryInfo.method,
            url: API.bacisInfoManagement.deleteSettlementCatrgoryInfo.url,
            data: {data},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                _this.setState({selectedRows:[]})
                _this.reloadData();
                Message.success("删除数据成功","")
               // this.setState({tableData:data.data,loading:false})
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
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                settlementCategory={this.state.settlementCategory}
                reloadData={this.reloadData.bind(this)}
                updateRow={this.updateRow.bind(this)}
                deleteRow={this.deleteRow.bind(this)}
                newRow={this.newRow.bind(this)}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.settlementCategory} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            
        </Content>)
    }
}

export default SettlementCategoryManagement;