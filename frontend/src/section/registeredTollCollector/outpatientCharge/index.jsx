import React from 'react';
import {Layout} from 'antd';
import RegistrationInfoDisplay from "./RegistrationInfoDisplay";
import ChargeItemSection from './ChargeItemSection';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';

const {Content} = Layout;

class OutpatientCharge extends React.Component {

  state = {
    medicalRecord:{},
    chargeItems:[],
    chargedItems:[],
    medical_record_id:0,
    loading:true
  }


  /**********************     API     ***************************/

  handleSearch=(medical_record_id)=>{
    medical_record_id=parseInt(medical_record_id)
    this.setState({loading:true})
    API.request(API.outpatientWorkstation.outpatientCharge.getRegistrationInfo,{medical_record_id})
    .ok((medicalRecord)=>{
      if(medicalRecord!==null) {
        this.setState({
          medicalRecord:medicalRecord,
          loading:false,
          medical_record_id
        });

        API.request(API.outpatientWorkstation.outpatientCharge.getChargeItems,{medical_record_id})
        .ok((chargeItems)=>{
          chargeItems.forEach(element => {element.key = element.id;});
          this.setState({chargeItems})
        }).submit();

        API.request(API.outpatientWorkstation.outpatientCharge.getChargedItems,{medical_record_id})
        .ok((chargedItems)=>{
          chargedItems.forEach(element => {element.key = element.id;});
          this.setState({chargedItems})
        }).submit();

      } else  
        Message.openNotification("查找失败","没有找到该病历号记录。")
    }).submit();
  }

  charge=(reqData)=>{
    API.request(API.outpatientWorkstation.outpatientCharge.charge,reqData)
    .ok((data)=>{
      Message.success('缴费成功')
      //重新搜索改病历号
      this.handleSearch(reqData.medical_record_id)
    }).internalError((data)=>{
      Message.openNotification('缴费失败','非法操作，已缴费或该条目不存在！')
    }).submit()
  }

  withdraw=(medical_record_id,id)=>{
    medical_record_id=parseInt(medical_record_id)
    API.request(API.outpatientWorkstation.outpatientCharge.withdraw,{medical_record_id:medical_record_id,charges_id_list:[id]})
    .ok((data)=>{
      Message.success('退费成功')
      //重新搜索改病历号
      this.handleSearch(medical_record_id)
    }).internalError((data)=>{
      Message.openNotification('退费失败','非法操作，或该条目不存在！')
    }).submit()
  }


  render() {
    const state = this.state;
    return(
    <Content style={{ margin: '0 16px',paddingTop:5 }}>
      
      <RegistrationInfoDisplay 
        medicalRecord={state.medicalRecord}
        loading={state.loading}
        handleSearch={this.handleSearch.bind(this)}/>
      <br/>

      <ChargeItemSection 
        charge={this.charge.bind(this)}
        withdraw={this.withdraw.bind(this)}
        medical_record_id={state.medical_record_id}
        loading={state.loading}
        chargeItems={state.chargeItems}
        chargedItems={state.chargedItems}/>
    </Content>)
  }
}

export default OutpatientCharge;

