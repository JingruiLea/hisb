package edu.neu.his.service;

import edu.neu.his.bean.Drug;
import edu.neu.his.bean.OutpatientChargesRecord;
import edu.neu.his.bean.Prescription;
import edu.neu.his.bean.PrescriptionItem;
import edu.neu.his.config.PrescriptionStatus;
import edu.neu.his.mapper.BillRecordMapper;
import edu.neu.his.mapper.ChargeAndRefundMapper;
import edu.neu.his.mapper.PrescriptionItemMapper;
import edu.neu.his.mapper.PrescriptionMapper;
import edu.neu.his.mapper.auto.AutoPrescriptionItemMapper;
import edu.neu.his.mapper.auto.AutoDrugMapper;
import edu.neu.his.mapper.auto.OutpatientChargesRecordMapper;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PrescriptionService {
    @Autowired
    private PrescriptionMapper prescriptionMapper;

    @Autowired
    private PrescriptionItemMapper prescriptionItemMapper;

    @Autowired
    private AutoPrescriptionItemMapper autoPrescriptionItemMapper;

    @Autowired
    private OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    private AutoDrugMapper autoDrugMapper;

    @Transactional
    public List<PrescriptionItem> findByPrescriptionAndStatus(int prescription_id, String prescriptionStatus, String recordStatus){
        return prescriptionItemMapper.selectByPrescriptionAndStatus(prescription_id, prescriptionStatus, recordStatus);
    }

    @Transactional
    public List<Prescription> findByMedicalRecordId(int medical_record_id){
        return prescriptionMapper.selectByMedicalRecordId(medical_record_id);
    }

    @Transactional
    public List<Map> getList(int medical_record_id,  String prescriptionStatus, String recordStatus){
        List<Map> result = new ArrayList<>();
        List<Map> prescriptionItemResult = new ArrayList<>();
        List<Prescription> prescriptionList = findByMedicalRecordId(medical_record_id);
        prescriptionList.forEach(prescription -> {
            int prescription_id = prescription.getId();
            List<PrescriptionItem> list = findByPrescriptionAndStatus(prescription_id, prescriptionStatus, recordStatus);
            list.forEach(prescriptionItem -> {
                Map prescriptionItemMap = Utils.objectToMap(prescriptionItem);
                int drug_id = prescriptionItem.getDrug_id();
                Drug drug = autoDrugMapper.selectByPrimaryKey(drug_id);
                prescriptionItemMap.put("drug_item",drug);
                prescriptionItemResult.add(prescriptionItemMap);
            });
            Map prescriptionMap = Utils.objectToMap(prescription);
            prescriptionMap.put("drug_item",prescriptionItemResult);
            result.add(prescriptionMap);
        });

        return result;
    }

    @Transactional
    public PrescriptionItem findPrescriptionItemById(int id){
        return autoPrescriptionItemMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public boolean allExist(List ids){
        List<Integer> notHave = new ArrayList<>();

        ids.forEach(id->{
            PrescriptionItem prescriptionItem = findPrescriptionItemById((int)id);
            if(prescriptionItem==null)
                notHave.add((int)id);
        });
        return notHave.size()==0;
    }

    @Transactional
    public int updatePrescriptionItem(PrescriptionItem prescriptionItem){
        return autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);
    }

    @Transactional
    public int returnDrug(PrescriptionItem prescriptionItem){
        //修改处方详情
        prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemReturned);
        return autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);
    }
}
