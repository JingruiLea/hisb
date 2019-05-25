//import React from 'react';
import {Modal,notification} from 'antd';

const confirm = Modal.confirm; 

export default {
  showConfirm:(title,content)=> {
    confirm({
      title: title,
      content: content,
      onOk() {
        return new Promise((resolve, reject) => {
          setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
        }).catch(() => console.log('Oops errors!'));
      },
      onCancel() {},
    });
  },
  showAuthExpiredMessage:()=> {
    confirm({
      title: '退出',
      content: '您的登录已过期，请重新登录',
      onOk() {
        return new Promise((resolve, reject) => {
          setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
        }).catch(() => window.location.href="/login");
      },
      onCancel() {
        return new Promise((resolve, reject) => {
          setTimeout(Math.random() > 0.5 ? resolve : reject, 1000);
        }).catch(() => window.location.href="/login");
      },
    });
  },
  showNetworkErrorMessage:()=> {
    confirm({
      title: '错误',
      content: '无法连接服务器，请检查你的网络配置',
      onOk() {},
      onCancel() {},
    });
  },
  openNotification: (message,description) => {
    notification.open({
      message: message,
      description:description,
      duration:1,
      onClick: () => {
        console.log('Notification Clicked!');
      },
    });
  }
}