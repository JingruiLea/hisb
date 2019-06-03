import React from 'react';
import {Row,Col,Layout,Card,Typography,Pagination, Form, Table, DatePicker, Button, Spin} from 'antd'

const { RangePicker, MonthPicker } = DatePicker;
const {Content, Footer} = Layout;


/************* 加载数据 ****************** */

const columns=[];
const tableData=[];

/************************************************************/

class OutpatientDailyCheck extends React.Component {

  state = {
    inited:false
  };

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const range = values.range;
        console.log(range);
        const start_date = range[0].format('YYYY-MM-DD');
        const end_date = range[1].format('YYYY-MM-DD');
        console.log('Received values of form: ', start_date,end_date);
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    return(
      <Content style={{ margin: '0 16px',paddingTop:30 }}>
        <Typography.Title style={{textAlign:'center'}} level={3}>门诊日结</Typography.Title>

        <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
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
          <Button type="primary" icon="search" htmlType="submit">查询</Button>&nbsp;
          <Button type="danger" icon="save" htmlType="submit" disabled={!this.state.inited}>结算</Button>
        </Form>


        {!this.state.inited?<Spin style={{textAlign:'center',paddingTop:300,paddingLeft:'49%'}}/>:
          <Table columns={columns} dataSource={tableData} pagination={false}></Table>
        }

    </Content>)
  }
}

export default Form.create({ name: 'form' })(OutpatientDailyCheck);

