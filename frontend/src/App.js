import React from 'react';

import zhCN from 'antd/lib/locale-provider/zh_CN';
import { LocaleProvider } from 'antd';
import GlobalRouter from './Router/GlobalRouter'
import logo from './logo.svg';


function App() {
  return (
    <div>
      <LocaleProvider locale={zhCN}>
          <GlobalRouter/>
        </LocaleProvider>
    </div>
  );
}

export default App;
