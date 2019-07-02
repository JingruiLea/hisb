package edu.neu.his.bean.statistics;

import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;

    @Transactional
    public List<Map<String,Object>> getFeeName (String start_date, String end_date, int department_id){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getExpenseClassifications(start_date,end_date,department_id,status);
    }

    @Transactional
    public List<Map<Object,Object>> getTotal(String start_date, String end_date){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getTotal(start_date,end_date,status);
    }

    @Transactional
    public List<Map<String,Object>> statisticsByUser(String start_date, String end_date, int user_id){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.statisticsByUser(start_date,end_date,user_id,status);
    }

    @Transactional
    public List<Map<Object,Object>> getTotalUser(String start_date, String end_date){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getTotalUser(start_date,end_date,status);
    }
}
