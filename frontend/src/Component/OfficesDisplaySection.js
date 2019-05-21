import React from 'react';
import { Layout, Button } from 'antd';
import { Table, Divider, Tag } from 'antd';
import DashboardHeader from './DashboardHeader';


const { Column, ColumnGroup } = Table;
const { Header, Content, Footer} = Layout;

const columns = [
    {
        title: 'Name',
        dataIndex: 'name',
    },
    {
        title: 'Age',
        dataIndex: 'age',
    },
    {
        title: 'Address',
        dataIndex: 'address',
    },
    {
        title: 'Address1',
        dataIndex: 'address',
    },
    {
        title: 'Address2',
        dataIndex: 'address',
    },
    {
        title: 'Address3',
        dataIndex: 'address',
    },
];

const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key: i,
    name: `Edward King ${i}`,
    age: 32,
    address: `London, Park Lane no. ${i}`,
    address1: `London, Park Lane no. ${i}`,
    address2: `London, Park Lane no. ${i}`,
    address3: `London, Park Lane no. ${i}`,
  });
}

class OfficesDisplaySection extends React.Component {
    render() {
        return (
        <Layout>
            <DashboardHeader/>
            <br></br>
            
            <Content style={{ margin: '0 16px' }}>
                <Table 
                    columns={columns} 
                    dataSource={data} />
            </Content>

            <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
        </Layout>)
    }
}

export default OfficesDisplaySection;