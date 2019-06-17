import React from 'react';
import {Row,Col,Layout,Card,Typography, Input, Table, Divider} from 'antd';
import Sider from './sider';
import { Descriptions } from 'antd';

import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import PrescriptionDisplay from './PrescriptionDisplay';

const {Content, Footer} = Layout;

class OutpatientDispensingSection extends React.Component {

  state = {
    registrationList:[],/** 
      key:1,
      id:1,
      medical_record_number:'0120000',
      name:'刘金星'
    }*/
    dispenseList:[
          {
              "id":1,
              "medical_record_id" :1,
              "type":"成药", 
              "status":"", 
              "create_time":"",
              "user_id":1,
              "prescription_item_list":[
                  { 
                      "drug_item":{
                          "id" :1,
                          "code":"AIE929",
                          "name":"脑残片",
                          "format":"例",
                          "unit":"unit",
                          "manufacturer":"manu",
                          "dosage_form":"dos", 
                          "type":"type",
                          "price":"price",
                          "pinyin":"prinyin",
                          "stock":"stock"
                      },
                      "id":1,
                      "note":"note",
                      "usage" :"usage",
                      "dosage":"dosage",
                      "frequency":"fre",
                      "day_count":"dc",
                      "times" :"times",
                      "amount":"amount",
                      "prescription_id":"pi",
                      "drug_id":1,
                      "status":"已取药"
                  }
              ]
          }
      ]
    ,
    withdrawableList:null,
    withdrawedList:null,
    selectedRegistration:null
  }

  componentDidMount=()=>{
    API.request(API.pharmacyWorkStation.drugDispatcher.dispenseList,{})
    .ok(data=>{
      console.log('wxxxxx',data)
    }).submit();
  }

  searchRegistrationList=(medical_record_id)=>{
    API.request(API.outpatientDoctor.medicalRecord.registrationInfo,{medical_record_id})
    .ok(data=>{
      if(data.length) {
        data.forEach(x=>x.key=x.medical_record_id)
        this.setState({registrationList:data})
      } else {
        Message.openNotification('失败','找不到该病历号对应的挂号信息，请检查输入是否正确。')
      }
    }).submit()
  }

  searchPrescription=(registration)=>{
    this.setState({selectedRegistration:registration})
    API.request(API.pharmacyWorkStation.drugDispatcher.dispenseList,{medical_record_id:registration.medical_record_id})
    .ok(dispenseList=>{
      this.setState({dispenseList})
    })
    API.request(API.pharmacyWorkStation.drugDispatcher.withdrawableList,{medical_record_id:registration.medical_record_id})
    .ok(withdrawableList=>{
      this.setState({withdrawableList})
    })
    API.request(API.pharmacyWorkStation.drugDispatcher.withdrawedList,{medical_record_id:registration.medical_record_id})
    .ok(withdrawedList=>{
      this.setState({withdrawedList})
    })
  }

  render() {
    const {registrationList} = this.state;
    return(
      <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Sider 
              searchPrescription={this.searchPrescription.bind(this)}
              searchRegistrationList={this.searchRegistrationList.bind(this)}
              registrationList={registrationList}/>
          </Col>

          <Col span={18}>
           <PrescriptionDisplay 
            registration={this.state.selectedRegistration}
            withdrawableList={this.state.withdrawableList}
            withdrawedList={this.state.withdrawedList}
            dispenseList={this.state.dispenseList}/>
          </Col>
        </Row>
      </Content>)
  }

}

export default OutpatientDispensingSection;