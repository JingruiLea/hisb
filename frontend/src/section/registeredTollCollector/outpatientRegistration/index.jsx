import React from 'react';
import {Card,Form,Layout, Spin, Input, Typography, Button, Col, message, Modal} from 'antd';
import axios from 'axios';
import RegistrationForm from './RegistrationForm';
import DetailDrawer from './DetailDrawer';
import HistoryCard from './HistoryCard';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';
import Message from '../../../global/Message';
import RegistrationBillPrint from './RegistrationBillPrint'

const {Content} = Layout;

const data = [
  {
    id:"099023",
    name:"菜徐坤",
    gender:"男",
    time:"2018-03-31",
    status:"已就诊",
    cost:1000,
    department:"神经科",
    key:"1"
  }
]

class OutpatientRegistration extends React.Component {

  state = {
    loading:true,
    defaultRegistrationLevel:{},
    registrationLevel:[],
    settlementCategory:[],
    departments:[],
    outPatientDoctors:[],
    cost:0,
    payMode:false,
    printVisible:false,
    bill:{}
  }

  //退号
  withdrawNumber=(id)=>{}

  componentDidMount=()=>{this.init();}

  setPaymentMode=(payMode)=>{this.setState({payMode})}

  handlePrintCancel=()=>{this.setState({printVisible:false})}

  handlePrintOk=()=>{this.setState({printVisible:true})}

  //初始化面板信息 加载必须的数据
  init=()=>{
    const _this = this;
    axios({
        method: API.outpatientWorkstation.registration.init.method,
        url: API.outpatientWorkstation.registration.init.url,
        data: {data:data},
        crossDomain: true,
        withCredentials:true
      }).then((res)=>{
        const code = res.data.code;
        const data = res.data.data;
        console.log('receive',data)
        if(code===Status.Ok) {
            _this.setState({
              defaultRegistrationLevel:data.defaultRegistrationLevel,
              registrationLevel:data.registrationLevel,
              settlementCategory:data.settlementCategory,
              departments:data.departments,
              loading:false,
            })
        } else if(code===Status.PermissionDenied) {
            Message.showAuthExpiredMessage();
        } else {
            Message.showConfirm('错误',res.data.msg)
        }
    }).catch((err)=>{
        Message.showNetworkErrorMessage();
    });
  }


  //同步医生列表
  syncDoctorList=(data)=>{
    const _this = this;
    axios({
        method: API.outpatientWorkstation.registration.syncDoctorList.method,
        url: API.outpatientWorkstation.registration.syncDoctorList.url,
        data: data,
        crossDomain: true,
        withCredentials:true
      }).then((res)=>{
        const code = res.data.code;
        const data = res.data.data;
        console.log('receive',data)
        if(code===Status.Ok) {
            if(data.length===0) 
              Message.openNotification("找不到排班医生","没有医生在此时间段，或没有匹配的科室，请重新选择")
            _this.setState({
              outPatientDoctors:data
            })
        } else if(code===Status.PermissionDenied) {
            Message.showAuthExpiredMessage();
        } else {
            Message.showConfirm('错误',res.data.msg)
        }
      }).catch((err)=>{
          Message.showNetworkErrorMessage();
      });
    }


   calculateFee=async (data)=>{
      const _this = this;
      axios({
          method: API.outpatientWorkstation.registration.calculateFee.method,
          url: API.outpatientWorkstation.registration.calculateFee.url,
          data: data,
          crossDomain: true,
          withCredentials:true
        }).then((res)=>{
          const code = res.data.code;
          const data = res.data.data;
          console.log('receive',data)
          if(code===Status.Ok) {
              _this.setState({
                payMode:true,
                cost:data.fee
              })
          } else if(code===Status.PermissionDenied) {
              Message.showAuthExpiredMessage();
          } else {
              Message.showConfirm('错误',res.data.msg)
          }
      }).catch((err)=>{
          Message.showNetworkErrorMessage();
      });
    }

    submitRegistration=async(values)=>{
      const _this = this;
      axios({
          method: API.outpatientWorkstation.registration.confirmRegistration.method,
          url: API.outpatientWorkstation.registration.confirmRegistration.url,
          data: data,
          crossDomain: true,
          withCredentials:true
        }).then((res)=>{
          const code = res.data.code;
          const data = res.data.data;
          console.log('receive',data)
          if(code===Status.Ok) {
              _this.setState({
                cost:data.fee,
                printVisible:true,
                bill:{
                  "medical_record_id" : 10000002, //病历号
                  "name" : 'www', //操作员,
                  "department_name" : "菜徐坤",
                  "cost": 100,
                  "create_time": "2019-6-4 14:10"
                }
              })
          } else if(code===Status.PermissionDenied) {
              Message.showAuthExpiredMessage();
          } else {
              Message.showConfirm('错误',res.data.msg)
          }
      }).catch((err)=>{
          Message.showNetworkErrorMessage();
      });
    }


    render() {
      const state = this.state;
      return(<Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Card title="挂号">
          {state.loading?
          <div style={{textAlign:'center',paddingTop:30}}>
            <Spin/><br/>
            <Typography.Paragraph>加载中...</Typography.Paragraph>
          </div>
          :<RegistrationForm
            defaultRegistrationLevel={state.defaultRegistrationLevel}
            registrationLevel={state.registrationLevel}
            settlementCategory={state.settlementCategory}
            departments={state.departments}
            outPatientDoctors={state.outPatientDoctors}
            cost={this.state.cost}
            payMode={this.state.payMode}
            syncDoctorList={this.syncDoctorList.bind(this)}
            calculateFee={this.calculateFee.bind(this)}
            calculateFee={this.calculateFee.bind(this)}
            setPaymentMode={this.setPaymentMode.bind(this)}
            submitRegistration={this.submitRegistration.bind(this)}
          />}
        </Card>
        <br/>
        <HistoryCard data={data}/>
        <Modal 
          footer={null}
          closable
          onCancel={this.handlePrintCancel.bind(this)}
          onOk={this.handlePrintOk.bind(this)}
          visible={this.state.printVisible
        }><RegistrationBillPrint bill={this.state.bill}/></Modal>
      </Content>)
    }

}

export default OutpatientRegistration;

