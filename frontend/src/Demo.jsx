import React from 'react';

import {Icon, Button, Form, InputNumber} from 'antd';

class Demo extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log(values)
      }
    });
  };

  render(){
    const { getFieldDecorator } = this.props.form;

    return (<Form onSubmit={this.handleSubmit.bind(this)}>
       <Form.Item label="科室编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入科室编号' }]
        })(
          <InputNumber
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="重要字段 请谨慎填写"
          />
        )}
      </Form.Item>
      <Button type="primary" htmlType="submit">submit</Button>
    </Form>)
  }
}


export default Demo;






