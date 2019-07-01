package edu.neu.his.bean.examTemplate;

import edu.neu.his.bean.user.User;
import edu.neu.his.auto.ExamTemplateItemMapper;
import edu.neu.his.auto.ExamTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 *实现处理数据库中exam_template_item、exam_template表的相关操作
 */
@Service
public class ExamTemplateService {

    @Autowired
    ExamTemplateMapper examTemplateMapper;

    @Autowired
    ExamTemplateItemMapper examTemplateItemMapper;

    /**
     * 查找数据库中所有模版的列表
     * @param user 操作的医生
     * @return 返回查找到的所有模版列表
     */
    @Transactional
    public List<ExamTemplate> findAll(User user){
        return examTemplateMapper.selectAll().stream().filter(item->{
            switch (item.getDisplay_type()){
                case 0:
                    if(item.getUser_id() == user.getUid()){
                        return true;
                    }
                    break;
                case 1:
                    if(item.getDepartment_id() == user.getDepartment_id()){
                        return true;
                    }
                    break;
                case 2:
                    return true;
            }
            return false;
        }).collect(Collectors.toList());
    }

    /**
     * 向数据库中插入一条模版记录
     * @param examTemplate 要插入数据库中的ExamTemplate对象
     * @return 插入数据库中的ExamTemplate对象id
     */
    @Transactional
    public int insert(ExamTemplate examTemplate){
        return examTemplateMapper.insert(examTemplate);
    }

    /**
     * 从数据库中根据id找到对应的模版
     * @param id 模版id
     * @return 根据id找到的对应模版
     */
    @Transactional
    public ExamTemplate selectById(int id){
        return examTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 从数据库中根据name找到对应的模版
     * @param name 模版模版名称
     * @return 根据name找到的对应模版
     */
    @Transactional
    public ExamTemplate selectByName(String name){
        return examTemplateMapper.selectByName(name);
    }

    /**
     * 根据id从数据库中删除对应模版
     * @param id 模版id
     */
    @Transactional
    public void deleteByTemplateId(int id) {
        examTemplateMapper.deleteByPrimaryKey(id);
        examTemplateMapper.deleteItemByPrimaryKey(id);
    }

    /**
     * 根据id从数据库中删除对应模版详情
     * @param id 模版详情id
     */
    @Transactional
    public void deleteItemByPrimaryKey(int id){
        examTemplateMapper.deleteItemByPrimaryKey(id);
    }

    /**
     *  根据id从数据库中删除对应非药品项目
     * @param id 模版id
     * @return 删除对应的非药品项目id
     */
    @Transactional
    public List<Integer> getNonDrugItemIdListById(Integer id){
        List<ExamTemplateItem> itemList = examTemplateItemMapper.selectByTemplateId(id);
        return itemList.stream().map(ExamTemplateItem::getNon_drug_item_id).collect(Collectors.toList());
    }

    public Integer updateById(ExamTemplate examTemplate) {
        return examTemplateMapper.updateByPrimaryKey(examTemplate);
    }
}
