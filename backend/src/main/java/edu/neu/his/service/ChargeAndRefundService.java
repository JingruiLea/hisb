package edu.neu.his.service;

import edu.neu.his.bean.OutpatientChargesRecord;
import edu.neu.his.mapper.ChargeAndRefundMapper;
import edu.neu.his.mapper.OutpatientChargesRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChargeAndRefundService {
    @Autowired
    private ChargeAndRefundMapper chargeAndRefundMapper;

    @Autowired
    private OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordId(int medical_record_id){
        return  chargeAndRefundMapper.findByMedicalRecordId(medical_record_id);
    }

    @Transactional
    public OutpatientChargesRecord findByMedicalRecordIdAndId(int medical_record_id, int id){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndId(medical_record_id, id);
    }

    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(int medical_record_id, String start_time, String end_time){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndTime(medical_record_id, start_time, end_time);
    }

    @Transactional
    public int update (OutpatientChargesRecord outpatientChargesRecord){
        return outpatientChargesRecordMapper.updateByPrimaryKey(outpatientChargesRecord);
    }
}
