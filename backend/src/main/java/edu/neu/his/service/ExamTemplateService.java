package edu.neu.his.service;

import edu.neu.his.bean.ExamTemplate;
import edu.neu.his.mapper.auto.ExamTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExamTemplateService {

    @Autowired
    ExamTemplateMapper examTemplateMapper;

    @Transactional
    public List<ExamTemplate> findAll(){
        return examTemplateMapper.selectAll();
    }

    @Transactional
    public int insert(ExamTemplate examTemplate){
        return examTemplateMapper.insert(examTemplate);
    }

    public ExamTemplate selectByName(String name){
        return examTemplateMapper.selectByName(name);
    }
}
