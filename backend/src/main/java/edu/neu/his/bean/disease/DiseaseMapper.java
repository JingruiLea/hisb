
package edu.neu.his.bean.disease;

import edu.neu.his.util.Importable;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 该类对数据库中的disease，disease_classification表进行数据持久化操作
 */
@Mapper
@Component(value = "DiseaseMapper")
public interface DiseaseMapper extends Importable<Disease> {
    /**
     * 根据名称查找对应的疾病
     * @param name 疾病名称
     * @return 对应的疾病
     */
    @Select("SELECT disease.id,disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and disease.name = #{name}")
    Disease findByName(@Param("name") String name);

    /**
     * 根据疾病类别id查找对应的疾病
     * @param classification_id 疾病类别id
     * @return 对应的疾病
     */
    @Select("SELECT disease.id, disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and classification_id = #{classification_id}")
    List<Disease> findAll(@Param("classification_id") int classification_id);

    /**
     * 向数据库的disease表中插入一条记录
     * @param disease  要插入数据库中的Disease对象
     */
    @Insert("INSERT INTO disease(name, code, id, classification_id, pinyin, custom_name, custom_pinyin) " +
            "VALUES(#{name}, #{code}, #{id}, #{classification_id}, #{pinyin}, #{custom_name}, #{custom_pinyin})")
    void insertDisease(Disease disease);

    /**
     * 根据id更新数据库的disease表中相应的记录
     * @param rawId 选中行的id
     * @param id 疾病id
     * @param code 疾病编码
     * @param name 名称
     * @param classification_id 疾病类别id
     * @param pinyin 拼音码
     * @param custom_name 客户姓名
     * @param custom_pinyin 客户拼音码
     */
    @Update("UPDATE disease SET code = #{code}, name = #{name}, id = #{id}, classification_id=#{classification_id}, pinyin=#{pinyin}," +
            "custom_name=#{custom_name}, custom_pinyin = #{custom_pinyin} WHERE id = #{raw_id}")
    void update(@Param("raw_id") int rawId,@Param("id") int id,@Param("code") String code,@Param("name") String name,@Param("classification_id") int classification_id,
                @Param("pinyin") String pinyin,@Param("custom_name") String custom_name,@Param("custom_pinyin") String custom_pinyin);

    /**
     * 根据id从数据库中删除对应的疾病
     * @param id 疾病id
     */
    @Delete("DELETE FROM disease WHERE id=#{id}")
    void deleteDisease(int id);

    /**
     * 根据id从数据库中删除对应的疾病类别
     * @param diseaseClassification 疾病类别
     */
    @Insert("INSERT INTO disease_classification(id, name) VALUES(#{id}, #{name})")
    void insertDiseaseClassification(DiseaseClassification diseaseClassification);

    @Override
    default int insert(Disease instance){
        this.insertDisease(instance);
        return 1;
    }

    /**
     * 根据疾病类别名称查找对应的疾病类别id
     * @param classification_name 疾病类别名称
     * @return 疾病类别id
     */
    @Select("SELECT id FROM disease_classification where name = #{classification_name}")
    int findDiseaseClassificationIdByName(@Param("classification_name") String classification_name);

    /**
     * 查找所有疾病类别
     * @return 所有疾病类别列表
     */
    @Select("SELECT * FROM disease_classification")
    List<DiseaseClassification> findAllDiseaseClassification();

    /**
     * 检查数据库中是否存在该疾病
     * @param code 疾病编码
     * @return 该疾病编码在disease表中存在的数量
     */
    @Select("SELECT count(*) FROM disease WHERE code = #{code}")
    int checkCodeExist(@Param("code") String code);

    /**
     * 检查数据库中是否存在该疾病分类
     * @param classification_id 疾病分类id
     * @return 该疾病分类id在disease_classification表中存在的数量
     */
    @Select("SELECT count(*) FROM disease_classification WHERE id = #{classification_id}")
    int checkClassificationExist(@Param("classification_id") int classification_id);

    /**
     * 检查数据库中是否存在该疾病
     * @param id 疾病id
     * @return 该疾病id在disease表中存在的数量
     */
    @Select("SELECT count(*) FROM disease WHERE id = #{id}")
    int checkIdExist(@Param("id") int id);

    /**
     * 查找所有疾病
     * @return 所有疾病列表
     */
    @Select("SELECT disease.id, disease.code, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id")
    List<Disease> findall();
}
 