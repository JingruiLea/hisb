import React from 'react';
import {Card,Badge,Table, Button, Icon,Input, Spin} from 'antd';
import Message from '../../global/Message';
import Highlighter from 'react-highlight-words';


class PatientSelector extends React.Component {


  state = {
    searchText:''
  }

  /**
   * 
   * currentPatient{
   *  registration:
   *  medicalRecord:
   * }
   */

  getColumnSearchProps = dataIndex => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
      <div style={{ padding: 8 }}>
        <Input
          ref={node => {
            this.searchInput = node;
          }}
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
          搜索
        </Button>
        <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
          重置
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


  handleSearch = (selectedKeys, confirm) => {
    confirm();
    this.setState({ searchText: selectedKeys[0] });
  };

  handleReset = clearFilters => {
    clearFilters();
    this.setState({ searchText: '' });
  };

  columns = [{
    title: '#',
    dataIndex: 'medical_record_id',
    ...this.getColumnSearchProps('medical_record_id'),
  },{
    title: '患者姓名',
    ...this.getColumnSearchProps('patient_name'),
    render:(registraion)=>
      (<span>
      {this.isRegistrationCurrent(registraion)?
      <Badge color="#f50" text={registraion.patient_name} />:
      <span>{registraion.patient_name}</span>}
      </span>)
  }]

  render() {
    const {state} = this;
    //变量
    const {patientList,currentPatient,loading} = this.props;
    //方法
    const {selectPatient} = this.props;
    const {waiting,pending} = patientList;

    return (
      <Card style={{height:'870px'}}>
        <div>
          {loading?<Spin style={{float:'right'}}/>:null}
          <p style={{float:'left'}}>当前诊断患者：<b>{currentPatient.registration?currentPatient.registration.patient_name:"未选择"}</b></p>
        </div>

        <Table
          onRow={registraion=>{
            return {
              onDoubleClick:event=>{
                Message.showConfirm(
                '确认',
                `你确定要看诊${registraion.patient_name}吗？未保存的信息会丢失。`,
                ()=>{selectPatient(registraion)},
                ()=>{})
              }
            }
          }}
          title={()=>`待诊（共${waiting.length}名患者)`}
          columns={this.columns} dataSource={waiting} 
          pagination={{pageSize:6}} size="small"/>

        <Table 
          onRow={registraion=>{
            return {
              onDoubleClick:event=>{
                Message.showConfirm(
                '确认',
                `你确定要看诊${registraion.patient_name}吗？未保存的信息会丢失。`,
                ()=>{selectPatient(registraion)},
                ()=>{})
              }
            }
          }}
          title={()=>`暂存（共${pending.length}名患者)`}
          columns={this.columns} dataSource={pending} 
          pagination={true} pagination={{pageSize:6}} 
          size="small" />
      </Card>
    )
  }
}

export default PatientSelector;