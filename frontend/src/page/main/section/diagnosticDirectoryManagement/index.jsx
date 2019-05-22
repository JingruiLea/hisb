import React from 'react';
import { Layout, Menu, Breadcrumb, Icon, Typography, Divider } from 'antd';
import { Tree } from 'antd';
import { Row, Col,Card } from 'antd';
import DiagnosticDirectoryManagementTable from './DiagnosticDirectoryManagementTable'

const {Content,Footer} = Layout
const { TreeNode } = Tree;


const data = []

for(var i=0;i<100;i++) {
  data.push({
    title:'某些传染病和寄生虫病',
    key:i+"",
    subnode:[
      {
        title:'肠道传染病',
        key:i+"-1"
      },
      {
        title:'肠道传染病',
        key:i+"-2"
      },
      {
        title:'肠道传染病',
        key:i+"-3"
      },
      {
        title:'肠道传染病',
        key:i+"-4"
      }
    ]
  })
}

class DiagnosticDirectoryManagementSection extends React.Component {

  render() {
    return (
      <Content style={{ margin: '0 16px' }}>
        <Row>
          <Col span={4} style={{minWidth:'100px'}}>
            <Card style={{overflow:'scroll',minWidth:'100px',height:'800px'}} >
            <Typography.Paragraph>疾病目录分类</Typography.Paragraph>
            <Tree showLine defaultExpandedKeys={['0-0-0']} onSelect={this.onSelect}>
              {data.map(data=>(
                <TreeNode title={data.title} key={data.key}>
                  {data.subnode.map(data=>(
                    <TreeNode title={data.title} key={data.key}/>
                  ))}
                </TreeNode>
              ))}
              </Tree>
            </Card>
          </Col>
          <Col span={20}>
            <Card  style={{minHeight:'800px'}}>
              <DiagnosticDirectoryManagementTable/>
            </Card>
          </Col>
        </Row>
      </Content>)
  }

}

export default DiagnosticDirectoryManagementSection;