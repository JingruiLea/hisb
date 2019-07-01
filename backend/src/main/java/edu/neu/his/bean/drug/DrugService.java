package edu.neu.his.bean.drug;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.util.Common;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 实现处理数据库中drug表的相关操作
 */
@Service
public class DrugService {

    @Autowired
    AutoDrugMapper autoDrugMapper;

    @Autowired
    DrugMapper drugMapper;

    /**
     * 向数据库中插入一条药品记录
     * @param drug 要插入数据库中的Drug对象
     * @return 药品id
     */
    @Transactional
    public int insertDrug(Drug drug){
        drugMapper.insert(drug);
        return drug.getId();
    }

    /**
     * 根据药品类别获得费用科目id
     * @param drug 药品
     * @return 费用科目id
     */
    @Transactional
    public int getExpenseClassificationId(Drug drug){
        switch (drug.getType()){
            case Common.ZHONGCAOYAOTYPE:
                return 15;
            case Common.XIYAOTYPE:
                return 13;
            case Common.ZHONGCHENGYAOTYPE:
                return 14;
        }
        return 0;
    }

    /**
     * 查找数据库中所有药品的列表
     * @param drugList 药品列表
     * @return 可用药品列表
     */
    @Transactional
    public boolean allItemValid(List<Map> drugList) {
        for(Map map:drugList){
            Drug drug = autoDrugMapper.selectByPrimaryKey((Integer) map.get("drug_id"));
            if(drug == null) return false;
        }
        return true;
    }

    /**
     * 检查药品是否存在
     * @param id 药品id
     * @return 返回该药品是否存在，true代表存在，false代表不存在
     */
    @Transactional
    public boolean existDrug(int id){
        if(autoDrugMapper.selectByPrimaryKey(id)==null)
            return false;
        else return true;
    }

    /**
     * 根据id从数据库中删除对应药品
     * @param id 药品id
     * @return 删除的药品id
     */
    @Transactional
    public int deleteDrug(int id){
        return autoDrugMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新数据库中的一条相应的药品记录
     * @param drug 要进行更新的Drug对象
     * @return 更新的药品id
     */
    @Transactional
    public int updateDrug(Drug drug){
        return autoDrugMapper.updateByPrimaryKey(drug);
    }

    /**
     * 查找数据库中所有药品的列表
     * @return 所有药品的列表
     */
    @Transactional
    public List<Drug> selectAllDrug(){
        return autoDrugMapper.selectAll();
    }

    /**
     * 从数据库中根据名称找到对应的药品
     * @param name 药品名称
     * @return 药品
     */
    @Transactional
    public List<Drug> selectDrugByName(String name){
        return drugMapper.selectByName(name);
    }

    /**
     * 将文件批量导入数据库
     * @param inputStream 输入流
     * @return 是否导入成功
     */
    @Transactional
    public boolean importFromFile(InputStream inputStream) {
        try {
            ExcelImportation excel = new ExcelImportation(inputStream, Drug.class, drugMapper);
            excel.setColumnFields("id", "code", "name", "format", "unit", "manufacturer", "dosage_form", "type", "price", "pinyin", "stock");
            excel.skipLine(2);
            Map<String, Function<String, ?>> preFunctionMap = excel.getPreFunctionMap();
            preFunctionMap.put("stock", (i)-> 100);
            preFunctionMap.put("type", s->{
                switch (Integer.parseInt(s)){
                    case 101:
                        return Common.XIYAOTYPE;
                    case 103:
                        return Common.ZHONGCAOYAOTYPE;
                    case 102:
                        return Common.ZHONGCHENGYAOTYPE;
                    default:
                        return "";
                }
            });
            excel.exec();
            return true;
        } catch (Exception e)  {
            return false;
        }

    }

    /**
     * 从数据库中根据ID找到对应的药品
     * @param id 药品id
     * @return 药品
     */
    public Drug selectDrugById(int id){
        return autoDrugMapper.selectByPrimaryKey(id);
    }
}
