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

/**
 * 实现处理数据库中prescription、prescription_item表的相关操作
 */
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

    /**
     * 向数据库中插入一条处方记录
     * @param user_id 用户id
     * @param type 类型
     * @param medical_record_id 病历号
     * @param drugIds 药品id列表
     * @return 添加的处方id
     */
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

    /**
     *  判断病历是否已提交
     * @param medicalRecordId 病历id
     * @return 是否已提交
     */
    @Transactional
    public boolean recordMedicalHasSubmit(int medicalRecordId){
        return medicalRecordService.medicalRecordHasSubmit(medicalRecordId);
    }

    /**
     * 向数据库中插入处方详情
     * @param prescriptionId 处方id
     * @param drugInfos 药品信息列表
     */
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

    /**
     * 根据处方id和药品信息列表从数据库中删除对应处方详情
     * @param prescriptionId 处方id
     * @param drugInfos 药品信息列表
     * @return 是否删除成功
     */
    @Transactional
    public boolean removeItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionItem item = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("drug_id"));
            if(item == null) return false;
            itemMapper.deleteByPrimaryKey(item.getId());
        }
        return true;
    }

    /**
     * 从数据库中根据处方id找到对应的处方详情列表
     * @param prescriptionId 处方id
     * @return 根据处方id找到的对应处方详情列表
     */
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

    /**
     * 提交处方
     * @param user 用户
     * @param prescriptionId 处方id
     */
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

    /**
     * 从数据库中根据处方ID和状态找到对应的处方详情列表
     * @param prescription_id 处方ID
     * @param prescriptionStatus 处方状态
     * @param recordStatus 病历状态
     * @return 根据处方ID和状态找到的对应处方详情列表
     */
    @Transactional
    public List<PrescriptionItem> findByPrescriptionAndStatus(int prescription_id, String prescriptionStatus, String recordStatus){
        return prescriptionItemMapper.selectByPrescriptionAndStatus(prescription_id, prescriptionStatus, recordStatus);
    }

    /**
     * 从数据库中根据病历号找到对应的处方列表
     * @param medical_record_id 病历号
     * @return 根据病历号找到的对应处方列表
     */
    @Transactional
    public List<Prescription> findByMedicalRecordId(int medical_record_id){
        return prescriptionMapper.selectByMedicalRecordId(medical_record_id);
    }

    /**
     * 从数据库中根据处方id找到对应的处方
     * @param prescriptionId 处方id
     * @return 根据处方id找到的对应处方
     */
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

    /**
     * 从数据库中根据处方详情id找到对应的处方详情
     * @param id 处方详情id
     * @return 根据处方详情id找到的对应处方详情
     */
    @Transactional
    public PrescriptionItem findPrescriptionItemById(int id){
        return autoPrescriptionItemMapper.selectByPrimaryKey(id);
    }

    /**
     * 获得能够退药的处方
     * @param maps 处方详情id列表
     * @return 能够退药的处方
     */
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

    /**
     * 获得能够发药的处方
     * @param ids 处方详情id列表
     * @return 能够发药的处方
     */
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

    /**
     * 更新数据库中的一条相应的处方详情
     * @param prescriptionItem 要进行更新的PrescriptionItem对象
     * @return 更新的PrescriptionItem对象id
     */
    @Transactional
    public int updatePrescriptionItem(PrescriptionItem prescriptionItem){
        return autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);
    }

    /**
     * 退药
     * @param prescriptionItem 处方详情
     * @param amount 总金额
     * @param cost 价格
     */
    @Transactional
    public void returnDrug(PrescriptionItem prescriptionItem, int amount, float cost, Map req){
        //修改处方详情
        int new_amount = prescriptionItem.getAmount()-amount;
        prescriptionItem.setAmount(amount);
        prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemReturned);
        autoPrescriptionItemMapper.updateByPrimaryKey(prescriptionItem);
        int item_id = prescriptionItem.getId();

        PrescriptionItem new_prescriptionItem = prescriptionItem;
        new_prescriptionItem.setId(null);
        new_prescriptionItem.setAmount(new_amount);
        new_prescriptionItem.setStatus(PrescriptionStatus.PrescriptionItemTaken);
        int new_item_id = insert(new_prescriptionItem);
        modifyChargeRecord(item_id,cost,new_item_id);
    }

    /**
     * 查找数据库中所有科室的列表
     * @return 数据库中所有科室的列表
     */
    @Transactional
    public List<Prescription> selectAll() {
        return autoPrescriptionMapper.selectAll();
    }

    /**
     * 根据处方id从数据库中删除对应处方详情
     * @param prescriptionId 处方id
     * @return 是否删除成功
     */
    @Transactional
    public boolean removeAllItems(int prescriptionId) {
        if(autoPrescriptionMapper.selectByPrimaryKey(prescriptionId)==null){return false;}
        prescriptionMapper.removeAllItems(prescriptionId);
        return true;
    }

    private void modifyChargeRecord(int item_id, float cost, int new_item_id){
        OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundMapper.findByItemId(item_id);
        float new_cost = outpatientChargesRecord.getCost()-cost;
        int id = outpatientChargesRecord.getId();

        OutpatientChargesRecord newOutpatientChargesRecord = outpatientChargesRecord;
        newOutpatientChargesRecord.setCost(new_cost);
        newOutpatientChargesRecord.setItem_id(new_item_id);
        outpatientChargesRecordMapper.insert(newOutpatientChargesRecord);

        outpatientChargesRecord.setId(id);
        outpatientChargesRecord.setCost(cost);
        outpatientChargesRecord.setItem_id(item_id);
        outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Charged);
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

    /**
     * 向数据库中插入一条处方详情记录
     * @param prescriptionItem 要插入数据库中的PrescriptionItem对象
     * @return 插入数据库中的PrescriptionItem对象id
     */
    @Transactional
    public int insert(PrescriptionItem prescriptionItem){
        autoPrescriptionItemMapper.insert(prescriptionItem);
        return prescriptionItem.getId();
    }
}
