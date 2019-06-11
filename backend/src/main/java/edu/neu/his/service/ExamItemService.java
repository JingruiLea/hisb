package edu.neu.his.service;

import edu.neu.his.bean.ExamItem;
import edu.neu.his.mapper.ExamItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamItemService {
    @Autowired
    ExamItemMapper examItemMapper;

    @Transactional
    public int insert(ExamItem examItem){
        return examItemMapper.insert(examItem);
    }
    @Transactional
    public int deleteById(Integer id){
        return examItemMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public ExamItem selectByDetial(Integer nonDrugId, Integer examId){
        return examItemMapper.selectOneByDetail(nonDrugId, examId);
    }
}
