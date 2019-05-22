import React from 'react';
import {Row,Col,Layout,Card,Typography,Pagination, Form, Table, Divider} from 'antd'
import { Tree,Input,DatePicker } from 'antd';
import DashboardHeader from './DashboardHeader'
import {
  G2,
  Chart,
  Geom,
  Axis,
  Tooltip,
  Coord,
  Label,
  Legend,
  View,
  Guide,
  Shape,
  Facet,
  Util
} from "bizcharts";

const data = [
  {
    year: "1951 年",
    sales: 38
  },
  {
    year: "1952 年",
    sales: 52
  },
  {
    year: "1956 年",
    sales: 61
  },
  {
    year: "1957 年",
    sales: 145
  },
  {
    year: "1958 年",
    sales: 48
  },
  {
    year: "1959 年",
    sales: 38
  },
  {
    year: "1960 年",
    sales: 38
  },
  {
    year: "1962 年",
    sales: 38
  }
];
const cols = {
  sales: {
    tickInterval: 20
  }
};

const { RangePicker, MonthPicker } = DatePicker;
const {Content, Footer} = Layout;



const columns = [
  {
    title:'看诊人次',
    key:'visiting_times',
    dataIndex:'visiting_times'
  },{
    title:'发票数量',
    key:'invoice_quantity',
    dataIndex:'invoice_quantity'
  },{
    title:'西药费',
    key:'western_medicine_fee',
    dataIndex:'western_medicine_fee'
  },{
    title:'中成药',
    key:'chinese_patent_medicine',
    dataIndex:'chinese_patent_medicine'
  },{
    title:'中草药',
    key:'chinese_herbal_medicine',
    dataIndex:'chinese_herbal_medicine'
  },{
    title:'挂号费',
    key:'registration_fee',
    dataIndex:'registration_fee'
  },{
    title:'诊查费',
    key:'examination_fee',
    dataIndex:'examination_fee'
  },{
    title:'检验费',
    key:'inspection_fee',
    dataIndex:'inspection_fee'
  },{
    title:'治疗费',
    key:'treatment_fee',
    dataIndex:'treatment_fee'
  },{
    title:'治疗费（材料费）',
    key:'treatment_material_fee',
    dataIndex:'treatment_material_fee'
  },{
    title:'手术治疗费',
    key:'surgical_treatment_fee',
    dataIndex:'surgical_treatment_fee'
  },{
    title:'其他治疗费',
    key:'other_treatment_cost',
    dataIndex:'other_treatment_cost'
  }
]

const data2=[]
for(var i=0;i<5;i++) data2.push({
  visiting_times:1,
  invoice_quantity:1,
  western_medicine_fee:1,
  chinese_patent_medicine:1,
  chinese_herbal_medicine:1,
  registration_fee:1,
  examination_fee:1,
  inspection_fee:1,
  treatment_fee:1,
  treatment_material_fee:1,
  surgical_treatment_fee:1,
  other_treatment_cost:1,
  key:1
})

class OutpatientDepartmentsWorkloadStatisticSection extends React.Component {
  render() {
    return(
      <Layout>
        <DashboardHeader/>
        <Content style={{ margin: '0 16px',paddingTop:30 }}>
          <Typography.Title style={{textAlign:'center'}} level={4}>门诊科室工作量统计（开单科室）</Typography.Title>
          <RangePicker renderExtraFooter={() => 'extra footer'} showTime />
          <br/><br/>
          <Table columns={columns} dataSource={data2} pagination={false}></Table>

          <Chart height={400} data={data} scale={cols} forceFit>
            <Axis name="year" />
            <Axis name="sales" />
            <Tooltip
              crosshairs={{
                type: "y"
              }}
            />
            <Geom type="interval" position="year*sales" />
          </Chart>
          
        </Content>
    </Layout>)
  }
}

export default OutpatientDepartmentsWorkloadStatisticSection;