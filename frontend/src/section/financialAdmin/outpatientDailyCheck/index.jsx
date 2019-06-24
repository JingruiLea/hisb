import React from 'react';
import {Layout,Typography, Form, DatePicker, Button, Spin, Select, Empty, Descriptions} from 'antd'
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';

const { RangePicker } = DatePicker;
const {Content} = Layout;

class OutpatientDailyCheck extends React.Component {

  state = {
    loading:true,
    collectors:[],
    data:null
  };

  componentDidMount=()=>{
    this.loadCollectors();
  }

  loadCollectors=()=>{
    API.request(API.financialAdmin.dailyReportCheck.init)
    .ok(collectors=>{
      this.setState({collectors,loading:false})
    }).submit()
  }

  requestCheck=(start_date,end_date,toll_collector_id)=>{
    API.request(API.financialAdmin.dailyReportCheck.getReport,{
      toll_collector_id,
      start_date,
      end_date
    }).ok(data=>{
      //console.log(data)
      this.setState({data})
    }).submit();
  }

  submitCheck=(start_date,end_date,toll_collector_id)=>{
    API.request(API.financialAdmin.dailyReportCheck.confirmCheck,{
      toll_collector_id,
      start_date,
      end_date
    }).ok(data=>{
      Message.success('核对通过！')
    }).submit();
  }

  handleSubmit = (mode)=> {
    //e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const range = values.range;
        const start_date = range[0].format('YYYY-MM-DD');
        const end_date = range[1].format('YYYY-MM-DD');
        //console.log('Received values of form: ', start_date,end_date);
        if(mode==="search")
          this.requestCheck(start_date,end_date,values.toll_collector_id)
        else if(mode==="submit") 
          Message.showConfirm('财务入库','你确认要完成财务入库的地方吗？',()=>{
            this.submitCheck(start_date,end_date,values.toll_collector_id)
          });
      }
    });
  };

  render() {
    const {collectors,loading,data} = this.state;
    const { getFieldDecorator } = this.props.form;
    return(
      <Content style={{ margin: '0 16px',paddingTop:30 }}>
        <Typography.Title style={{textAlign:'center'}} level={3}>门诊日结核对</Typography.Title>

        <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
          <Form.Item label="收费员" hasFeedback>
            {getFieldDecorator('toll_collector_id', {
              rules: [
                {
                  required: true,
                  message: '收费员!',
                }
              ],
            })(<Select style={{width:'200px'}} >
             {collectors.map(collector=>(
                <Select.Option value={collector.id} key={collector.id}>{collector.name}</Select.Option>
             ))}
            </Select>)}
          </Form.Item>
          <Form.Item label="日期" hasFeedback>
            {getFieldDecorator('range', {
              rules: [
                {
                  required: true,
                  message: '选择日期!',
                }
              ],
            })(<RangePicker renderExtraFooter={() => 'extra footer'} showTime />)}
          </Form.Item>
          <Button type="primary" icon="search"  disabled={this.state.loading} onClick={()=>this.handleSubmit("search")}>查询</Button>&nbsp;
          <Button type="danger" icon="save" disabled={this.state.loading} onClick={()=>{this.handleSubmit("submit")}}>核对通过</Button>
        </Form>


        {loading?<Spin style={{textAlign:'center',paddingTop:300,paddingLeft:'49%'}}/>:null}
        {!data?<Empty image={Empty.PRESENTED_IMAGE_SIMPLE}/>:
        <Descriptions>

        </Descriptions>}

    </Content>)
  }
}

export default Form.create({ name: 'form' })(OutpatientDailyCheck);

