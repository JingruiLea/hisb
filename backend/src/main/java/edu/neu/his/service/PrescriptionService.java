package edu.neu.his.service;

import edu.neu.his.bean.Prescription;
import edu.neu.his.bean.PrescriptionItem;
import edu.neu.his.config.Response;
import edu.neu.his.mapper.PrescriptionItemMapper;
import edu.neu.his.mapper.PrescriptionMapper;
import edu.neu.his.bean.*;
import edu.neu.his.mapper.auto.*;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionItemMapper prescriptionItemMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    edu.neu.his.mapper.auto.PrescriptionMapper autoPrescriptionMapper;


    @Autowired
    edu.neu.his.mapper.auto.PrescriptionItemMapper itemMapper;

    @Autowired
    DrugMapper drugMapper;

    @Autowired
    DrugService drugService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;


    @Transactional
    public int create(int user_id, int type, int medical_record_id, List<Map> drugIds){
        Prescription prescription = new Prescription();
        prescription.setCreate_time(Utils.getSystemTime());
        prescription.setMedical_record_id(medical_record_id);
        prescription.setStatus("暂存");
        prescription.setType(type);
        prescription.setUser_id(user_id);
        int id = autoPrescriptionMapper.insert(prescription);
        addItems(id, drugIds);
        return id;
    }

    @Transactional
    public boolean recordMedicalHasSubmit(int medicalRecordId){
        return medicalRecordService.medicalRecordHasSubmit(medicalRecordId);
    }

    @Transactional
    public void addItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem prescriptionItem = new PrescriptionItem();
            prescriptionItem.setAmount((Integer) i.get("amount"));
            if (i.get("note") == null) prescriptionItem.setNote("");
            else prescriptionItem.setNote((String) i.get("note"));
            prescriptionItem.setDrug_id((Integer)i.get("id"));
            prescriptionItem.setPrescription_id(prescriptionId);
            prescriptionItem.setStatus(Common.WEIQUYAO);
            itemMapper.insert(prescriptionItem);
        }
    }

    @Transactional
    public void updateItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem prescriptionItem = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("id"));
            prescriptionItem.setAmount((Integer) i.get("amount"));
            prescriptionItem.setNote((String)i.get("note"));
            itemMapper.updateByPrimaryKey(prescriptionItem);
        }
    }

    @Transactional
    public void removeItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem item = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("id"));
            itemMapper.deleteByPrimaryKey(item.getId());
        }
    }

    @Transactional
    public List<Map> detail(int prescriptionId){
        Prescription prescription = autoPrescriptionMapper.selectByPrimaryKey(prescriptionId);
        List<PrescriptionItem> items = itemMapper.selectByPrescriptionId(prescriptionId);
        List<Map> res = new ArrayList<>();
        for(PrescriptionItem item:items){
            HashMap map = new HashMap();
            map.put("id", item.getId());
            map.put("amount", item.getAmount());
            map.put("note", item.getNote());
            res.add(map);
        }
        return res;
    }

    @Transactional
    public void submit(User user, int prescriptionId){
        Prescription prescription = autoPrescriptionMapper.selectByPrimaryKey(prescriptionId);
        prescription.setStatus(Common.YITIJIAO);
        autoPrescriptionMapper.updateByPrimaryKey(prescription);
        List<PrescriptionItem> drugList = itemMapper.selectByPrescriptionId(prescriptionId);
        drugList.forEach(item->{
            Drug drug = drugMapper.selectByPrimaryKey(item.getId());
            OutpatientChargesRecord record = new OutpatientChargesRecord();
            record.setCreate_time(Utils.getSystemTime());
            record.setMedical_record_id(prescription.getMedical_record_id());
            record.setBill_record_id(0);
            record.setItem_id(item.getId());
            record.setType(Common.RECORD_TYPE_JIANCHA);
            record.setExpense_classification_id(drugService.getExpenseClassificationId(drug));
            record.setStatus(Common.WEIJIAOFEI);
            record.setQuantity(item.getAmount());
            record.setCost(item.getAmount() * drug.getPrice());
            record.setCollect_time("");
            record.setExecute_department_id(user.getDepartment_id());
            record.setCreate_time(Utils.getSystemTime());
            record.setCollect_time("");
            record.setReturn_time("");
            record.setCreate_user_id(user.getUid());
            record.setCollect_user_id(0);
            record.setReturn_user_id(0);
            outpatientChargesRecordMapper.insert(record);
        });
    }


    @Transactional
    public List<PrescriptionItem> findByPrescriptionAndStatus(int prescription_id, String prescriptionStatus, String recordStatus){
        return prescriptionItemMapper.selectByPrescriptionAndStatus(prescription_id, prescriptionStatus, recordStatus);
    }

    @Transactional
    public List<Prescription> findByMedicalRecordId(int medical_record_id){
        return prescriptionMapper.selectByMedicalRecordId(medical_record_id);
    }

    @Transactional
    public Prescription findById(int prescriptionId){
        return autoPrescriptionMapper.selectByPrimaryKey(prescriptionId);
    }


}
