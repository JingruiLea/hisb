import React from 'react';
import {Card,Form,Layout,Row,Drawer, Table, Input, Typography, Button, Col} from 'antd';
import axios from 'axios';
import RegistrationForm from './RegistrationForm';
import DetailDrawer from './DetailDrawer';
import HistoryCard from './HistoryCard'

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

  //退号
  withdrawNumber=(id)=>{
    
  }

  render() {
    return(<Content style={{ margin: '0 16px',paddingTop:5 }}>
      <Card title="挂号">
        <RegistrationForm/>
      </Card>
      <br/>
      <HistoryCard data={data}/>
    </Content>)
  }
}

export default OutpatientRegistration;

