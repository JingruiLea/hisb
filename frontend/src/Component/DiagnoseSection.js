import React from 'react';
import {Layout, Divider} from 'antd'
import DashboardHeader from './DashboardHeader'
import {Row,Col,Table,Card,Typography,Pagination} from 'antd'
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

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;
const TabPane = Tabs.TabPane;
const {Content,Header} = Layout;


const columns = [{
  title: '#',
  dataIndex: 'id',
  key: 'id',
},{
  title: '姓名',
  dataIndex: 'name',
  key: 'name',
},{
  title: '年龄',
  dataIndex: 'age',
  key: 'age',
}]

var data = [];
for(var i=0;i<40;i++) {
  data.push({
    id:i,
    name:'wxx'+i,
    age:i*10,
    key:i
  })
}



class MedicalRecordForm extends React.Component {
  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received login values of form: ', values);
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 3 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 20 },
      },
    };
    const rules = {
      rules:[{
        required:true,
        message:'字段不能为空'
    }]}
    const columns = [
      {
        title: 'ICD编码',
        dataIndex: 'icd_code',
        key: 'icd_code',
      },{
        title: '名称',
        dataIndex: 'name',
        key: 'name',
      },{
        title: '主诊',
        dataIndex: 'principal_diagnosis',
        key: 'principal_diagnosis',
        render:(data)=>(<Radio checked={data}></Radio>)
      },{
        title: '疑似',
        dataIndex: 'subspect',
        key: 'subspect',
        render:(data)=>(<Checkbox checked={data} name={"n"}></Checkbox>)
      },{
        title: '发病日期',
        dataIndex:'date_of_onset',
        key:'date_of_onset',
      }
    ];
    const demoData = [{
      icd_code:"A02.004",
      name:"鼠伤寒肠炎",
      principal_diagnosis:true,
      subspect:true,
      date_of_onset:"2016-1-2",
      key:1
    }]
  
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      },
      onSelect: (record, selected, selectedRows) => {
        console.log(record, selected, selectedRows);
      },
      onSelectAll: (selected, selectedRows, changeRows) => {
        console.log(selected, selectedRows, changeRows);
      },
    };
    
    const ToolBar = <div>门诊/急诊 病例信息
      <Button icon="save" style={{float:'right'}} htmlType="submit">提交</Button>
      <Button icon="save" style={{float:'right'}}>保存</Button>
      <Button icon="save" style={{float:'right'}}>清空</Button>
      </div>
    
    return (<Form {...formItemLayout} onSubmit={this.handleSubmit}>
       <Card style={{height:'870px'}} 
            title={ToolBar}>
        <Typography.Paragraph>病史内容</Typography.Paragraph>
        <Form.Item label="主诉">
          {getFieldDecorator('zhusu',rules)(<Input/>)}
        </Form.Item>
        <Form.Item label="先病史">
          {getFieldDecorator('xianbingshi',rules)(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="既往史">
          {getFieldDecorator('jiwangshi',rules)(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="个人史">
          {getFieldDecorator('gerenshi',rules)(<Input.TextArea/>)}
        </Form.Item>
        <Typography.Paragraph>检查及结果</Typography.Paragraph>
        <Form.Item label="体格检查">
          {getFieldDecorator('tigejiancha',rules)(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="辅助检查">
          {getFieldDecorator('fuzhujiancha',rules)(<Input.TextArea/>)}
        </Form.Item>
        <Typography.Paragraph>评估诊断</Typography.Paragraph>
        <Table
          columns={columns}
          dataSource={demoData}
          rowSelection={rowSelection}
          pagination={false}
        />
        </Card>
      </Form>
    );
  }
 
}

const WrappedMedicalRecordForm = Form.create({ name: 'medical-record' })(MedicalRecordForm);

class DiagnoseSection extends React.Component {

  render() {
    return (
    <Layout>
      <DashboardHeader/>

      <Content style={{ margin: '0 16px',paddingTop:3}}>
        <Row>
          <Col span={5}>
            <Card style={{height:'870px'}}>
              <Typography.Paragraph>待诊患者（共有{data.length}名患者）</Typography.Paragraph>
              <Table columns={columns} dataSource={data} pagination={{pageSize:6}} size="small"/>
  
              <Divider/>
              <Typography.Paragraph>已诊患者（共有{data.length}名患者）</Typography.Paragraph>
              <Table columns={columns} dataSource={data} pagination={true} pagination={{pageSize:6}} size="small" />
            </Card>
          </Col>

          <Col span={12}>
            <WrappedMedicalRecordForm/>
          </Col>

          <Col span={7}>
            <Card style={{height:'470px'}}>
            <Tabs defaultActiveKey="1" onChange={console.log}>
              <TabPane tab="病例模板" key="1">
                  <DirectoryTree multiple defaultExpandAll onSelect={this.onSelect} onExpand={this.onExpand}>
                    <TreeNode title="parent 0" key="0-0">
                      <TreeNode title="leaf 0-0" key="0-0-0" isLeaf />
                      <TreeNode title="leaf 0-1" key="0-0-1" isLeaf />
                    </TreeNode>
                    <TreeNode title="parent 1" key="0-1">
                      <TreeNode title="leaf 1-0" key="0-1-0" isLeaf />
                      <TreeNode title="leaf 1-1" key="0-1-1" isLeaf />
                    </TreeNode>
                  </DirectoryTree>
                </TabPane>
                <TabPane tab="常用诊断" key="2">
                  常用诊断
                </TabPane>
                <TabPane tab="病历病例" key="3">
                  病历病例
                </TabPane>
                <TabPane tab="特殊字符" key="4">
                  特殊字符
                </TabPane>
              </Tabs>
            </Card>
            
            <Card style={{height:'400px'}} title="模板内容">
            <Typography.Paragraph>模板内容 这个是模板 吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦
            吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦
            吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦吧喇叭啦
            </Typography.Paragraph>
            </Card>
          </Col>

          
        </Row>
      </Content>
    </Layout>)
  }
}

export default DiagnoseSection;