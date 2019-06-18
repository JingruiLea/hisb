import React from 'react';

import {Row,Col,Layout, Table, Divider,Spin,Typography,Card, Select,DatePicker,Modal,InputNumber} from 'antd'
import ToolBar from './WorkforceManage/ToolBar';
import ScheduleToolBar from './WorkforceManage/ScheduleToolBar';
import axios from 'axios';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import DataTable from './WorkforceManage/DataTable';
import ScheduleTable from './WorkforceManage/ScheduleTable';
import moment from 'moment';

const {Content, Footer} = Layout;

const { RangePicker} = DatePicker;

const confirm = Modal.confirm; 

var dateRange = '';
var dataConflict = null;


const mainColumn = [
  {
    title:'姓名',
    key:1,
    dataIndex:'name'
  },{
    title:'电话',
    key:2,
    dataIndex:'phone'
  },{
    title:'职称',
    key:3,
    dataIndex:'title'
  }
  ,{
    title:'班次',
    key:4,
    dataIndex:'shift'
  }
  ,{
    title:'状态',
    key:5,
    dataIndex:'state'
  }
]

const data2=[{
  name:'a1',
  phone:135,
  title:'主任医师',
  shift:'全天',
  state:'有效',
},{
    name:'a2',
    phone:136,
    title:'副主任医师',
    shift:'上午',
    state:'有效',
}]

const userColumn = [
  {
    title:'用户',
    key:1,
    dataIndex:'user'
  },{
    title:'全天',
    key:2,
    dataIndex:'all'
  },{
      title:'工作日',
      key:2,
      dataIndex:'weekday'
    }
    ,{
      title:'周末',
      key:2,
      dataIndex:'weekend'
    }
]

const data1=[{
  user:'user1',
  all:5,
  weekday:0,
  weekend:0,
},{
  user:'user2',
  all:2,
  weekday:0,
  weekend:0,
}]

const { Option } = Select;



function onChange(value) {
  console.log(`selected ${value}`);
}

function onBlur() {
  console.log('blur');
}

function onFocus() {
  console.log('focus');
}

function onSearch(val) {
  console.log('search:', val);
}

function disabledDate(current) {
  // Can not select days before today and today
  return current && current < moment().endOf('day');
}

class WorkforceManage extends React.Component {

  state = {
    selectedRows:[],//选中项
    selectedScheduleRows:[],//排班结果选中项
    shifts:[],//表格数据
    schedules:[],//排班数据
    loading:true,//加载状态
    overwrite:false,//是否需要覆盖
    overwriteIds:[],//需覆盖的ID
    inputDate:true,//是否选择好了日期->排班按钮的disable属性
    num:3,//每日排班人数
    num1:3,
    num2:3,
    conflict:0,//新增行时检查冲突
    toAddddSchedule:false//需要向数据库中添加排班信息
  };

  //设置表格选中的数据
setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

  //设置表格选中的数据
  setScheduleSelected=(selectedScheduleRows)=>{this.setState({selectedScheduleRows:selectedScheduleRows})}

//初始化加载数据
componentDidMount = ()=>{
  this.reloadData();
  this.setSchedule();
}

onDateChange = (date, dateString)=>{
  dateRange = dateString;
  this.timeConflict(dateRange);
}
onNumberChange = (value) =>{
  this.setState({num:value});
}


