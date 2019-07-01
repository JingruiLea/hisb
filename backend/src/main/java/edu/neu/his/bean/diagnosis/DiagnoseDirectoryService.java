package edu.neu.his.bean.diagnosis;

import edu.neu.his.bean.disease.Disease;
import edu.neu.his.bean.disease.DiseaseClassification;
import edu.neu.his.bean.disease.DiseaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中disease, disease_classification表的相关操作
 */
@Service
public class DiagnoseDirectoryService {
    @Autowired DiseaseMapper diseaseMapper;

    /**
     * 更新数据库中的一条相应的疾病记录
     * @param rawId 选中行的id
     * @param disease  要进行更新的Disease对象
     */
    @Transactional
    public void updateDisease(int rawId, Disease disease) {
        diseaseMapper.update(rawId, disease.getId(), disease.getCode(),disease.getName(),disease.getClassification_id(),disease.getPinyin(),disease.getCustom_name(),disease.getCustom_pinyin());
    }

    /**
     * 从数据库中根据名称找到对应的疾病
     * @param name 疾病名称
     * @return 返回根据名称找到的对应疾病
     */
    @Transactional
    public Disease findDiseaseByName(String name) {
        return diseaseMapper.findByName(name);
    }

    /**
     *向数据库中插入一条疾病记录
     * @param disease 要插入数据库中的Disease对象
     */
    @Transactional
    public void insertDisease(Disease disease) {
        diseaseMapper.insertDisease(disease);
    }

    /**
     * 根据疾病目录类别id在数据库中查询此类别中的所有的疾病
     * @param classification_id 疾病目录类别id
     * @return 返回查找到的疾病类别中的所有的疾病
     */
    @Transactional
    public List<Disease> findAll(int classification_id) {
        return diseaseMapper.findAll(classification_id);
    }

    /**
     *根据id从数据库中删除对应疾病
     * @param id 要删除的疾病的id
     */
    @Transactional
    public void deleteDisease(int id) {
        diseaseMapper.deleteDisease(id);
    }

    /**
     *向数据库中插入一条疾病分类
     * @param diseaseClassification 要插入数据库中的DiseaseClassification对象
     */
    @Transactional
    public void insertDiseaseClassification(DiseaseClassification diseaseClassification) {
        diseaseMapper.insertDiseaseClassification(diseaseClassification);
    }

    /**
     *从数据库中的根据code查询疾病数量
     * @param code 疾病编码
     * @return 是否存在该疾病
     */
    @Transactional
    public boolean checkCodeExist(String code) {
        return diseaseMapper.checkCodeExist(code)==1;
    }

    /**
     *从数据库中的根据id查询疾病数量
     * @param id 疾病id
     * @return 是否存在该疾病
     */
    @Transactional
    public boolean checkIdExist(int id) {
        return diseaseMapper.checkIdExist(id)==1;
    }

    /**
     *从数据库中的根据类别id查询疾病数量
     * @param classification_id 类别id
     * @return 是否存在该疾病
     */
    @Transactional
    public boolean checkClassificationExist(int classification_id) {
        return diseaseMapper.checkClassificationExist(classification_id)==1;
    }

    /**
     * 查找数据库中所有疾病分类的列表
     * @return 返回查找到的疾病分类的列表
     */
    @Transactional
    public List<DiseaseClassification> findAllDiseaseClassification() {
        return diseaseMapper.findAllDiseaseClassification();
    }
}
