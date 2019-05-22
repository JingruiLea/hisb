import React from 'react';
import { Layout, Menu, Breadcrumb, Icon,Card,Typography } from 'antd';

//import '../login.css'
import LoginFrom from './LoginForm'

class LoginPage extends React.Component {
    
    componentDidMount() {
        document.getElementsByTagName("title")[0].innerHTML="登录"
        document.getElementsByTagName("body")[0].setAttribute("background","/bg.png")
        document.getElementsByTagName("body")[0].setAttribute("style",'background-size:cover')
    }

    render() {
        return (
        <div style={{paddingLeft:'150px',paddingTop:'200px'}}>
            <Card style={{width:'350px',height:'380px',paddingTop:'30px'}}>
                <Typography.Title>登录</Typography.Title>
                <LoginFrom/>
            </Card>
        </div>);
    }
}



export default LoginPage;