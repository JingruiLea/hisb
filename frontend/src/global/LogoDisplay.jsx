import React from 'react';
import logo from './logo.png'

class LogoDisplay extends React.Component {
  render() {
    return(
      <div style={{textAlign:'center',margin:'0 auto'}}>
        <img style={{padding:'50px',textAlign:'center'}} alt="his-logo" src={logo} />
      </div>
    )
  }
} 

export default LogoDisplay;