import React from 'react';
import {Row,Col,Layout,Card,Tabs} from 'antd'

import API from '../../../global/ApiConfig';

const {Content,Header} = Layout;

class InspectionSection extends React.Component {

  componentDidMount=()=>{this.props.onRef(this)}

  render() {
    return (
      <div>

      </div>
    )
  }
}

export default InspectionSection;