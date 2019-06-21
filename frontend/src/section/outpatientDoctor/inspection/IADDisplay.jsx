import React from 'react';
import { Collapse, Table, Button,Typography} from 'antd';

const Panel = Collapse.Panel;

class IADDisplay extends React.Component {

  state={
    toolBarDisable:false
  }

  componentDidMount=()=>{this.props.onRef(this)}
  disableToolBar=()=>this.setState({toolBarDisable:true})
  enableToolBar=()=>this.setState({toolBarDisable:false})

  render() {
    const {openEditor,list,disabled} = this.props;
    const {toolBarDisable} = this.state;

    list.forEach(data=>{
      data.key = data.id;
      data.sum = data.items.map(x=>x.non_drug_item.fee).reduce((prev, curr, idx, arr)=>prev + curr);
      data.items.forEach(x=>x.key=x.id)
    })
    return(
      <div>
        <Typography.Title level={4}>
          当前共开具 <b>{list.length}</b> 组检查
          <Button 
            style={{float:'right',marginRight:20}}
            type="primary" 
            icon="plus"
            disabled={disabled || toolBarDisable}
            onClick={()=>{this.props.openEditor("new",[])}}
            >新建</Button>
        </Typography.Title>

        <Collapse accordion bordered={false}>
          {list.map(data=>(
            <Panel header={
              <span>
                <span style={{marginRight:'10px'}}>检查编号：{data.id}</span>
                <span style={{marginRight:'10px'}}>状态：{data.status}</span>
              </span>
            } key={data.id}>
              <Table
                size="small"
                title={()=>(
                  <span>
                    <span style={{marginRight:'30px'}}>创建时间：{data.create_time}</span>
                    <span style={{marginRight:'30px'}}>总费用：{data.sum}</span>              
                    <span style={{marginRight:'30px'}}>
                    {data.status==="暂存"?
                      <Button 
                        size="small" 
                        disabled={toolBarDisable}
                        style={{marginRight:'10px'}}
                        onClick={()=>openEditor("update",data.items.map(x=>x.non_drug_item),data.id)} 
                        type="primary">
                        编辑
                      </Button>:null}
                      {data.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.deleteIAD([data.id])} 
                        type="danger">
                        删除
                      </Button>:null}
                      {data.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.sendIAD([data.id])} 
                        type="primary">
                        发送
                      </Button>:null}
                      {data.status==="已提交"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.cancelIAD([data.id])} 
                        type="danger">
                        作废
                      </Button>:null}
                    </span>
                  </span>
                )}
                dataSource={data.items}
                columns={[
                  {title:"项目名称",dataIndex:"non_drug_item.name"},
                  {title:"项目编码",dataIndex:"non_drug_item.code"},
                  {title:"拼音",dataIndex:"non_drug_item.pinyin"},
                  {title:"规格",dataIndex:"non_drug_item.format"},
                  {title:"费用",dataIndex:"non_drug_item.fee"}
                ]}
              />
            </Panel>
          ))}
        </Collapse>
      </div>
    )
  }

}

export default IADDisplay;