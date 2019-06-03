import React from 'react';
import {Row,Col,Layout,Card,Typography,Pagination, Form, Table, Divider, Button, Spin} from 'antd'
import { Tree,Input,DatePicker } from 'antd';
import { Chart,Geom,Axis,Tooltip} from "bizcharts";

const cols = {
  sales: {
    tickInterval: 20
  }
};

const { RangePicker, MonthPicker } = DatePicker;
const {Content, Footer} = Layout;


/************* 加载数据 ****************** */
const column = [
  {"title":"员工/费用类别","dataIndex":"col0"},
  {"title":"中药费","dataIndex":"col1"},
  {"title":"西药费","dataIndex":"col2"},
  {"title":"门诊费","dataIndex":"col3"},
  ]

const tableData = [
  {"col0":"路人甲","col1":100,"col2":100,"col3":100,"key":1},
  {"col0":"炮兵乙","col1":100,"col2":100,"col3":100,"key":2},
  {"col0":"土匪丁","col1":100,"col2":100,"col3":100,"key":3},
  ]

const chartsData = [
  {"name":"路人家",sum:300},
  {"name":"炮兵乙",sum:300},
  {"name":"土匪丁",sum:300},
];
  
/************************************************************/

class OutpatientPersonalWorkloadStatictic extends React.Component {

  state = {};//null

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
        <Typography.Title style={{textAlign:'center'}} level={3}>个人工作量统计</Typography.Title>

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
          <Button type="primary" icon="search" htmlType="submit">查询</Button>
        </Form>


        {this.state===null?<Spin style={{textAlign:'center',paddingTop:300,paddingLeft:'49%'}}/>:
        <div>
          <br/>
          <Table columns={column} dataSource={tableData} pagination={false}></Table>
          
          <br/>
          <Typography.Title style={{textAlign:'center'}} level={3}>个人总收入统计</Typography.Title>
          <Chart height={400} data={chartsData} scale={cols} forceFit>
            <Axis name="name" />
            <Axis name="sum" />
            <Tooltip
              crosshairs={{
                type: "y"
              }}
            />
            <Geom type="interval" position="name*sum" />
          </Chart>
        </div>}

    </Content>)
  }
}

export default Form.create({ name: 'form' })(OutpatientPersonalWorkloadStatictic);
