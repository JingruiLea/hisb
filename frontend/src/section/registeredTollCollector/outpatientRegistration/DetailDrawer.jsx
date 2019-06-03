import React from 'react';
import {Card,Form,Layout,Row,Drawer, Table, Input, Typography, Button, Col} from 'antd'
import RegistrationForm from './RegistrationForm'

class DetailDrawer extends React.Component {

  //处理退号
  handleWithdrawNumber=()=>{
    const data = this.props.data;
    this.props.withdrawNumber(data.id)
  }

  render() {
    const data = this.props.data;
    return(
      <Drawer
          title="挂号详情信息"
          placement="right"
          closable={true}
          onClose={this.onClose}
          visible={this.props.visible}
          onClose={this.props.onClose}
          width={500}
        >
          {data===null?null:
          <div>
            <Row>
              <Col span={8}>病历号：</Col>
              <Col span={16}>{data.id}</Col>
            </Row>
            <Row>
              <Col span={8}>姓名</Col>
              <Col span={16}>{data.name}</Col>
            </Row>
            <Row>
              <Col span={8}>性别</Col>
              <Col span={16}>{data.gender}</Col>
            </Row>
            <Row>
              <Col span={8}>病历号：</Col>
              <Col span={16}>{10001}</Col>
            </Row>
            <Row>
              <Col span={8}>病历号：</Col>
              <Col span={16}>{10001}</Col>
            </Row>
            <Row>
              <Col span={8}>病历号：</Col>
              <Col span={16}>{10001}</Col>
            </Row>
            <Row>
              <Col span={8}>病历号：</Col>
              <Col span={16}>{10001}</Col>
            </Row>

            <Button type="danger" onClick={this.handleWithdrawNumber.bind(this)}>退号</Button>
          </div>}
        </Drawer>)
  }
}

export default DetailDrawer;

