import React from 'react';
import {Collapse,Card,Typography, Tabs, Table, Divider} from 'antd';
//import Sider from './sider';
import { Descriptions } from 'antd';

//import API from '../../../global/ApiConfig';
//import Message from '../../../global/Message';

const {Panel} = Collapse;

class PrescriptionDisplay extends React.Component {

  render() {
    const {dispenseList,registration} = this.props;
    //const {withdrawableList,withdrawedList} = this.props;
    return (
    <Card title="用户处方明细" style={{minHeight:'800px'}}>
      {registration===null?null:
      <Descriptions bordered title="基本信息" border >
        <Descriptions.Item label="病例号">{registration.medical_record_id}</Descriptions.Item>
        <Descriptions.Item label="患者姓名">{registration.patient_name}</Descriptions.Item>
        <Descriptions.Item label="年龄">{registration.age}</Descriptions.Item>
        <Descriptions.Item label="就诊科室">{registration.registration_department}</Descriptions.Item>
        <Descriptions.Item label="医保诊断">{registration.medical_insurance_diagnosis}</Descriptions.Item>
        <Descriptions.Item label="挂号类型">{registration.registration_category}</Descriptions.Item>
      </Descriptions>}

      <Divider/>

      <Tabs>
        <Tabs.TabPane tab="可取药" key="1" disabled={dispenseList===null}>
          {dispenseList?<div>
            <Typography.Title level={4}>
              共有{dispenseList.length}个处方
            </Typography.Title>
            <Collapse accordion>
              {dispenseList.map(dispense=>(
                <Panel header={
                  <span>
                  <span style={{marginRight:'20px'}}><b>处方号码:</b>{dispense.id}</span>
                  <span style={{marginRight:'20px'}}><b>子目数:</b>{dispense.prescription_item_list.length}</span>
                  <span style={{marginRight:'20px'}}><b>创建日期:</b>{dispense.create_time}</span>
                  <span style={{marginRight:'20px'}}><b>状态:</b>{dispense.status}</span>
                  </span>
                  } key="1">
                    <Table columns={
                      [
                        {
                          title:"药品编码",
                          dataIndex:'drug_item.code'
                        },{
                          title:"药品名称",
                          dataIndex:'drug_item.name'
                        },{
                          title:"药品规格",
                          dataIndex:'drug_item.format'
                        },{
                          title:"药品单位",
                          dataIndex:'drug_item.unit'
                        },{
                          title:"药品类型",
                          dataIndex:'drug_item.type'
                        },{
                          title:"数量",
                          dataIndex:'amount'
                        },{
                          title:"剂量",
                          dataIndex:'dosage'
                        },{
                          title:"使用频率",
                          dataIndex:'frequency'
                        }
                      ]
                    } dataSource={dispense.prescription_item_list}/>
                </Panel>
              ))}
            </Collapse>
          </div>:null}
        </Tabs.TabPane>

        <Tabs.TabPane tab="可退药" key="2">

        </Tabs.TabPane>

        <Tabs.TabPane tab="已退药" key="3">

        </Tabs.TabPane>
      </Tabs>
      
      
    </Card>
    )
  }

}

export default PrescriptionDisplay;