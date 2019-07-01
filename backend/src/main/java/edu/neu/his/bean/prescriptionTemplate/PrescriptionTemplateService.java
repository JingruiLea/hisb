package edu.neu.his.bean.prescriptionTemplate;

import edu.neu.his.auto.AutoDrugMapper;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.auto.PrescriptionTemplateItemMapper;
import edu.neu.his.auto.PrescriptionTemplateMapper;
import edu.neu.his.bean.drug.Drug;
import edu.neu.his.bean.drug.DrugService;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *  实现处理数据库中prescription_template、prescription_template_item表的相关操作
 */
@Service
public class PrescriptionTemplateService {

    @Autowired
    PrescriptionTemplateMapper prescriptionTemplateMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    PrescriptionTemplateItemMapper itemMapper;

    @Autowired
    AutoDrugMapper drugMapper;

    @Autowired
    DrugService drugService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    /**
     * 向数据库中插入一条处方组套记录
     * @param req 要插入的PrescriptionTemplate对象的信息
     * @param user 用户
     * @param name 组套名称
     * @param drugIds 药品id列表
     * @return 添加的处方id
     */
    @Transactional
    public int create(Map req, User user, String name, List<Map> drugIds){
        PrescriptionTemplate prescription = Utils.fromMap(req, PrescriptionTemplate.class);
        prescription.setCreate_time(Utils.getSystemTime());
        prescription.setTemplate_name(name);
        prescription.setUser_id(user.getUid());
        prescription.setDepartment_id(user.getDepartment_id());
        prescriptionTemplateMapper.insert(prescription);
        addItems(prescription.getId(), drugIds);
        return prescription.getId();
    }

    /**
     *  判断病历是否已提交
     * @param id 病历id
     * @return 是否已提交
     */
    @Transactional
    public boolean recordMedicalHasSubmit(int id){
        return medicalRecordService.medicalRecordHasSubmit(id);
    }

    /**
     * 重命名组套
     * @param templateName 组套名称
     * @return 重命名的组套名称
     */
    @Transactional
    public String rename(String templateName){
        while(prescriptionTemplateMapper.selectByName(templateName)!=null){
            templateName += "(1)";
        }
        return templateName;
    }

    /**
     * 向数据库中插入处方组套详情
     * @param prescriptionId 处方id
     * @param drugInfos 药品信息列表
     */
    @Transactional
    public void addItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem prescriptionItem = new PrescriptionTemplateItem();
            prescriptionItem = Utils.fromMap(i, PrescriptionTemplateItem.class);
            prescriptionItem.setDrug_id((Integer)i.get("drug_id"));
            prescriptionItem.setPrescription_template_id(prescriptionId);
            itemMapper.insert(prescriptionItem);
        }
    }

    /**
     * 更新数据库中的一条相应的处方组套记录
     * @param prescriptionId 组套id
     * @param drugInfos 药品信息列表
     */
    @Transactional
    public void updateItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem prescriptionItem = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("id"));
            prescriptionItem.setAmount((Integer) i.get("amount"));
            prescriptionItem.setNote((String)i.get("note"));
            itemMapper.updateByPrimaryKey(prescriptionItem);
        }
    }

    /**
     * 根据处方组套id和药品信息列表从数据库中删除对应处方组套详情
     * @param prescriptionId 处方id
     * @param drugInfos 药品信息列表
     */
    @Transactional
    public void removeItems(int prescriptionId, List<Map> drugInfos){
        for(Map i:drugInfos){
            PrescriptionTemplateItem item = itemMapper.selectByDetail(prescriptionId, (Integer) i.get("drug_id"));
            itemMapper.deleteByPrimaryKey(item.getId());
        }
    }

    /**
     * 根据处方组套id从数据库中删除对应处方组套详情
     * @param prescriptionId 处方id
     */
    @Transactional
    public void removeAllItems(int prescriptionId){
        itemMapper.deleteAllByPrescriptionId(prescriptionId);
    }

    /**
     * 从数据库中根据处方id找到对应的添加了药品的处方组套详情
     * @param prescriptionId 处方id
     * @return 根据处方id找到的对应的添加了药品的处方组套详情列表
     */
    @Transactional
    public List<Map> items(int prescriptionId){
        List<PrescriptionTemplateItem> items = itemMapper.selectByPrescriptionId(prescriptionId);
        List<Map> res = new ArrayList<>();
        for(PrescriptionTemplateItem item:items){
            Map map = Utils.objectToMap(item);
            Drug drug = drugMapper.selectByPrimaryKey(item.getDrug_id());
            map.put("drug_item", Utils.objectToMap(drug));
            res.add(map);
        }
        return res;
    }

    /**
     * 从数据库中根据处方组套id找到对应的处方组套详情列表
     * @param prescriptionId 处方id
     * @return 根据处方组套id找到的对应处方组套详情列表
     */
    @Transactional
    public Map detail(int prescriptionId){
        PrescriptionTemplate prescriptionTemplate = prescriptionTemplateMapper.selectByPrimaryKey(prescriptionId);
        Map res = Utils.objectToMap(prescriptionTemplate);
        res.put("items", items(prescriptionId));
        return res;
    }

    /**
     * 查找数据库中所有处方组套的列表
     * @param user 用户
     * @return 查找到的数据库中所有处方组套的列表
     */
    @Transactional
    public List<PrescriptionTemplate> findAll(User user){
       return prescriptionTemplateMapper.selectAll().stream().filter(item->{
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
     * 根据处方id从数据库中删除对应组套和组套详情
     * @param id 处方id
     */
    @Transactional
    public void delete(int id){
        List<PrescriptionTemplateItem> list = itemMapper.selectByPrescriptionId(id);
        for (PrescriptionTemplateItem item : list) {
            itemMapper.deleteByPrimaryKey(item.getId());
        }
        prescriptionTemplateMapper.deleteByPrimaryKey(id);
    }

    /**
     * 从数据库中根据id找到对应的组套
     * @param id 组套id
     * @return 根据id找到的对应组套
     */
    @Transactional
    public PrescriptionTemplate selectById(int id){
        return prescriptionTemplateMapper.selectByPrimaryKey(id);
    }


}
