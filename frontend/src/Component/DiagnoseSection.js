import React from 'react';
import {Layout} from 'antd'
import DashboardHeader from './DashboardHeader'
import {Row,Col,Table,Card,Typography} from 'antd'
import { Tabs } from 'antd';
import { Tree } from 'antd';

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
    age:i*10
  })
}


class DiagnoseSection extends React.Component {
  render() {
    return (
    <Layout>
      <DashboardHeader/>
      <br/>

      <Content style={{ margin: '0 16px'}}>
        <Row>
          <Col span={5}>
            <Card>
              <Typography.Paragraph>待诊患者（共有{data.length}名患者）</Typography.Paragraph>
              <Table columns={columns} dataSource={data} pagination={true} size="small"/>
              <br/>
              <Typography.Paragraph>已诊患者（共有{data.length}名患者）</Typography.Paragraph>
              <Table columns={columns} dataSource={data} pagination={true} size="small" />
            </Card>
          </Col>

          <Col span={12}>3</Col>

          <Col span={7}>
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
              <TabPane tab="特殊字符" key="3">
                特殊字符
              </TabPane>
            </Tabs>
          </Col>

          
        </Row>
      </Content>
    </Layout>)
  }
}

export default DiagnoseSection;