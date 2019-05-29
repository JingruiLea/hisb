
package edu.neu.his.mapper;

import edu.neu.his.bean.Disease;
import edu.neu.his.bean.DiseaseClassification;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "DiseaseMapper")
public interface DiseaseMapper {
    @Select("SELECT disease.id, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and disease.name = #{name}")
    Disease findByName(@Param("name") String name);

    @Select("SELECT disease.id, disease.name, classification_id, disease_classification.name as classification_name, pinyin, custom_name, custom_pinyin " +
            "FROM  disease, disease_classification " +
            "WHERE disease.classification_id = disease_classification.id and classification_id = #{classification_id}")
    List<Disease> findAll(@Param("classification_id") int classification_id);

    @Insert("INSERT INTO disease(name, id, classification_id, pinyin, custom_name, custom_pinyin) " +
            "VALUES(#{name}, #{id}, #{classification_id}, #{pinyin}, #{custom_name}, #{custom_pinyin})")
    void insertDisease(Disease disease);

    @Update("UPDATE disease SET name = #{name}, id = #{id}, classification_id=#{classification_id}, pinyin=#{pinyin}," +
            "custom_name=#{custom_name}, custom_pinyin = #{custom_pinyin} WHERE id = #{raw_id}")
    void update(@Param("raw_id") String rawId,@Param("id") String id,@Param("name") String name,@Param("classification_id") int classification_id,
                @Param("pinyin") String pinyin,@Param("custom_name") String custom_name,@Param("custom_pinyin") String custom_pinyin);

    @Delete("DELETE FROM disease WHERE id=#{id}")
    void deleteDisease(String id);



    @Insert("INSERT INTO disease_classification(name) VALUES(#{name})")
    void insertDiseaseClassification(DiseaseClassification diseaseClassification);

    @Select("SELECT id FROM disease_classification where name = #{classification_name}")
    int findDiseaseClassificationIdByName(@Param("classification_name") String classification_name);

    @Select("SELECT * FROM disease_classification")
    List<DiseaseClassification> findAllDiseaseClassification();

    @Select("SELECT count(*) FROM disease WHERE id = #{id}")
    int checkIdExist(@Param("id") String id);
}
 