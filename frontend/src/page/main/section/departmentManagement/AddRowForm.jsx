import React from 'react';
import { Layout, Button,Input,Modal,Breadcrumb,Form,Icon,Select} from 'antd';
const confirm = Modal.confirm;

const Option = Select.Option

class AddRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received post values of form: ', values);
        this.props.newRow(values)
      }
    });
  };


  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 },
      },
    };

    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
       <Form.Item label="科室名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入科室名称' }],
        })(
          <Input
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="科室名称"
          />,
        )}
      </Form.Item>
      <Form.Item label="科室编码">
        {getFieldDecorator('code', {
          rules: [{ required: true, message: '输入科室编码' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入科室编码"
          />,
        )}
        </Form.Item>
        <Form.Item label="科室类别">
        {getFieldDecorator('classification', {
          rules: [{ required: true, message: '选择科室类别' }],
        })(
          <Select
            name="classification"
            showSearch
            placeholder="选择类型"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.classification.map((x)=>(<Option value={x} key={x}>{x}</Option>))}
            </Select>
        )}
        </Form.Item>
        <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);
