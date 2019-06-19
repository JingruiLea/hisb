import React from 'react';
import { Layout, Button,Input,Modal,Breadcrumb,Form,Icon} from 'antd';
//import AddRowForm from './AddShiftRowForm';
//import EditRowForm from './EditShiftRowForm';
//import BatchImportUpload from './BatchImportUpload';

const confirm = Modal.confirm;

class ToolBar extends React.Component {
  state = {
    //editModalDisplay:false,
    //addRowModalDisplay:false,
    //batchImportModalDisplay:false
  }

  //更新数据
  reloadData = ()=>{this.props.reloadData()}

  //确认选择弹窗
  showChooseConfirm() {
    const rowsNum = this.props.selectedRows.length;
    const _this = this;
    confirm({
      title: '你确定要开始排班吗',
      content: ` `,
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        //删除需要覆盖的
        if(_this.props.overwrite){
          _this.props.overwriteInfo(_this.props.overwriteIds);
          console.log('确认按钮:overwrite');
        }
       // _this.props.updateSchedule();

       //生成排班信息
        const selectedRows = _this.props.selectedRows;
        console.log('selectedRows',selectedRows);
        _this.props.chooseRow(selectedRows,_this.props.num);
      },
      onCancel() {
        console.log('Cancel clicked');
      },
    });
  }

  render() {
      const selectedRows = this.props.selectedRows;
      const chooseButtonDisabled = selectedRows.length===0 || this.props.disabled ||this.props.inputDate;
      const chooseNum = selectedRows.length===0?null:selectedRows.length+"条记录";
      return (
        
      <div>
        <div>
        <div style={{float:'right',margin:5}}>

          <Button onClick={this.props.reloadData}>
            <Icon type="sync" spin={this.props.disabled}></Icon>
          </Button>
          <Button 
            type="danger"
            disabled={chooseButtonDisabled}
            onClick={this.showChooseConfirm.bind(this)}
          >
          选择{chooseNum}
          </Button>
          
        </div>
        </div>
      </div>)
  }
}

export default ToolBar;
