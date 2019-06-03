import React from 'react';
import {Radio,Layout,Card,Icon,DatePicker, Form, Table, Button} from 'antd'
import { Tree,Input } from 'antd';

const {Content, Footer} = Layout;

const siderColumn = [
  {
    title:'病历号',
    key:'medical_record_number',
    dataIndex:'medical_record_number'
  },{
    title:'姓名',
    key:'name',
    render:(row)=>(<Button type="link" onClick={()=>{this.props.selectRecord(row.id)}}>{row.name}</Button>)
  },{
    title:"发票号",
    key:"bill_number",
    dataIndex:"bill_number"
  }
]

const data = [
  {
    key:1,
    id:1,
    medical_record_number:'0120000',
    name:'刘金星',
    bill_number:'09831239'
  }
]


class Sider extends React.Component {
  state = {
    uid:1,
    searchResult:[
      {
        key:1,
        medical_record_number:'0120000',
        name:'刘金星',
        bill_number:'09831239'
      }
    ]
  }

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        if(values.date) {
          values.date = values.date.format('YYYY-MM-DD')
        }
        console.log('Received values of form: ', values);
      }
    });
  };

  handleRowClick=(e1,e2,e3)=>{
    console.log(e1,e2,e3)
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return(
      <div>
          <Form onSubmit={this.handleSubmit.bind(this)}>
            <Card title={
              <span>
                <span>查询退药列表</span>
                <Button icon="sync" style={{float:'right'}} htmlType="submit">查询</Button>
              </span>
            } style={{minWidth:'100px',maxHeight:'440px'}} >
              <Form.Item>
                {getFieldDecorator('number', {
                  rules: [{ required: true, message: '输入病例号/发票号' }],
                })(
                  <Input
                    prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="病例号/发票号"
                  />
                )}
              </Form.Item>
              <Form.Item>
                {getFieldDecorator('date', {})(<DatePicker style={{width:'100%'}}/>)}
              </Form.Item>
              <Form.Item>
                {getFieldDecorator('status', {})(
                <Radio.Group name="radiogroup" defaultValue={1}>
                  <Radio value={1}>待缴费</Radio>
                  <Radio value={2}>待领取</Radio>
                  <Radio value={3}>已领取</Radio>
                  <Radio value={4}>已退费</Radio>
                </Radio.Group>
                )}
              </Form.Item>
              <Table columns={siderColumn} dataSource={this.state.searchResult} pagination={false}/>
            </Card>
          </Form>
          

          <Card title="退药列表" style={{minWidth:'100px',height:'400px'}} >
            <Table columns={siderColumn} dataSource={data} />
          </Card>
      </div>)
  }

}

export default Form.create({ name: 'sider_form' })(Sider);;