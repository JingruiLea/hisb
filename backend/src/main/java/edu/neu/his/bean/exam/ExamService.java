package edu.neu.his.bean.exam;

import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.nondrug.NonDrugChargeService;
import edu.neu.his.bean.user.User;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 实现处理数据库中exam、examItem表的相关操作
 */
@Service
public class ExamService {
    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamItemMapper examItemMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    NonDrugChargeService nonDrugChargeService;

    @Transactional
    public int insertItem(ExamItem item){
        return examItemMapper.insert(item);
    }

    @Transactional
    public boolean medicalRecordHasSubmit(Exam exam){
        String medicalRecordStatus = medicalRecordService.getStatusById(exam.getMedical_record_id());
        if(!Common.YITIJIAO.equals(medicalRecordStatus)){
            return false;
        }
        return true;
    }

    /**
     * 向数据库中插入一条检查/检验/处置记录
     * @param exam  要插入数据库中的Exam对象
     * @return 检查/检验/处置id
     */
    @Transactional
    public int insert(Exam exam){
        return examMapper.insert(exam);
    }

    /**
     * 从数据库中根据检查/检验/处置id找到对应的非药品项目id
     * @param id 检查/检验/处置id
     * @return 对应的非药品项目id列表
     */
    @Transactional
    public List<Integer> getNonDrugItemIdListById(Integer id){
        List<ExamItem> itemList = examItemMapper.selectByExamId(id);
        return itemList.stream().map(ExamItem::getNon_drug_item_id).collect(Collectors.toList());
    }

    /**
     * 从数据库中根据检查/检验/处置id找到对应的检查/检验/处置
     * @param id 检查/检验/处置id
     * @return 对应的检查/检验/处置
     */
    @Transactional
    public Exam selectById(Integer id){
        return examMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新数据库中的一条相应的检查/检验/处置记录
     * @param record 要进行更新的Exam对象
     * @return 检查/检验/处置id
     */
    @Transactional
    public int updateByPrimaryKey(Exam record){
        return examMapper.updateByPrimaryKey(record);
    }

    public List<Exam> selectByMedicalRecordIdAndType(int medicalRecordId, int type){
        List<Exam> list = examMapper.selectAll();
        List<Exam> res = new ArrayList<>();
        for(Exam exam:list){
            if(exam.getMedical_record_id() == medicalRecordId && exam.getType()==type){
                res.add(exam);
            }
        }
        return res;
    }

    /**
     * 从数据库中根据病历号和医生找到对应的检查/检验/处置列表
     * @param medicalRecordId 病历号
     * @param user 医生
     * @return 对应的检查/检验/处置列表
     */
    @Transactional
    public List list(int medicalRecordId, User user){
        List res = new ArrayList();
        List<Exam> examList = examMapper.selectByMedicalRecordId(medicalRecordId);
        for (Exam exam : examList) {
            Map examMap = Utils.objectToMap(exam);
            List<ExamItem> examItemList = examItemMapper.selectByExamId(exam.getId());
            List itemList = new ArrayList();
            for (ExamItem examItem : examItemList) {
                Map examItemMap = Utils.objectToMap(examItem);
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(examItem.getNon_drug_item_id());
                examItemMap.put("non_drug_item", Utils.objectToMap(nonDrugChargeItem));
                if(nonDrugChargeItem.getDepartment_id() == user.getDepartment_id()){
                    itemList.add(examItemMap);
                }
            }
            examMap.put("items", itemList);
            res.add(examMap);
        }
        return res;
    }

    /**
     * 从数据库中根据类别找到对应的非药品项目列表
     * @param type 检查/检验/处置类别
     * @return 对应的非药品项目列表
     */
    @Transactional
    public List<NonDrugChargeItem> allItemsByType(int type){
        return nonDrugChargeService.findAll().stream().filter(item->{
            if(type == 0 && item.getExpense_classification_id() == 3){
                return true;
            }else if(type == 1 && item.getExpense_classification_id() == 7){
                return true;
            }else if(type == 2 && item.getExpense_classification_id() == 16){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     * 根据id从数据库中删除对应检查/检验/处置
     * @param id 检查/检验/处置id
     * @return 删除的检查/检验/处置id
     */
    @Transactional
    public int delete(int id){
        return examMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据检查/检验/处置id从数据库中删除对应检查/检验/处置子目
     * @param id 检查/检验/处置id
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteAllItemById(int id) {
        List<ExamItem> list = examItemMapper.selectByExamId(id);
        for (ExamItem o : list) {
            if(examItemMapper.deleteByPrimaryKey(o.getId()) != 1){
                return false;
            }
        }
        return true;
    }

    public List listByType(int type, int medicalRecordId, User systemUser) {
        List<Exam> examList = selectByMedicalRecordIdAndType(medicalRecordId, type);
        List res = new ArrayList();
        for (Exam exam : examList) {
            Map examMap = Utils.objectToMap(exam);
            if(exam.getType()!=type) continue;
            List<ExamItem> examItemList = examItemMapper.selectByExamId(exam.getId());
            List itemList = new ArrayList();
            for (ExamItem examItem : examItemList) {
                Map examItemMap = Utils.objectToMap(examItem);
                NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(examItem.getNon_drug_item_id());
                examItemMap.put("non_drug_item", Utils.objectToMap(nonDrugChargeItem));
                itemList.add(examItemMap);
            }
            examMap.put("exam_item", itemList);
            res.add(examMap);
        }
        return res;
    }
}
