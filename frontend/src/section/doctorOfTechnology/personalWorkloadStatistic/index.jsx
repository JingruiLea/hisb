import React from 'react';

class PersonalWorkloadStatistic extends React.Component {

  render() {
    return (
    <div>
      医生录入统计时间起始时间和截止时间，点击“统计”按钮，统计出该时间段内本人登记的患者数量、以及每个患者的对应执行科室为本科室的费用情况。注意，只能查询本人进行检查的项目收费情况，同一个患者其他医生执行的项目收费情况，是查询不到的。
    </div>)
  }
}

export default PersonalWorkloadStatistic;