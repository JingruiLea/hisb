package edu.neu.his.bean.exam;

import edu.neu.his.mapper.auto.ExamTemplateItemMapper;
import edu.neu.his.mapper.auto.NonDrugChargeItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ExamTemplateItemService {

    @Autowired
    ExamTemplateItemMapper examTemplateItemMapper;

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    @Transactional
    public int insert(ExamTemplateItem examTemplateItem){
        return examTemplateItemMapper.insert(examTemplateItem);
    }


    public List<HashMap> detail(int templateId) {
        List<HashMap> res = new ArrayList<>();
        List<ExamTemplateItem> examTemplateItems = examTemplateItemMapper.selectByTemplateId(templateId);
        for (ExamTemplateItem item: examTemplateItems) {
            HashMap<String, Object> resItem = new HashMap<>();
            resItem.put("id", item.getId());
            resItem.put("exam_template_id", item.getExam_template_id());
            resItem.put("non_drug_item", nonDrugChargeItemMapper.selectByPrimaryKey(item.getId()));
            res.add(resItem);
        }
        return res;
    }

    public ExamTemplateItem selectByDetail(Integer nonDrugId, int examId) {
        return examTemplateItemMapper.selectOneByDetail(nonDrugId, examId);
    }

    public void deleteById(Integer id) {
        examTemplateItemMapper.deleteByPrimaryKey(id);
    }
}
