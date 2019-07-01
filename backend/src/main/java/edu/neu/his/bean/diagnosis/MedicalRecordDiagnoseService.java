package edu.neu.his.bean.diagnosis;

import edu.neu.his.auto.AutoMedicalRecordDiagnoseItemMapper;
import edu.neu.his.auto.AutoMedicalRecordDiagnoseMapper;
import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseMapper;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现处理数据库中medical_record_diagnose、medical_record_diagnose_item表的相关操作
 */
@Service
public class MedicalRecordDiagnoseService {
    @Autowired
    private MedicalRecordDiagnoseMapper medicalRecordDiagnoseMapper;

    @Autowired
    private MedicalRecordDiagnoseItemMapper medicalRecordDiagnoseItemMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseMapper autoMedicalRecordDiagnoseMapper;

    @Autowired
    private AutoMedicalRecordDiagnoseItemMapper autoMedicalRecordDiagnoseItemMapper;

    @Autowired
    DiseaseMapper diseaseMapper;

    /**
     *从数据库中根据病历号找到对应的诊断
     * @param medical_record_id 病历号
     * @return 返回根据病历号找到的对应诊断
     */
    @Transactional
    public MedicalRecordDiagnose findDiagnoseByMedicalRecordId(int medical_record_id){
        return medicalRecordDiagnoseMapper.selectByMedicalRecordId(medical_record_id);
    }

    /**
     *从数据库中根据诊断id找到对应的诊断子目
     * @param medical_record_diagnose_id 诊断id
     * @return 返回根据诊断id找到的对应诊断子目
     */
    @Transactional
    public List<MedicalRecordDiagnoseItem> findDiagnoseItemByDiagnoseId(int medical_record_diagnose_id){
        return medicalRecordDiagnoseItemMapper.selectByDiagnoseId(medical_record_diagnose_id);
    }

    /**
     * 从数据库中根据诊断id和类型找到对应的诊断子目
     * @param medical_record_diagnose_id 诊断id
     * @param diagnose_type 诊断类型
     * @return 返回根据诊断id和类型找到的对应诊断子目
     */
    @Transactional
    public List<MedicalRecordDiagnoseItem> findDiagnoseItemByDiagnoseIdAndType(int medical_record_diagnose_id,String diagnose_type){
        return medicalRecordDiagnoseItemMapper.selectByDiagnoseIdAndType(medical_record_diagnose_id,diagnose_type);
    }

    /**
     *向数据库中插入一条诊断
     * @param medicalRecordDiagnose 要插入数据库中的MedicalRecordDiagnose对象
     * @return 返回诊断id
     */
    @Transactional
    public int insertDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        autoMedicalRecordDiagnoseMapper.insert(medicalRecordDiagnose);
        return medicalRecordDiagnose.getId();
    }

    /**
     *从数据库中根据诊断id和类型找到对应的诊断子目
     * @param medicalRecordDiagnose 要从数据库中查找的MedicalRecordDiagnose对象
     * @return 返回数据库中找到的MedicalRecordDiagnose对象
     */
    @Transactional
    public Map getExistDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        Map diagnose = Utils.objectToMap(medicalRecordDiagnose);
        List<MedicalRecordDiagnoseItem> westernList = findDiagnoseItemByDiagnoseIdAndType(medicalRecordDiagnose.getId(), DiagnoseItemType.Western);
        List<MedicalRecordDiagnoseItem> chineseList = findDiagnoseItemByDiagnoseIdAndType(medicalRecordDiagnose.getId(), DiagnoseItemType.Chinese);
        diagnose.put("western_diagnose", westernList);
        diagnose.put("chinese_diagnose", chineseList);

        return diagnose;
    }

    /**
     *新建一个诊断
     * @param medicalRecordDiagnose 需要新建的MedicalRecordDiagnose对象
     * @return 返回填入诊断子目的MedicalRecordDiagnose对象
     */
    @Transactional
    public Map getEmptyDiagnose(MedicalRecordDiagnose medicalRecordDiagnose){
        Map diagnose = Utils.objectToMap(medicalRecordDiagnose);
        List<MedicalRecordDiagnoseItem> westernList = new ArrayList<>();
        List<MedicalRecordDiagnoseItem> chineseList = new ArrayList<>();
        diagnose.put("western_diagnose", westernList);
        diagnose.put("chinese_diagnose", chineseList);

        return diagnose;
    }

    /**
     *根据id从数据库中删除对应诊断
     * @param medical_record_diagnose_id 诊断id
     */
    @Transactional
    public void deleteAllByDiagnoseId(int medical_record_diagnose_id){
        List<MedicalRecordDiagnoseItem> medicalRecordDiagnoseItems = medicalRecordDiagnoseItemMapper.selectByDiagnoseId(medical_record_diagnose_id);
        medicalRecordDiagnoseItems.forEach(medicalRecordDiagnoseItem -> {
            int item_id = medicalRecordDiagnoseItem.getId();
            autoMedicalRecordDiagnoseItemMapper.deleteByPrimaryKey(item_id);
        });
    }

    /**
     * 向数据库中插入一条诊断子目
     * @param medicalRecordDiagnoseItem  要插入数据库中的MedicalRecordDiagnoseItem对象
     * @return 返回诊断子目id
     */
    @Transactional
    public int insertDiagnoseItem(MedicalRecordDiagnoseItem medicalRecordDiagnoseItem){
        autoMedicalRecordDiagnoseItemMapper.insert(medicalRecordDiagnoseItem);
        return medicalRecordDiagnoseItem.getId();
    }

    /**
     * 查找数据库中所有疾病的列表
     * @return 返回查找到的所有疾病的列表
     */
    @Transactional
    public List<Disease> selectAllDisease() {
        return diseaseMapper.findall();
    }
}
