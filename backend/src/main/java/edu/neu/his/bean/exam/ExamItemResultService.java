package edu.neu.his.bean.exam;

import edu.neu.his.auto.ExamItemResultMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 实现处理数据库中exam_item_result表的相关操作
 */
@Service
public class ExamItemResultService {

    @Autowired
    ExamItemResultMapper examItemResultMapper;

    /**
     * 向数据库中添加一条记录或更新数据库中的一条相应的科室记录
     * @param examItemResult 要进行添加或更新的ExamItemResult对象
     */
    @Transactional
    public void insertOrUpdate(ExamItemResult examItemResult) {
        ExamItemResult searchResult = examItemResultMapper.selectByExamItemId(examItemResult.getExam_item_id());
        if (searchResult != null){
            examItemResult.setId(searchResult.getId());
            examItemResultMapper.updateByPrimaryKey(examItemResult);
        }else {
            examItemResultMapper.insert(examItemResult);
        }
    }

    /**
     * 从数据库中根据检查/检验/处置子目id找到对应的检查/检验/处置结果
     * @param id 检查/检验/处置子目id
     * @return 检查/检验/处置结果
     */
    @Transactional
    public ExamItemResult selectByExamItemId(int id){
        return examItemResultMapper.selectByExamItemId(id);
    }

}
