import React from 'react';

import { BrowserRouter, Route} from 'react-router-dom';

import OfficesDisplaySection from './section/officesDisplay'
import DiagnosticDirectoryManagementSection from './section/diagnosticDirectoryManagement'
import DiagnoseSection from './section/diagnose'
import OutpatientDispensingSection from './section/outpatientDispensing'
import OutpatientDepartmentsWorkloadStatisticSection from './section/outpatientDepartmentsWorkloadStatistic'

class DashboardSection extends React.Component {
  render() {
      const {sectionKey} = this.props;
      return (
        <div>
          {sectionKey==="0"?<div>Welcome</div>:null}

          {sectionKey==="3-1"?<OfficesDisplaySection/>:null}
          {sectionKey==="3-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="3-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="3-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="3-5"?<DiagnosticDirectoryManagementSection/>:null}
          {sectionKey==="3-6"?<div>ToBeImplement</div>:null}
          {sectionKey==="3-7"?<div>ToBeImplement</div>:null}

          {sectionKey==="4-1"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-5"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-6"?<div>ToBeImplement</div>:null}

          {sectionKey==="5-1"?<DiagnoseSection/>:null}
          {sectionKey==="5-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-5"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-6"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-7"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-8"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-9"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-10"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-11"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-12"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-13"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-14"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-15"?<div>ToBeImplement</div>:null}

          {sectionKey==="6-1"?<div>ToBeImplement</div>:null}
          {sectionKey==="6-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="6-3"?<div>ToBeImplement</div>:null}

          {sectionKey==="7-1"?<div>ToBeImplement</div>:null}
          {sectionKey==="7-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="7-3"?<div>ToBeImplement</div>:null}

          {sectionKey==="8-1"?<OutpatientDispensingSection/>:null}
          {sectionKey==="8-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="8-3"?<OutpatientDepartmentsWorkloadStatisticSection/>:null}
        </div>);
  }
}

export default DashboardSection;
