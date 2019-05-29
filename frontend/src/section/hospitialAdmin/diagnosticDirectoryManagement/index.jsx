import React from 'react';
import { Layout, Button,Input,Icon, Table, Divider, Tag,Spin,Typography} from 'antd';
import axios from 'axios';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class DiagnosticDirectoryManagementSection extends React.Component {
    state = {
        selectedRows:[],//选中项
        diseases:[],//表格数据
        diseaseClassification:[],//疾病分类
        selectClassificationID:-1,
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.init();}


    /***************************************  数据交互   ******************************************* */
    
    init = async()=>{
        axios({
            method: API.bacisInfoManagement.diagnoseDirectory.allClassification.method,
            url: API.bacisInfoManagement.diagnoseDirectory.allClassification.url,
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                var allClassification = data.data;
                console.log('classification loaded:',allClassification);
                this.setState({
                    diseaseClassification:allClassification,
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


    handleSelectClassification=async (selectClassificationID)=>{
        console.log('select classification id',selectClassificationID)
        await this.setState({
            loading:true,
            selectClassificationID:selectClassificationID
        });
        this.reloadData()
    }


    reloadData=()=>{
        console.log('reload disease, classification id',this.state.selectClassificationID)
        axios({
            method: API.bacisInfoManagement.diagnoseDirectory.searchAllByClassificationId.method,
            url: API.bacisInfoManagement.diagnoseDirectory.searchAllByClassificationId.url,
            data:{classification_id:this.state.selectClassificationID},
            crossDomain: true,
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                var diseases = data.data.diseases;
                diseases.forEach((x)=>{x.key = x.id})
                this.setState({
                    diseases:diseases,
                    loading:false,
                    selectClassificationID:this.state.selectClassificationID
                });
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
            Message.showNetworkErrorMessage();
            console.log(err)
        });
    }

    updateRow=(data)=>{
        const _this = this;
        axios({
            method: API.bacisInfoManagement.diagnoseDirectory.update.method,
            url: API.bacisInfoManagement.diagnoseDirectory.update.url,
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
            method: API.bacisInfoManagement.diagnoseDirectory.add.method,
            url: API.bacisInfoManagement.diagnoseDirectory.add.url,
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
            method: API.bacisInfoManagement.diagnoseDirectory.delete.method,
            url: API.bacisInfoManagement.diagnoseDirectory.delete.url,
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
                handleSelectClassification={this.handleSelectClassification.bind(this)}
                diseaseClassification={this.state.diseaseClassification}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.diseases} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            
        </Content>)
    }
}

export default DiagnosticDirectoryManagementSection;