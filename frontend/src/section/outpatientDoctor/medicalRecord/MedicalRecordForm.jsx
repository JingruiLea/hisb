import React from 'react';
import {Layout, Divider} from 'antd'
import {Row,Col,Table,Spin,Typography,Collapse} from 'antd'
import { Tabs } from 'antd';
import { Tree } from 'antd';
import {
  Form,
  Input,
  Tooltip,
  Icon,
  Cascader,
  Select,
  Checkbox,
  Button,
  Radio,
} from 'antd';
import Message from '../../../global/Message';
import DiagnoseSelectionTable from './DiagnoseSelectionTable'
const {Panel} = Collapse;


class MedicalRecordForm extends React.Component {

  state = {
    primaryDiagnose:{
      westernDiagnoseData:[{
        disease_id:1,
        disease_code:"A02.004",
        disease_name:"鼠伤寒肠炎",
        main_symptom:false,
        suspect:false,
        key:1
      },{
        disease_id:2,
        disease_code:"A02.003",
        disease_name:"鼠伤肠",
        main_symptom:false,
        suspect:true,
        key:2
      }],
      chineseDiagnoseData:[{
        disease_id:3,
        disease_code:"A02.004",
        disease_name:"鼠伤寒肠炎",
        syndrome_differentiation:'肾虚',
        key:3
      },{
        disease_id:4,
        disease_code:"A02.104",
        disease_name:"伤寒肠炎",
        syndrome_differentiation:'肾虚',
        key:4
      }],
    },
    diagnoses:{
      chineseDiagnoseDiseases:[
        {id:10,name:"脑残1",key:10,code:"A1029"},
        {id:11,name:"脑残2",key:11,code:"A1039"},
      ],
      westernDiagnoseDiseases:[
        {id:12,name:"脑残1",key:12,code:"A1049"},
        {id:13,name:"脑残2",key:13,code:"A1059"},
      ]
    }
  }

  componentDidMount=()=>{this.props.onRef(this)}

  setPrimaryDiagnose=(primaryDiagnose)=>{this.setState({primaryDiagnose})}

  applyTemplate=(template)=>{
    console.log('dom apply',template)
    setTimeout(()=>{
      this.props.form.setFieldsValue(template)
    },1) //我觉得这个是官方的bug
  }

  handleSubmit = (mode) => {
    const form = this.props.form;
    const values = form.getFieldsValue();
    values.primaryDiagnose = this.state.primaryDiagnose;
    console.log(values);

    switch(mode) {
      case "clear":{
        Message.showConfirm('清空','你确认要清空病历吗？',()=>{form.resetFields()},()=>{})
        break;
      }
      case "store":{
        
        break;
      }
      case "save":{
        form.validateFields((err, values) => {
          if (!err) {
            values.primaryDiagnose = this.state.primaryDiagnose;
            console.log('Received login values of form: ', values);
          }
        });
        break;
      }
      case "saveAsTemplate":{

        break;
      }
    }

  };

  render() {
    const {state} = this;
    const {medicalRecord,registration} = this.props.currentPatient;
    const disabled = medicalRecord === undefined;
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 },
      },
    };
    const rules = {
      rules:[{
        required:true,
        message:'字段不能为空'
    }]}

    return (
      <div style={{minHeight:'870px',marginRight:'10px'}} >
        <div>
          <Typography.Title level={4} style={{float:'left'}}>病历信息</Typography.Title>
          <Button icon="save" disabled={disabled} type="primary" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("store")}}>暂存</Button>
          <Button icon="save" disabled={disabled} type="primary" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("save")}}>保存</Button>
          <Button icon="delete" disabled={disabled} type="danger" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("clear")}}>清空</Button>
          <Button icon="delete" disabled={disabled} type="danger" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("saveAsTemplate")}}>存为模板</Button>
        </div><br/><br/>

        <Form {...formItemLayout} onSubmit={this.handleSubmit} >
        <Collapse defaultActiveKey={['3']}>
          <Panel header="病史内容" key="1">
            <Form.Item label="主诉">
              {getFieldDecorator('chief_complaint',rules)(<Input/>)}
            </Form.Item>
            <Form.Item label="现病史">
              {getFieldDecorator('current_medical_history',rules)(<Input.TextArea/>)}
            </Form.Item>
            <Form.Item label="现病治疗情况">
              {getFieldDecorator('current_treatment_situation',rules)(<Input.TextArea/>)}
            </Form.Item>
            <Form.Item label="既往史">
              {getFieldDecorator('past_history',rules)(<Input.TextArea/>)}
            </Form.Item>
            <Form.Item label="过敏史">
              {getFieldDecorator('allergy_history',rules)(<Input.TextArea/>)}
            </Form.Item>
          </Panel>

          <Panel header="检查及结果" key="2">
            <Form.Item label="体格检查">
              {getFieldDecorator('physical_examination',rules)(<Input.TextArea/>)}
            </Form.Item>
          </Panel>

          <Panel header="初步诊断" key="3">
            <DiagnoseSelectionTable 
              diagnoses={this.state.diagnoses}
              primaryDiagnose={this.state.primaryDiagnose}
              setPrimaryDiagnose={this.setPrimaryDiagnose.bind(this)}
            />
          </Panel>
        </Collapse>

        </Form>
      </div>
    );
  }
 
}

export default Form.create({ name: 'medical-record' })(MedicalRecordForm);