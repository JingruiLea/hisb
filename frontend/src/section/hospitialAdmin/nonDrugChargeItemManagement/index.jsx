import React from 'react';
import { Layout, Button,Input,Icon, Table, Divider, Tag,Spin,Typography} from 'antd';
import axios from 'axios';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;

/*
const tableData = [];
for (let i = 0; i < 10; i+=2) {
  tableData.push({
    key:i,
    uid: i,
    username:'USER'+(i+100000),
    password:"2132131"+i,
    real_name:'菜徐坤',
    role:'NBA形象大使',
    department_name:"鬼畜区",
    is_doctor:true,
    participate_in_scheduling:true,
    title:'最好的kunkun'
  })
}
*/


class NonDrugChargeItemManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        data:[],//表格数据
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
            method: API.bacisInfoManagement.nonDrugItem.all.method,
            url: API.bacisInfoManagement.nonDrugItem.all.url,
            data: {},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                //for(var d of data) {d.key = d.uid;}
                console.log(data.data)
                this.setState({
                    //data:data.data,
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
            method: API.bacisInfoManagement.nonDrugItem.update.method,
            url: API.bacisInfoManagement.nonDrugItem.update.url,
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
            method: API.bacisInfoManagement.nonDrugItem.add.method,
            url: API.bacisInfoManagement.nonDrugItem.add.url,
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
            method: API.bacisInfoManagement.nonDrugItem.delete.method,
            url: API.bacisInfoManagement.nonDrugItem.delete.url,
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
                data={this.state.data} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            
        </Content>)
    }
}

export default NonDrugChargeItemManagement;