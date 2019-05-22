import React from 'react';

import { BrowserRouter, Route} from 'react-router-dom';
import LoginPage from '../Page/LoginPage';
import DashboardRouter from '../Page/DashboardPage';

import Demo from '../Demo'

class GlobalRouter extends React.Component {
  render() {
      return (
        <BrowserRouter>
            <Route exact path="/" component={LoginPage}/>
            <Route path="/login" component={LoginPage}/>

            <Route path="/diagnose" component={DashboardRouter}/>
            <Route path="/officeManage" component={DashboardRouter}/>
            <Route path="/diagnosticDirectoryManage" component={DashboardRouter}/>
            <Route path="/outpatientDispensing" component={DashboardRouter}/>
            <Route path="/outpatientDepartmentsWorkloadStatistic" component={DashboardRouter}/>
            
            <Route path="/demo" component={Demo}/>
        </BrowserRouter>);
  }
}

export default GlobalRouter;