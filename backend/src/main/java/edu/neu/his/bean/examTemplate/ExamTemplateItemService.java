package edu.neu.his.bean.examTemplate;

import edu.neu.his.auto.ExamTemplateItemMapper;
import edu.neu.his.auto.ExamTemplateMapper;
import edu.neu.his.auto.NonDrugChargeItemMapper;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *实现处理数据库中exam_template_item、exam_template表的相关操作
 */
@Service
public class ExamTemplateItemService {

    @Autowired
    ExamTemplateItemMapper examTemplateItemMapper;

    @Autowired
    ExamTemplateMapper examTemplateMapper;

    @Autowired
    NonDrugChargeItemMapper nonDrugChargeItemMapper;

    /**
     * 向数据库中插入一条模版详情记录
     * @param examTemplateItem 要插入数据库中的examTemplateItem对象
     * @return 插入数据库中的examTemplateItem对象id
     */
    @Transactional
    public int insert(ExamTemplateItem examTemplateItem){
        return examTemplateItemMapper.insert(examTemplateItem);
    }


    public Map detail(int templateId) {
        Map res = Utils.objectToMap(examTemplateMapper.selectByPrimaryKey(templateId));
        List<ExamTemplateItem> examTemplateItems = examTemplateItemMapper.selectByTemplateId(templateId);
        List resItem = new ArrayList();
        for (ExamTemplateItem item: examTemplateItems) {
            Map resItemMap = Utils.objectToMap(item);
            resItemMap.put("non_drug_item", nonDrugChargeItemMapper.selectByPrimaryKey(item.getNon_drug_item_id()));
            resItem.add(resItemMap);
        }
        res.put("items", resItem);
        return res;
    }

    public ExamTemplateItem selectByDetail(Integer nonDrugId, int examId) {
        return examTemplateItemMapper.selectOneByDetail(nonDrugId, examId);
    }

    public void deleteById(Integer id) {
        examTemplateItemMapper.deleteByPrimaryKey(id);
    }
}
