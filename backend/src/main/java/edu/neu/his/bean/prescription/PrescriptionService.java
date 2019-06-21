package edu.neu.his.bean.prescription;

import edu.neu.his.auto.AutoPrescriptionMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.outpatientCharges.ChargeAndRefundMapper;
import edu.neu.his.bean.outpatientCharges.ChargeAndRefundService;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecordStatus;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.config.Auth;
import edu.neu.his.util.Common;
import edu.neu.his.auto.AutoPrescriptionItemMapper;
import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
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
    private MedicalRecordService medicalRecordService;

    @Autowired
    private AutoPrescriptionMapper autoPrescriptionMapper;

    @Autowired
    private AutoPrescriptionItemMapper itemMapper;

    @Autowired
    private AutoDrugMapper drugMapper;

    @Autowired
    private DrugService drugService;

    @Autowired
    private OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    private ChargeAndRefundMapper chargeAndRefundMapper;


    @Transactional
    public int create(int user_id, int type, int medical_record_id, List<Map> drugIds){
        Prescription prescription = new Prescription();
        prescription.setCreate_time(Utils.getSystemTime());
        prescription.setMedical_record_id(medical_record_id);
        prescription.setStatus(Common.ZANCUN);
        prescription.setType(type);
        prescription.setUser_id(user_id);
        autoPrescriptionMapper.insert(prescription);
        addItems(prescription.getId(), drugIds);
        return prescription.getId();
    }

    @Transactional
    public boolean recordMedicalHasSubmit(int medicalRecordId){
        return medicalRecordService.medicalRecordHasSubmit(medicalRecordId);
    }

    @Transactional
    public void addItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem prescriptionItem = Utils.fromMap(i, PrescriptionItem.class);
            prescriptionItem.setDrug_id((Integer)i.get("drug_id"));
            prescriptionItem.setPrescription_id(prescriptionId);
            prescriptionItem.setStatus(Common.WEIQUYAO);
            itemMapper.insert(prescriptionItem);
        }
    }

    @Transactional
    public void updateItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem prescriptionItem = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("drug_id"));
            PrescriptionItem prescriptionItem1 = Utils.fromMap(i, PrescriptionItem.class);
            prescriptionItem1.setId(prescriptionItem.getId());
            itemMapper.updateByPrimaryKey(prescriptionItem1);
        }
    }

    @Transactional
    public boolean removeItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem item = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("drug_id"));
            if(item == null) return false;
            itemMapper.deleteByPrimaryKey(item.getId());
        }
        return true;
    }

    @Transactional
    public List<Map> detail(int prescriptionId){
        Prescription prescription = autoPrescriptionMapper.selectByPrimaryKey(prescriptionId);
        List<PrescriptionItem> items = itemMapper.selectByPrescriptionId(prescriptionId);
        List<Map> res = new ArrayList<>();
        for(PrescriptionItem item:items){
            Utils.objectToMap(item);
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
            Drug drug = drugMapper.selectByPrimaryKey(item.getDrug_id());
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

    @Autowired
    private AutoPrescriptionItemMapper autoPrescriptionItemMapper;

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
    public Prescription findById(int prescriptionId){
        return autoPrescriptionMapper.selectByPrimaryKey(prescriptionId);
    }


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
            prescriptionMap.put("prescription_item_list",prescriptionItemResult);
            result.add(prescriptionMap);
        });

        return result;
    }

    @Transactional
    public PrescriptionItem findPrescriptionItemById(int id){
        return autoPrescriptionItemMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public boolean allCanReturn(List<Map> maps){
        for (Map map : maps) {
            int id = (int)map.get("id");
            PrescriptionItem prescriptionItem = findPrescriptionItemById(id);
            if(prescriptionItem==null || prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemReturned))
                return false;
        }
        return true;
    }

    @Transactional
    public boolean allCanTake(List ids){
        List<Integer> cannotTake = new ArrayList<>();

        ids.forEach(id->{
            PrescriptionItem prescriptionItem = findPrescriptionItemById((int)id);
            if(prescriptionItem==null || prescriptionItem.getStatus().equals(PrescriptionStatus.PrescriptionItemReturned))
                cannotTake.add((int)id);
        });
        return cannotTake.size()==0;
    }

    @Transactional
    public int updatePrescriptionItem(PrescriptionItem prescriptionItem){
        return autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);
    }

    @Transactional
    public int returnDrug(PrescriptionItem prescriptionItem, int amount){
        //修改处方详情
        int new_amount = prescriptionItem.getAmount()-amount;
        prescriptionItem.setAmount(amount);
        prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemReturned);
        autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);

        PrescriptionItem new_prescriptionItem = prescriptionItem;
        new_prescriptionItem.setAmount(new_amount);
        new_prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemTaken);
        return insert(prescriptionItem);
    }

    @Transactional
    public List<Prescription> selectAll() {
        return autoPrescriptionMapper.selectAll();
    }

    @Transactional
    public boolean removeAllItems(int prescriptionId) {
        if(autoPrescriptionMapper.selectByPrimaryKey(prescriptionId)==null){return false;}
        prescriptionMapper.removeAllItems(prescriptionId);
        return true;
    }

    @Transactional
    public void modifyChargeRecord(int item_id, float cost, Map req, int new_item_id){
        OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundMapper.findByItemId(item_id);
        float new_cost = outpatientChargesRecord.getCost()-cost;
        OutpatientChargesRecord newOutpatientChargesRecord = outpatientChargesRecord;
        newOutpatientChargesRecord.setCost(new_cost);
        newOutpatientChargesRecord.setItem_id(new_item_id);
        outpatientChargesRecordMapper.insert(newOutpatientChargesRecord);

        outpatientChargesRecord.setCost(cost);
        outpatientChargesRecord.setItem_id(item_id);
        outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Refunded);
        outpatientChargesRecord.setReturn_time(Utils.getSystemTime());
        outpatientChargesRecord.setReturn_user_id(Auth.uid(req));
        outpatientChargesRecordMapper.updateByPrimaryKey(outpatientChargesRecord);
    }

    public int delete(Integer id) {
       return autoPrescriptionMapper.deleteByPrimaryKey(id);
    }
    
    public List<PrescriptionItem> findPrescriptionItemByStatus(String prescriptionStatus){
        return prescriptionItemMapper.selectByStatus(prescriptionStatus);
    }

    public boolean cancel(Integer id) {
        Prescription prescription = autoPrescriptionMapper.selectByPrimaryKey(id);
        if(prescription == null){
            return false;
        }
        prescription.setStatus(Common.YIZUOFEI);
        autoPrescriptionMapper.updateByPrimaryKey(prescription);
        return true;
    }

    @Transactional
    public int insert(PrescriptionItem prescriptionItem){
        autoPrescriptionItemMapper.insert(prescriptionItem);
        return prescriptionItem.getId();
    }
}
