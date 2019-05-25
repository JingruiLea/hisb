import React from 'react';
import { Layout, Button,Input,Modal,Breadcrumb,Form,Icon,Select} from 'antd';
const confirm = Modal.confirm;

const Option = Select.Option

class EditRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received editRow values of form: ', values);
        values.id = form.props.data.key;
        this.props.updateRow(values);
        this.props.exit();
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const data = this.props.data;
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
      <Form.Item>
          <Input
            value={data.id}
            hidden
            name="id"
          />
      </Form.Item>
       <Form.Item label="科室名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入科室名称' }],
          initialValue:data.name
        })(
          <Input
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入科室名称"
          />
        )}
      </Form.Item>
      <Form.Item label="科室编码">
        {getFieldDecorator('code', {
          rules: [{ required: true, message: '输入科室编码' }],
          initialValue:data.code
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入科室编码"
          />
        )}
        </Form.Item>
        <Form.Item label="科室类型">
        {getFieldDecorator('classification', {
          rules: [{ required: true, message: '选择科室类别' }],
          initialValue:data.classification
        })(
          <Select
            name="classification"
            showSearch
            placeholder="请选择类型"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.departmentClassification.map((x)=>(<Option value={x} key={x}>{x}</Option>))}
            </Select>
        )}
        </Form.Item>
        <Button htmlType="submit" type="primary">修改</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);
