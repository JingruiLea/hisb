package edu.neu.his.service;

import edu.neu.his.bean.MedicalRecord;
import edu.neu.his.bean.Prescription;
import edu.neu.his.bean.PrescriptionItem;
import edu.neu.his.mapper.auto.PrescriptionItemMapper;
import edu.neu.his.mapper.auto.PrescriptionMapper;
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
    PrescriptionMapper prescriptionMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PrescriptionItemMapper itemMapper;

    @Transactional
    public void create(int user_id, int type, int medical_record_id, List<Map> drugIds){
        Prescription prescription = new Prescription();
        prescription.setCreate_time(Utils.getSystemTime());
        prescription.setMedical_record_id(medical_record_id);
        prescription.setStatus("暂存");
        prescription.setType(type);
        prescription.setUser_id(user_id);
        int id = prescriptionMapper.insert(prescription);
        addItems(id, drugIds);
    }

    @Transactional
    public boolean recordMedicalHasSubmit(int id){
        return medicalRecordService.medicalRecordHasSubmit(id);
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
        Prescription prescription = prescriptionMapper.selectByPrimaryKey(prescriptionId);
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

    //@Transactional


}
