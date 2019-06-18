import React from 'react';
import { Layout, Divider,Spin,Typography} from 'antd';
import axios from 'axios';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class NonDrugChargeItemManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        non_drug_charge:[],//表格数据 非药品目录
        expense_classification:[],//费用分类
        department:[],//科室名称
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    /***************************************  API   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        this.setState({loading:true})
        API.request(API.bacisInfoManagement.nonDrugItem.all,{})
        .ok((data)=>{
            const tableData = data.non_drug_charge;
            for(var d of tableData) {d.key = d.id;}
            this.setState({
                non_drug_charge:tableData,
                department:data.department,
                expense_classification:data.expense_classification,
                loading:false
            });
        }).submit();
    }

    updateRow=(rowData)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.update,rowData)
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功")
        }).submit();
    }

    newRow=(rowData)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.add,rowData)
        .ok((data)=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit();
    }

    deleteRow=(idsArr)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.delete,{data:idsArr})
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("删除数据成功","")
        }).submit();
    }

    render() {
        return (
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                department={this.state.department}
                expense_classification={this.state.expense_classification}
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
                data={this.state.non_drug_charge} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            
        </Content>)
    }
}

export default NonDrugChargeItemManagement;