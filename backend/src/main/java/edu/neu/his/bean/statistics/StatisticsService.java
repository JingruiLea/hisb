package edu.neu.his.bean.statistics;

import edu.neu.his.bean.exam.ExamStatus;
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
    public List<Map<String,Object>> getByPrescribe (String start_date, String end_date, int department_id){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getByPrescribe(start_date,end_date,department_id,status);
    }

    @Transactional
    public List<Map<Object,Object>> getTotalPrescribe(String start_date, String end_date){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getTotalPrescribe(start_date,end_date,status);
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

    @Transactional
    public List<Map<Object,Object>> getTotalPatient(String start_date, String end_date, int doctor_id){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.getTotalPatient(start_date,end_date,status,doctor_id);
    }

    @Transactional
    public List<Map<String,Object>> statisticsByDoctor(String start_date, String end_date, int medical_record_id){
        String status = OutpatientChargesRecordStatus.Charged;
        return statisticsMapper.statisticsByDoctor(start_date,end_date,medical_record_id,status);
    }

    @Transactional
    public List<Map<Object,Object>> getTotalRegister(String start_date, String end_date, int doctor_id, int department_id){
        String status = ExamStatus.Registered;
        return statisticsMapper.getTotalRegister(start_date,end_date,status,doctor_id,department_id);
    }

    @Transactional
    public List<Map<String,Object>> statisticsByRegister(String start_date, String end_date, int medical_record_id,int doctor_id, int department_id){
        String status = OutpatientChargesRecordStatus.Charged;
        int type = OutpatientChargesRecordStatus.Exam;
        return statisticsMapper.statisticsByRegister(start_date,end_date,medical_record_id,status,doctor_id,department_id,type);
    }
}