  //确认覆盖对话框
  showOverwriteConfirm = (data)=>{
    const _this = this;
    confirm({
      title: '选择的时间段已排班,请重新选择时间！',
      content: '如果想要覆盖已生成的排班信息，点击覆盖按钮',
      onOk() {
        console.log('覆盖');
        _this.setState({
          overwrite:true,
          overwriteIds:data.data,
          inputDate:false
      });
      },
      onCancel() {
        console.log('取消');
        _this.setState({
          inputDate:true
      });
      },
    });
  }


 

/***************************************  数据交互   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        const _this = this;
        this.setState({loading:true})
        API.request(API.bacisInfoManagement.schedulingInfoManagement.getPersonnelInfo,{})
        .ok((data)=>{
            const shifts = data;
            for(var d of shifts) {d.key = d.id;}
                this.setState({
                    shifts:shifts,
                    loading:false
                });
        }).submit();
    }

  //schedule
  reloadSchedule = ()=>{
    const _this = this;
    this.setState({loading:true})
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getScheduleInfo,{})
    .ok((data)=>{
        var schedules = data;
        for(var d of schedules) {
            d.key = d.name;
          }
          this.setState({
            //schedules:schedules,
              loading:false
          });
          this.addSchedule(schedules);
          this.setSchedule();
    }).submit();
}

setSchedule = ()=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getAllScheduleInfo,{})
    .ok((data)=>{
        var schedules = data;
        for(var d of schedules) {
          d.key = d.name;
        }
        this.setState({
          schedules:schedules,
            loading:false
        });
    }).submit();
}

updateRow=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.updatePersonnelInfo,data)
    .ok((data)=>{
        this.setState({selectedRows:[]})
        this.reloadData();
        Message.success("修改成功")
    }).submit();
}

  //添加生成的排班信息至数据库
  addSchedule = (data)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.addScheduleInfo,{data})
    .ok((data)=>{
        this.reloadData();
        Message.success("添加数据成功");
    }).submit();
}

  //schedule
  updateSchedule=(data)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getScheduleInfo,data)
    .ok((data)=>{
        _this.setState({selectedScheduleRows:[]})
        this.reloadSchedule();
        Message.success("updateSchedule")
    }).submit();
}

  //生成排班信息
  chooseRow=(data,num)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.chooseDoctor,{data,dateRange,num})
    .ok((data)=>{
        _this.setState({selectedRows:[]})
        _this.reloadSchedule();
    }).submit();
}

timeConflict=(inputData)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.findTimeConflict,{data:inputData})
    .ok((data)=>{
        if(data.length==0){
        _this.setState({
            selectedRows:[],
            inputDate:false
          })
          //this.reloadData();
          Message.success("选择时间段成功")
        }
    }).submit();
}

  findRowConflict=(data)=>{
    var isConflict = this.addRowConflict(data);
    if(isConflict==null){
      console.log('TRUE');
      this.newScheduleRow(data);
    }else{
      console.log('FALSE');
      
    }
  }
  //新增排班结果中的行
  newScheduleRow=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.addScheduleRowInfo,data)
    .ok((data)=>{
        this.reloadData();
        Message.success("添加数据成功");
    }).submit();
  }

//查找新增行中的冲突
addRowConflict=(inputData)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.findAddRowConflict,inputData)
    .ok((data)=>{
        if(data.length!=0){
            //conflict = 1;
            this.setState({conflict:1});
          }
    }).submit();
    return this.state.conflict;
  }

//删除排班结果中的行
deleteScheduleRow=(data)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.deleteScheduleRowInfo,{data})
    .ok((data)=>{
        _this.setState({selectedScheduleRows:[]})
        _this.reloadData();
        Message.success("删除数据成功","")
    }).submit();
  }

//填入AddForm中的数据
getAddTableInfo=(data)=>{
    const _this = this;
    return new Promise((resolve,reject)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getAddInfo,{data:data})
    .ok((data)=>{
        _this.setState({selectedScheduleRows:[]})
        resolve(data);
  }).submit();
  })
  }


//覆盖排班信息
overwriteInfo=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.overwriteScheduleInfo,{data})
    .ok((data)=>{
        this.deleteOverwriteInfo();
        Message.success("覆盖数据成功");
    }).submit();
  }

//删除覆盖的排班信息
deleteOverwriteInfo=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.deleteOverwriteScheduleInfo,{data})
    .ok((data)=>{
        this.reloadSchedule();
        Message.success("删除覆盖数据成功");
    }).submit();
  }

    render() {
        return(
          <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Card title="循环策略" style={{overflow:'scroll',minWidth:'100px',height:'300px'}} >

            选择每日值班人数   
            <InputNumber 
                min={1} 
                max={10} 
                defaultValue={3} 
                onChange={this.onNumberChange} 
            />

            <Divider/>
            选择排班时间
            <RangePicker 
            onChange={this.onDateChange} 
            disabledDate={disabledDate}
            />
            </Card>
          </Col>
          <Col span={18}> 
          <Row>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                shifts={this.state.shifts}
                updateRow={this.updateRow.bind(this)}
                updateSchedule={this.updateSchedule.bind(this)}
                chooseRow={this.chooseRow.bind(this)}
                overwrite={this.state.overwrite}
                overwriteIds={this.state.overwriteIds}
                overwriteInfo={this.overwriteInfo.bind(this)}
                inputDate={this.state.inputDate}
                num={this.state.num}
                toAddSchedule={this.state.toAddddSchedule}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.shifts} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
            </Row>
          </Col>
        </Row>
        <Row>
          
          <ScheduleToolBar
                disabled={this.state.loading}
                selectedScheduleRows={this.state.selectedScheduleRows}
                reloadSchedule={this.reloadSchedule.bind(this)}
                getAddTableInfo={this.getAddTableInfo.bind(this)} 
                schedules={this.state.schedules}
                updateSchedule={this.updateSchedule.bind(this)}
                deleteScheduleRow={this.deleteScheduleRow.bind(this)}
                newScheduleRow={this.newScheduleRow.bind(this)}
                findRowConflict={this.findRowConflict.bind(this)}
                addRowConflict={this.addRowConflict.bind(this)}
                conflict={this.state.conflict}
                componentDidMount={this.componentDidMount.bind(this)}
                setSchedule={this.setSchedule.bind(this)}
            />
            <Divider/> 
                {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<ScheduleTable
                data={this.state.schedules} 
                rowSelection={this.state.rowSelection}
                reloadSchedule={this.reloadSchedule.bind(this)}
                setScheduleSelected={this.setScheduleSelected.bind(this)}
                updateSchedule={this.updateSchedule.bind(this)} 
                rowKey={this.id}
            />}
          </Row>
       
          </Content>)
      }
}

export default WorkforceManage;