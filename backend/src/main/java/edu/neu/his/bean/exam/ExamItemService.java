package edu.neu.his.bean.exam;

import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中exam_item表的相关操作
 */
@Service
public class ExamItemService {
    @Autowired
    ExamItemMapper examItemMapper;

    /**
     * 向数据库中插入一条科室记录检查/检验/处置子目
     * @param examItem 要插入数据库中的ExamItem对象
     * @return 检查/检验/处置子目id
     */
    @Transactional
    public int insert(ExamItem examItem){
        return examItemMapper.insert(examItem);
    }

    /**
     * 根据id从数据库中删除对应检查/检验/处置子目
     * @param id 要删除的检查/检验/处置子目的id
     * @return 删除的检查/检验/处置子目的id
     */
    @Transactional
    public int deleteById(Integer id){
        return examItemMapper.deleteByPrimaryKey(id);
    }

    /**
     * 从数据库中根据非药品项目id和检查/检验/处置id找到对应的检查/检验/处置子目
     * @param nonDrugId 非药品项目id
     * @param examId 检查/检验/处置子目id
     * @return 对应的检查/检验/处置子目
     */
    @Transactional
    public ExamItem selectByDetail(Integer nonDrugId, Integer examId){
        return examItemMapper.selectOneByDetail(nonDrugId, examId);
    }

    /**
     * 登记检查/检验/处置
     * @param itemIds 检查/检验/处置子目id列表
     * @return 是否登记成功
     */
    @Transactional
    public boolean register(List<Integer> itemIds){
        for (Integer itemId : itemIds) {
            ExamItem examItem = examItemMapper.selectByPrimaryKey(itemId);
            if(!hasPay(examItem)){
                return false;
            }else {
                examItem.setStatus(Common.YIDENGJI);
                examItemMapper.updateByPrimaryKey(examItem);
            }
        }
        return true;
    }

    private boolean hasPay(ExamItem item){
        if(item == null)
            return false;

        if (Common.WEIDENGJI.equals(item.getStatus())){
            return true;
        }

        return false;
    }

    public ExamItem selectByPrimaryKey(int id) {
        return examItemMapper.selectByPrimaryKey(id);
    }

    public void update(ExamItem examItem){
        examItemMapper.updateByPrimaryKey(examItem);
    }
}
