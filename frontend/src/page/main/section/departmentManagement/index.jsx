import React from 'react';
import { Layout, Button,Input,Icon, Table, Divider, Tag,Spin,Typography} from 'antd';
import Highlighter from 'react-highlight-words';
import axios from 'axios';
import ToolBar from './ToolBar';
import API from '../../../../global/ApiConfig';
import Status from '../../../../global/Status';
import Message from '../../../../global/Message';

const {Content} = Layout;

/*
const tableData = [];
for (let i = 0; i < 10; i+=2) {
  tableData.push({
    key: i,
    code:'IX'+(i+100000),
    name:'心血管内科',
    is_clinical:true,
    classification:'内科'
  })
  tableData.push({
    key: i+1,
    code:'IX'+(i+100001),
    name:'放射科',
    is_clinical:false,
    classification:'医学影像科'
  })
}
*/

class DepartmentManagement extends React.Component {
    state = {
        searchText: '',
        selectedRows:[],
        tableData:[],
        classification:[],
        loading:true
    };
    

    rowSelection = {
        onSelect: (record, selected, selectedRows) => {
          //console.log(record, selected, selectedRows);
          //this.setState({selectedRows:selectedRows})
        },
        onSelectAll:(record, selected, selectedRows) => {
            //console.log(record, selected, selectedRows);
            //this.setState({selectedRows:selectedRows})
        },
        onChange: (selectedRowKeys, selectedRows) => {
            this.setState({selectedRows:selectedRows})
            //console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
    };
    
    getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div style={{ padding: 8 }}>
            <Input
                ref={node => { this.searchInput = node;}}
                placeholder={`Search ${dataIndex}`}
                value={selectedKeys[0]}
                onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
                style={{ width: 188, marginBottom: 8, display: 'block' }}
            />
            <Button
                type="primary"
                onClick={() => this.handleSearch(selectedKeys, confirm)}
                icon="search"
                size="small"
                style={{ width: 90, marginRight: 8 }}
            >
                Search
            </Button>
            <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                Reset
            </Button>
            </div>
        ),
        filterIcon: filtered => (
            <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />
        ),
        onFilter: (value, record) =>
            record[dataIndex]
            .toString()
            .toLowerCase()
            .includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: visible => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: text => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text.toString()}
            />
        ),
    });

    columns = [
        {
            title: '科室编码',
            dataIndex: 'code',
            ...this.getColumnSearchProps('code'),
        },
        {
            title: '科室名称',
            dataIndex: 'name',
            ...this.getColumnSearchProps('name'),
        },
        {
            title: '科室分类',
            dataIndex: 'is_clinical',
            render:(data)=>(<Tag color={data?"geekblue":"green"} >
                {data?"临床":"医技"}
            </Tag>),
            sorter:(a,b)=>{return a.is_clinical-b.is_clinical}//return a.length() - b.length()}
        },
        {
            title: '科室类别',
            dataIndex: 'classification',
            ...this.getColumnSearchProps('classification'),
        }
    ];

    //搜索
    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    };

    //搜索框重置
    handleReset = clearFilters => {
        clearFilters();
        this.setState({ searchText: '' });
    };

    //添加新行
    addRow = (e)=>{
        console.log('add row',e)
    }

    componentDidMount = ()=>{
        this.reloadData();
    }

    /***************************************  数据交互   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        const _this = this;
        this.setState({loading:true})
        axios({
            method: API.getDepartmentInfo.method,
            url: API.getDepartmentInfo.url,
            data: {},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            //console.log('receive',data)
            if(data.code===Status.Ok) {
                var tableData = data.data.department;
                for(var d of tableData) {d.key = d.id;}
                this.setState({
                    tableData:data.data.department,
                    classification:data.data.classification,
                    loading:false
                });
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
            Message.showNetworkErrorMessage();
        });
    }

    updateRow=(data)=>{
        const _this = this;
        axios({
            method: API.updateDepartmentInfo.method,
            url: API.updateDepartmentInfo.url,
            data: data,
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                this.reloadData();
                Message.openNotification("修改成功","")
                //this.setState({tableData:data.data,loading:true})
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
            Message.showNetworkErrorMessage();
        });
    }

    newRow=(data)=>{
        const _this = this;
        axios({
            method: API.addDepartmentInfo.method,
            url: API.addDepartmentInfo.url,
            data: data,
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                this.reloadData();
                Message.openNotification("添加数据成功","")
               // this.setState({tableData:data.data,loading:false})
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
           Message.showNetworkErrorMessage();
        });
    }

    deleteRow=(data)=>{
        const _this = this;
        axios({
            method: API.deleteDepartmentInfo.method,
            url: API.deleteDepartmentInfo.url,
            data: {data:data},
            crossDomain: true
          }).then((res)=>{
            const data = res.data;
            console.log('receive',data)
            if(data.code===Status.Ok) {
                _this.reloadData();
                Message.openNotification("删除数据成功","")
               // this.setState({tableData:data.data,loading:false})
            } else if(data.code===Status.PermissionDenied) {
                Message.showAuthExpiredMessage();
            } else {
                Message.showConfirm('错误',data.msg)
            }
        }).catch((err)=>{
           Message.showNetworkErrorMessage();
        });
    }
    

    render() {
        return (
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                classification={this.state.classification}

                updateRow={this.updateRow.bind(this)}
                deleteRow={this.deleteRow.bind(this)}
                newRow={this.newRow.bind(this)}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<Table 
                columns={this.columns} 
                dataSource={this.state.tableData} 
                rowSelection={this.rowSelection}
                addRow={this.addRow}
                reloadData={this.reloadData}
            />}
        </Content>)
    }
}

export default DepartmentManagement;