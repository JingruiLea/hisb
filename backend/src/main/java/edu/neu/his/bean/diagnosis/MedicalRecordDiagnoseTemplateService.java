package edu.neu.his.bean.diagnosis;

import edu.neu.his.auto.AutoMedicalRecordDiagnoseTemplateItemMapper;
import edu.neu.his.auto.AutoMedicalRecordDiagnoseTemplateMapper;
import edu.neu.his.bean.medicalRecord.MedicalRecordStatus;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现处理数据库中medical_record_diagnose_template、medical_record_diagnose_template_item表的相关操作
 */
@Service
public class MedicalRecordDiagnoseTemplateService {
    @Autowired
    private AutoMedicalRecordDiagnoseTemplateMapper autoMedicalRecordDiagnoseTemplateMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseTemplateItemMapper autoMedicalRecordDiagnoseTemplateItemMapper;

    @Autowired
    private MedicalRecordDiagnoseTemplateMapper medicalRecordDiagnoseTemplateMapper;

    @Autowired
    private MedicalRecordDiagnoseTemplateItemMapper medicalRecordDiagnoseTemplateItemMapper;

    /**
     * 向数据库中插入一条诊断模版
     * @param medicalRecordTemplate  要插入数据库中的MedicalRecordDiagnoseTemplate对象
     * @return 返回诊断模版id
     */
    @Transactional
    public int insertDiagnoseTemplate(MedicalRecordDiagnoseTemplate medicalRecordTemplate){
        autoMedicalRecordDiagnoseTemplateMapper.insert(medicalRecordTemplate);
        return medicalRecordTemplate.getId();
    }

    /**
     * 从数据库中根据用户id找到对应的诊断模版
     * @param uid 用户id
     * @return 返回根据用户id和类型找到的对应诊断模版
     */
    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByUser(int uid){
        List<MedicalRecordDiagnoseTemplate> listByUserId = medicalRecordDiagnoseTemplateMapper.selectByUserId(uid, MedicalRecordStatus.Personal);
        return listByUserId;
    }

    /**
     * 从数据库中根据科室id找到对应的诊断模版列表
     * @param department_id 科室id
     * @return 返回根据科室id和类型找到的对应诊断模版列表
     */
    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByDepartment(int department_id){
        List<MedicalRecordDiagnoseTemplate> list = medicalRecordDiagnoseTemplateMapper.selectByDepartmentId(department_id,MedicalRecordStatus.Department);
        return list;
    }

    /**
     * 从数据库中根据类别找到对应的诊断模版列表
     * @param type 类别
     * @return 返回根据类别找到的对应诊断模版列表
     */
    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByType(int type){
        return medicalRecordDiagnoseTemplateMapper.selectByType(type);
    }

    /**
     * 从数据库中根据诊断模版列表找到对应的诊断子目
     * @param list 诊断模版列表
     * @return 返回填入诊断模版子目的对应api格式的诊断模版
     */
    @Transactional
    public List<Map> returnMapList(List<MedicalRecordDiagnoseTemplate> list){
        List<Map> resultList = new ArrayList<>();
        list.forEach(medicalRecordDiagnoseTemplate -> {
            Map diagnose = Utils.objectToMap(medicalRecordDiagnoseTemplate);
            List<MedicalRecordDiagnoseTemplateItem> westernItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(medicalRecordDiagnoseTemplate.getId(),DiagnoseItemType.Western);
            diagnose.put("western_diagnose", westernItems);
            List<MedicalRecordDiagnoseTemplateItem> chineseItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(medicalRecordDiagnoseTemplate.getId(),DiagnoseItemType.Chinese);
            diagnose.put("chinese_diagnose", chineseItems);
            resultList.add(diagnose);
        });
        return resultList;
    }

    /**
     *  从数据库中根据名称找到对应的诊断模版列表
     * @param title 名称
     * @return 返回根据名称找到的对应诊断模版列表
     */
    @Transactional
    public List<MedicalRecordDiagnoseTemplate> selectByTitle(String title){
        return medicalRecordDiagnoseTemplateMapper.selectByTitle(title);
    }

    /**
     * 向数据库中插入一条诊断模版子目
     * @param medicalRecordTemplateItem  要插入数据库中的MedicalRecordDiagnoseTemplateItem对象
     * @return 返回诊断模版子目id
     */
    @Transactional
    public int insertDiagnoseTemplateItem(MedicalRecordDiagnoseTemplateItem medicalRecordTemplateItem){
        autoMedicalRecordDiagnoseTemplateItemMapper.insert(medicalRecordTemplateItem);
        return medicalRecordTemplateItem.getId();
    }

    /**
     * 更新数据库中的一条相应的诊断子目模版
     * @param medicalRecordDiagnoseTemplate  要进行更新的MedicalRecordDiagnoseTemplateItem对象
     * @return 返回诊断模版子目id
     */
    @Transactional
    public int updateDiagnoseTemplate(MedicalRecordDiagnoseTemplate medicalRecordDiagnoseTemplate){
        return autoMedicalRecordDiagnoseTemplateMapper.updateByPrimaryKey(medicalRecordDiagnoseTemplate);
    }

    /**
     * 根据诊断模版id从数据库中删除对应诊断模版子目
     * @param diagnose_template_id 诊断模版ID
     */
    @Transactional
    public void deleteAllItem(int diagnose_template_id){
        List<MedicalRecordDiagnoseTemplateItem> diagnoseTemplateItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateId(diagnose_template_id);
        diagnoseTemplateItems.forEach(diagnoseTemplateItem->{
            autoMedicalRecordDiagnoseTemplateItemMapper.deleteByPrimaryKey(diagnoseTemplateItem.getId());
        });
    }

    /**
     * 从数据库中根据id找到对应的诊断模版
     * @param id 诊断模版id
     * @return 返回诊断模版
     */
    @Transactional
    public MedicalRecordDiagnoseTemplate selectTemplateById(int id){
        return autoMedicalRecordDiagnoseTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据诊断模版id从数据库中删除对应诊断模版
     * @param id 诊断模版id
     * @return 返回删除的诊断模版id
     */
    @Transactional
    public int deleteTemplateById(int id){
        return autoMedicalRecordDiagnoseTemplateMapper.deleteByPrimaryKey(id);
    }

    /**
     *  从数据库中根据诊断模版id找到对应的诊断模版子目
     * @param id 诊断模版id
     * @return 返回对应api格式的诊断模版
     */
    @Transactional
    public Map returnDiagnoseTemplateMap(int id){
        Map diagnose = new HashMap();
        List<MedicalRecordDiagnoseTemplateItem> westernItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(id,DiagnoseItemType.Western);
        diagnose.put("western_diagnose", westernItems);
        List<MedicalRecordDiagnoseTemplateItem> chineseItems = medicalRecordDiagnoseTemplateItemMapper.selectByDiagnoseTemplateIdAndType(id,DiagnoseItemType.Chinese);
        diagnose.put("chinese_diagnose", chineseItems);

        return diagnose;
    }
}
