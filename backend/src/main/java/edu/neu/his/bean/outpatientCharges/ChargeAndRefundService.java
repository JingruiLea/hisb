package edu.neu.his.bean.outpatientCharges;

import edu.neu.his.bean.exam.ExamItem;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.exam.ExamStatus;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.bean.exam.ExamItemMapper;
import edu.neu.his.auto.AutoPrescriptionItemMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChargeAndRefundService {
    @Autowired
    private ChargeAndRefundMapper chargeAndRefundMapper;

    @Autowired
    private OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    private AutoPrescriptionItemMapper autoPrescriptionItemMapper;

    @Autowired
    private ExamItemMapper examItemMapper;

    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordIdAndStatus(int medical_record_id,String status){
        List<OutpatientChargesRecord> list = chargeAndRefundMapper.findByMedicalRecordId(medical_record_id);
        List<OutpatientChargesRecord> records = new ArrayList<>();
        list.forEach(outpatientChargesRecord -> {
            if(outpatientChargesRecord.getStatus().equals(status))
                records.add(outpatientChargesRecord);
        });
        return records;
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

    @Transactional
    public int insert(OutpatientChargesRecord record){
        outpatientChargesRecordMapper.insert(record);
        return record.getId();
    }

    @Transactional
    public boolean itemHasReturn(OutpatientChargesRecord record){
        int item_id = record.getItem_id();
        if((int)record.getType()==OutpatientChargesRecordStatus.Prescription){
            PrescriptionItem prescriptionItem = autoPrescriptionItemMapper.selectByPrimaryKey(item_id);
            if(prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemReturned))
                return true;
            else return false;
        }
        else {
            ExamItem examItem = examItemMapper.selectByPrimaryKey(item_id);
            if(examItem.getStatus().equals(ExamStatus.Cancelled))
                return true;
            else return false;
        }
    }

    public List<OutpatientChargesRecord> findAll() {
        return outpatientChargesRecordMapper.selectAll();
    }
}
