import React from 'react';
import { Table, Button } from 'antd';
import DashboardHeader from './DashboardHeader';

const columns = [
  {
    title: '疾病编码',
    dataIndex: 'code',
  },
  {
    title: '疾病名称',
    dataIndex: 'name',
  },
  {
    title: '拼音码',
    dataIndex: 'pinying',
  },
  {
    title: '五笔码',
    dataIndex: 'wubi',
  }
];

const data = [];
for (let i = 0; i < 46; i++) {
  data.push({
    key: i,
    code: `B18.104`,
    name: '病毒性质肝炎',
    pinying:'BDXGY',
    wubi:'BDXGY'
  });
}

class DiagnosticDirectoryManagementTable extends React.Component {
  state = {
    selectedRowKeys: [], // Check here to configure the default column
    loading: false,
  };

  start = () => {
    this.setState({ loading: true });
    // ajax request after empty completing
    setTimeout(() => {
      this.setState({
        selectedRowKeys: [],
        loading: false,
      });
    }, 1000);
  };

  onSelectChange = selectedRowKeys => {
    console.log('selectedRowKeys changed: ', selectedRowKeys);
    this.setState({ selectedRowKeys });
  };

  render() {
    const { loading, selectedRowKeys } = this.state;
    const rowSelection = {
      selectedRowKeys,
      onChange: this.onSelectChange,
    };
    const hasSelected = selectedRowKeys.length > 0;
    return (
      <div>
        <div style={{ marginBottom: 16 }}>
          <Button type="primary" onClick={this.start} disabled={!hasSelected} loading={loading}>
            Reload
          </Button>
          <span style={{ marginLeft: 8 }}>
            {hasSelected ? `Selected ${selectedRowKeys.length} items` : ''}
          </span>
        </div>
        <Table rowSelection={rowSelection} columns={columns} dataSource={data}  />
      </div>
    );
  }
}


export default DiagnosticDirectoryManagementTable;