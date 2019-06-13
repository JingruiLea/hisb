import React from 'react';
import {Row,Input,Table,Radio,Checkbox,Select, Button} from 'antd'
import Message from "../../../global/Message";
const {Option} = Select;

class DiagnoseSelectionTable extends React.Component {

  chineseDiagnoseColumns = [
    {
      title: '诊断编码',
      dataIndex: 'disease_code',
      key: 'disease_code',
    },{
      title: '诊断名称',
      dataIndex: 'disease_name',
      key: 'disease_name',
    },{
      title: '辩证分型',
      key: 'syndrome_differentiation',
      dataIndex:'syndrome_differentiation',
      render:(syndrome_differentiation,record,index)=>{
        return(
        <Input
          value={syndrome_differentiation}
          onChange={(e)=>{
            const {primaryDiagnose} = this.props;
            const input = e.target.value;
            var editedRecord = record;
            editedRecord.syndrome_differentiation = input;
            var newData = primaryDiagnose;
            newData.chineseDiagnoseData[index] = editedRecord;
            this.props.setPrimaryDiagnose(newData)
          }}
        />)
      }
    },{
      title: '操作',
      key:'operate',
      render:(text,record,index)=>(<span>
        <a onClick={()=>{
            const {primaryDiagnose} = this.props;
            const key = record.key;
            var newData = primaryDiagnose;
            newData.chineseDiagnoseData =  newData.chineseDiagnoseData.filter(x=>x.key!==key);
            this.props.setPrimaryDiagnose(newData)
          }}>删除</a>
        </span>)
    }
  ];

  westernDiagnoseColumns = [
    {
      title: 'ICD编码',
      dataIndex: 'disease_code',
      key: 'disease_code',
    },{
      title: '名称',
      dataIndex: 'disease_name',
      key: 'disease_name',
    },{
      title: '主诊',
      key: 'main_symptom',
      dataIndex:'main_symptom',
      render:(main_symptom,record,index)=>{
        return(
        <Radio 
          checked={main_symptom}
          onClick={()=>{
            const {primaryDiagnose} = this.props;
            var newValue = !main_symptom;
            var newData = primaryDiagnose;
            newData.westernDiagnoseData.forEach(ele=>ele.main_symptom=false)
            newData.westernDiagnoseData[index].main_symptom = newValue;
            this.props.setPrimaryDiagnose(newData)
          }}
        />)
      }
    },{
      title: '疑似',
      dataIndex: 'suspect',
      key: 'suspect',
      render:(suspect,record,index)=>{
        return(
        <Checkbox 
          checked={suspect}
          onClick={()=>{
            const {primaryDiagnose} = this.props;
            var editedRecord = record;
            editedRecord.suspect = !suspect;
            var newData = primaryDiagnose;
            newData.westernDiagnoseData[index] = editedRecord;
            this.props.setPrimaryDiagnose(newData)
          }}
          />)
      }
    },{
      title: '操作',
      key:'operate',
      render:(text,record,index)=>(<span>
        <a onClick={()=>{
            const key = record.key;
            const {primaryDiagnose} = this.props;
            var newData = primaryDiagnose;
            newData.westernDiagnoseData = newData.westernDiagnoseData.filter(x=>x.key!==key);
            this.props.setPrimaryDiagnose(newData)
          }}>删除</a>
        </span>)
    }
  ];
  

  addNewWesternDiagnoseRow=(disease_id)=>{
    const {primaryDiagnose} = this.props;
    const {westernDiagnoseData} = primaryDiagnose;
    const {westernDiagnoseDiseases} = this.props.diagnoses;
    if(westernDiagnoseData.filter(x=>x.disease_id===disease_id).length>0) {
      Message.error('该诊断已经存在！')
      return;
    }
    const disease = westernDiagnoseDiseases.filter(x=>x.id===disease_id)[0];
    var newRow = {
      disease_id:disease_id,
      key:disease_id,
      disease_name:disease.name,
      disease_code:disease.code,
      main_symptom:false,
      suspect:false
    }
    if(westernDiagnoseData.length===0) newRow.main_symptom = true;
    var newData = primaryDiagnose;
    newData.westernDiagnoseData.push(newRow);
    this.props.setPrimaryDiagnose(newData);
  }

  addNewChineseDiagnoseRow=(disease_id)=>{
    const {primaryDiagnose} = this.props;
    const {chineseDiagnoseData} = primaryDiagnose;
    const {chineseDiagnoseDiseases} = this.props.diagnoses;
    if(chineseDiagnoseData.filter(x=>x.disease_id===disease_id).length>0) {
      Message.error('该诊断已经存在！')
      return;
    }
    const disease = chineseDiagnoseDiseases.filter(x=>x.id===disease_id)[0];
    var newRow = {
      disease_id:disease_id,
      key:disease_id,
      disease_name:disease.name,
      disease_code:disease.code,
      syndrome_differentiation:''
    }
    var newData = primaryDiagnose;
    newData.chineseDiagnoseData.push(newRow);
    this.props.setPrimaryDiagnose(newData);
  }

  render() {
    const {primaryDiagnose} = this.props;
    const {westernDiagnoseData,chineseDiagnoseData} = primaryDiagnose;
    const {westernDiagnoseDiseases,chineseDiagnoseDiseases} = this.props.diagnoses;
    return (
      <div>
         <Table
            title={()=>
            (<div>
              <span style={{marginRight:'10px'}}>西医诊断</span>
              <Select style={{width:'200px'}} 
                showSearch
                placeholder="选择一个西医诊断"
                optionFilterProp="children"
                onChange={(id)=>{
                  this.addNewWesternDiagnoseRow(id)
                }}
                filterOption={(input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }>
              {westernDiagnoseDiseases.map((x)=>
                (<Option value={x.id} key={x.id}>{x.name}</Option>)
                )}
              </Select>
            </div>)}
            columns={this.westernDiagnoseColumns}
            dataSource={westernDiagnoseData}
            pagination={false}
        />

        <Table
           title={()=>
            (<div>
              <span style={{marginRight:'10px'}}>中医诊断</span>
              <Select style={{width:'200px'}} 
                showSearch
                placeholder="选择一个中医诊断"
                optionFilterProp="children"
                onChange={(id)=>{
                  this.addNewChineseDiagnoseRow(id)
                }}
                filterOption={(input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }>
              {chineseDiagnoseDiseases.map((x)=>
                (<Option value={x.id} key={x.id}>{x.name}</Option>)
                )}
              </Select>
            </div>)}
          columns={this.chineseDiagnoseColumns}
          dataSource={chineseDiagnoseData}
          pagination={false}
        />
      </div>
    )
  }
}

export default DiagnoseSelectionTable;