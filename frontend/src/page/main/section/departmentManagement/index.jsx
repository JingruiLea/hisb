import React from 'react';
import { Layout, Button,Input,Icon, Table, Divider, Tag,Spin,Typography} from 'antd';
import Highlighter from 'react-highlight-words';
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
    key: i,
    code:'IX'+(i+100000),
    name:'心血管内科',
    is_clinical:true,
    classification:'内科'
  })
  tableData.push({
    key: i+1,
    code:'IX'+(i+100001),
    name:'放射科',
    is_clinical:false,
    classification:'医学影像科'
  })
}
*/

class DepartmentManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        tableData:[],//表格数据
        departmentClassification:[],//分类数据
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
            method: API.bacisInfoManagement.getDepartmentInfo.method,
            url: API.bacisInfoManagement.getDepartmentInfo.url,
            data: {},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                var tableData = data.data.department;
                for(var d of tableData) {d.key = d.id;}
                this.setState({
                    tableData:data.data.department,
                    departmentClassification:data.data.department_classification,
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
            method: API.bacisInfoManagement.updateDepartmentInfo.method,
            url: API.bacisInfoManagement.updateDepartmentInfo.url,
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
            method: API.bacisInfoManagement.addDepartmentInfo.method,
            url: API.bacisInfoManagement.addDepartmentInfo.url,
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
            method: API.bacisInfoManagement.deleteDepartmentInfo.method,
            url: API.bacisInfoManagement.deleteDepartmentInfo.url,
            data: {data:data},
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
                departmentClassification={this.state.departmentClassification}

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
                data={this.state.tableData} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            
        </Content>)
    }
}

export default DepartmentManagement;