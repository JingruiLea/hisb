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

//5 outpatientDoctor
import DiagnoseSection from '../../section/outpatientDoctor/diagnose'
import PatentMedicinePrescription from '../../section/outpatientDoctor/patentMedicinePrescription'

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
      const {sectionKey} = this.props;
      return (
        <div>
          {sectionKey==="0"?<PersonalWorkloadStatistic/>:null}

          {sectionKey==="3-1"?<DepartmentManagement/>:null}
          {sectionKey==="3-2"?<UserManagement/>:null}
          {sectionKey==="3-3"?<RegistrationLevelManagement/>:null}
          {sectionKey==="3-4"?<SettlementCategoryManagement/>:null}
          {sectionKey==="3-5"?<DiagnosticDirectoryManagementSection/>:null}
          {sectionKey==="3-6"?<NonDrugChargeItemManagement/>:null}
          {sectionKey==="3-7"?<SchedulingManagement/>:null}

          {sectionKey==="4-1"?<OutpatientRegistration/>:null}
          {sectionKey==="4-2"?<OutpatientCharge/>:null}
          {sectionKey==="4-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-5"?<div>ToBeImplement</div>:null}
          {sectionKey==="4-6"?<div>ToBeImplement</div>:null}

          {sectionKey==="5-1"?<DiagnoseSection/>:null}
          {sectionKey==="5-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-3"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-4"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-5"?<div>ToBeImplement</div>:null}
          {sectionKey==="5-6"?<PatentMedicinePrescription/>:null}
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
          {sectionKey==="6-3"?<PersonalWorkloadStatistic/>:null}

          {sectionKey==="7-1"?<OutpatientDispensingSection/>:null}
          {sectionKey==="7-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="7-3"?<DrugCatalogueManagement/>:null}

          {sectionKey==="8-1"?<ExpenseClassificationManagement/>:null}
          {sectionKey==="8-2"?<OupatientDailyCheck/>:null}
          {sectionKey==="8-3"?<OutpatientDepartmentsWorkloadStatistic/>:null}
          {sectionKey==="8-4"?<OutpatientPersonalWorkloadStatistic/>:null}
        </div>);
  }
}

export default DashboardSection;
