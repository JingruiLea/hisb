import React from 'react';

//3 hospitalAdmin
import DepartmentManagement from '../../section/hospitialAdmin/departmentManagement'
import UserManagement from '../../section/hospitialAdmin/userManagement'
import SettlementCategoryManagement from '../../section/hospitialAdmin/settlementCategoryManagement'
import DiagnosticDirectoryManagementSection from '../../section/hospitialAdmin/diagnosticDirectoryManagement'
import RegistrationLevelManagement from '../../section/hospitialAdmin/registrationLevelManagement'
import NonDrugChargeItemManagement from '../../section/hospitialAdmin/nonDrugChargeItemManagement'
import SchedulingManagement from '../../section/hospitialAdmin/schedulingManagement'

//4 registeredTollCollector
import OutpatientRegistration from '../../section/registeredTollCollector/outpatientRegistration'
import OutpatientCharge from '../../section/registeredTollCollector/outpatientCharge'
import DailyCollectSection from '../../section/registeredTollCollector/dailyCollect'

//5 outpatientDoctor
import OutpatientDoctorWorkspace from '../../section/outpatientDoctor'

//6 pharmacyOperator
import OutpatientDispensingSection from '../../section/pharmacyOperator/outpatientDispensing'
import DrugCatalogueManagement from '../../section/pharmacyOperator/drugCatalogueManagement'

//7 doctorOfTechnology
import PersonalWorkloadStatistic from '../../section/doctorOfTechnology/personalWorkloadStatistic'

//8 financialAdmin
import ExpenseClassificationManagement from '../../section/financialAdmin/expenseClassificationManagement'
import OupatientDailyCheck from '../../section/financialAdmin/outpatientDailyCheck'
import OutpatientDepartmentsWorkloadStatistic from '../../section/financialAdmin/outpatientDepartmentsWorkloadStatistic'
import OutpatientPersonalWorkloadStatistic from '../../section/financialAdmin/outpatientPersonalWorkloadStatistic'


class DashboardSection extends React.Component {
  render() {
      const me = this.props.me;
      if(!me) {
        window.location.href="/login"
      }
      const {sectionKey} = this.props;
      return (
        <div>
          {sectionKey==="0"?<OutpatientDispensingSection me={this.props.me}/>:null}

          {sectionKey==="3-1"?<DepartmentManagement me={this.props.me}/>:null}
          {sectionKey==="3-2"?<UserManagement me={this.props.me}/>:null}
          {sectionKey==="3-3"?<RegistrationLevelManagement me={this.props.me}/>:null}
          {sectionKey==="3-4"?<SettlementCategoryManagement me={this.props.me}/>:null}
          {sectionKey==="3-5"?<DiagnosticDirectoryManagementSection me={this.props.me}/>:null}
          {sectionKey==="3-6"?<NonDrugChargeItemManagement me={this.props.me}/>:null}
          {sectionKey==="3-7"?<SchedulingManagement me={this.props.me}/>:null}

          {sectionKey==="4-1"?<OutpatientRegistration me={this.props.me}/>:null}
          {sectionKey==="4-2"?<OutpatientCharge me={this.props.me}/>:null}
          {sectionKey==="4-3"?<DailyCollectSection/>:null}

          {sectionKey==="5-1"?<OutpatientDoctorWorkspace/>:null}
          {sectionKey==="5-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-5"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-6"?<div>ToBrImplement</div>:null}
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
          {sectionKey==="6-3"?<PersonalWorkloadStatistic me={this.props.me}/>:null}

          {sectionKey==="7-1"?<OutpatientDispensingSection me={this.props.me}/>:null}
          {sectionKey==="7-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="7-3"?<DrugCatalogueManagement me={this.props.me}/>:null}

          {sectionKey==="8-1"?<ExpenseClassificationManagement me={this.props.me}/>:null}
          {sectionKey==="8-2"?<OupatientDailyCheck me={this.props.me}/>:null}
          {sectionKey==="8-3"?<OutpatientDepartmentsWorkloadStatistic me={this.props.me}/>:null}
          {sectionKey==="8-4"?<OutpatientPersonalWorkloadStatistic me={this.props.me}/>:null}
        </div>);
  }
}

export default DashboardSection;
