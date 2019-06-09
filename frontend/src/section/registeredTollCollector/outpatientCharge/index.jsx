import React from 'react';
import {Card,Form,Layout,Row,Drawer, Table, Input, Typography, Button, Col} from 'antd';
import RegistrationInfoDisplay from "./RegistrationInfoDisplay";
import ChargeItemSection from './ChargeItemSection';
import API from '../../../global/ApiConfig';

const {Content} = Layout;

class OutpatientCharge extends React.Component {

  state = {
    medicalRecord:{},
    chargeItems:[],
    chargedItems:[],
    loading:true
  }

  handleSearch=(medical_record_number)=>{
    API.request(API.outpatientWorkstation.charge.getRegistrationInfo,{medical_record_number})
    .ok((medicalRecord)=>{
      this.setState({medicalRecord:medicalRecord,loading:false})
    }).submit();

    API.request(API.outpatientWorkstation.charge.getChargeItems,{medical_record_number})
    .ok((chargeItems)=>{
      this.setState({chargeItems})
    }).submit();

    API.request(API.outpatientWorkstation.charge.getChargedItems,{medical_record_number})
    .ok((chargedItems)=>{
      this.setState({chargedItems})
    }).submit();
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
        chargeItems={state.chargeItems}
        chargedItems={state.chargedItems}/>
    </Content>)
  }
}

export default OutpatientCharge;

