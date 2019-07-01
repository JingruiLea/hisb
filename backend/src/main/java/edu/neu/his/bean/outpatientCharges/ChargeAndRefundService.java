package edu.neu.his.bean.outpatientCharges;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.auto.NonDrugChargeItemMapper;
import edu.neu.his.bean.department.DepartmentMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugMapper;
import edu.neu.his.bean.exam.ExamItem;
import edu.neu.his.bean.expenseClassification.ExpenseClassificationMapper;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.prescription.PrescriptionItem;
import edu.neu.his.bean.exam.ExamStatus;
import edu.neu.his.bean.prescription.PrescriptionStatus;
import edu.neu.his.bean.exam.ExamItemMapper;
import edu.neu.his.auto.AutoPrescriptionItemMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.config.Auth;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现处理数据库中outpatient_charges_record表的相关操作
 */
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

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    AutoDrugMapper drugMapper;

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Autowired
    ExpenseClassificationMapper expenseClassificationMapper;

    /**
     * 从数据库中根据病历号和状态找到对应的缴费记录列表
     * @param medical_record_id 病历号
     * @param status 状态
     * @return 根据病历号和状态找到的对应缴费记录列表
     */
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

    /**
     * 从数据库中根据病历号和id找到对应的缴费记录
     * @param medical_record_id 病历号
     * @param id id
     * @return 根据病历号和id找到的对应缴费记录
     */
    @Transactional
    public OutpatientChargesRecord findByMedicalRecordIdAndId(int medical_record_id, int id){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndId(medical_record_id, id);
    }

    /**
     * 从数据库中根据病历号和起止时间找到对应的缴费记录列表
     * @param medical_record_id 病历号
     * @param start_time 起始时间
     * @param end_time 截止时间
     * @return 根据病历号和起止时间找到的对应缴费记录列表
     */
    @Transactional
    public List<OutpatientChargesRecord> findByMedicalRecordIdAndTime(int medical_record_id, String start_time, String end_time){
        return  chargeAndRefundMapper.findByMedicalRecordIdAndTime(medical_record_id, start_time, end_time);
    }

    /**
     * 更新数据库中的一条相应的缴费记录
     * @param outpatientChargesRecord 要进行更新的OutpatientChargesRecord对象
     * @return 更新的OutpatientChargesRecord对象id
     */
    @Transactional
    public int update (OutpatientChargesRecord outpatientChargesRecord){
        return outpatientChargesRecordMapper.updateByPrimaryKey(outpatientChargesRecord);
    }

    /**
     * 将缴费记录转换为api需要的格式
     * @param record 缴费记录
     * @return 转换的缴费信息
     */
    @Transactional
    public Map outpatientChargesRecordToMap(OutpatientChargesRecord record){
        Map res = Utils.objectToMap(record);
        res.put("excute_department", departmentMapper.selectById(record.getExecute_department_id()).getName());
        res.put("expense_classification", expenseClassificationMapper.findClassificationById(record.getExpense_classification_id()));
        String itemName = "";
        if(record.getType() == 0){
            PrescriptionItem item = autoPrescriptionItemMapper.selectByPrimaryKey(record.getItem_id());
            res.putAll(Utils.objectToMap(item));
            res.put("fee", res.get("cost"));
            res.put("mount", res.get("amount"));
            Drug drug = drugMapper.selectByPrimaryKey(item.getDrug_id());
            itemName = drug.getName();
            res.putAll(Utils.objectToMap(drug));
        }else if(record.getType() == 1){
            ExamItem item = examItemMapper.selectByPrimaryKey(record.getItem_id());
            res.putAll(Utils.objectToMap(item));
            res.put("fee", res.get("cost"));
            res.put("amount", 1);
            NonDrugChargeItem drug = nonDrugChargeItemMapper.selectByPrimaryKey(item.getNon_drug_item_id());
            itemName = drug.getName();
            Map drugMap = Utils.objectToMap(drug);
            drugMap.put("price", drug.getFee());
            res.putAll(drugMap);
        }
        res.put("status", record.getStatus());
        res.put("item_name", itemName);
        return res;
    }

    /**
     * 向数据库中插入一条缴费记录
     * @param record 要插入数据库中的OutpatientChargesRecord对象
     * @return 插入数据库中的OutpatientChargesRecord对象id
     */
    @Transactional
    public int insert(OutpatientChargesRecord record){
        outpatientChargesRecordMapper.insert(record);
        return record.getId();
    }

    /**
     * 判断是否已退药
     * @param record 缴费记录
     * @return 是否已退药
     */
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
