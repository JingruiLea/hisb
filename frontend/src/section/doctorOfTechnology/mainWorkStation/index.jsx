import React from 'react'
import {Row,Col} from 'antd'

class MainWorkStation extends React.Component {

  
  render() {
    const {me} = this.props;
    console.warn(me);

    return(
    <Row>
      <Col span={6}>
      
      </Col>
      <Col span={18}>
      
      </Col>
    </Row>)
  }
}

export default MainWorkStation;