package edu.neu.his.bean.medicalRecord;

import edu.neu.his.config.Auth;
import edu.neu.his.config.Response;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现病历模版管理的相关功能
 */
@RestController
@RequestMapping("/medicalRecordTemplate")
public class MedicalRecordTemplateController {
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;

    @Autowired
    private UserService userService;

    /**
     * 获得所有病历模版的列表
     * @param req 前端传递的request，包含用户id
     * @return 查找到的所有病历模版的列表
     */
    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        return Response.ok(returnList(req));
    }

    /**
     * 获得病历模版详情
     * @param req 前端传递的request，包含病历号
     * @return 查找到的所有病历模版详情的列表
     */
    @PostMapping("/detail")
    @ResponseBody
    public Map detail(@RequestBody Map req){
        int id = (int)req.get("id");
        return Response.ok(medicalRecordTemplateService.selectById(id));
    }

    /**
     * 创建病历模版
     * @param req 前端传递的request，包含MedicalRecordTemplate类中的各个字段
     * @return 创建的病历模版列表
     */
    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        req = Utils.initMap(req);
        MedicalRecordTemplate medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        String title = (String)req.get("title");
        title = updateCheckTitle(medicalRecordTemplate);
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        medicalRecordTemplate.setTitle(title);
        medicalRecordTemplate.setUser_id(uid);
        medicalRecordTemplate.setDepartment_id(department_id);
        medicalRecordTemplate.setCreate_time(Utils.getSystemTime());

        medicalRecordTemplateService.insert(medicalRecordTemplate);

        return Response.ok(returnList(req));
    }

    /**
     * 更新病历模版
     * @param req 前端传递的request，包含MedicalRecordTemplate类中的各个字段
     * @return 更新的病历模版列表
     */
    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        int id = (int)req.get("id");
        MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById(id);
        if(medicalRecordTemplate==null)
            return Response.error("错误，该病历模板不存在");

        req = Utils.initMap(req);
        String time = medicalRecordTemplate.getCreate_time();
        MedicalRecordTemplate newMedicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        newMedicalRecordTemplate.setCreate_time(time);
        newMedicalRecordTemplate.setDepartment_id(medicalRecordTemplate.getDepartment_id());
        newMedicalRecordTemplate.setUser_id(medicalRecordTemplate.getUser_id());

        String title = updateCheckTitle(newMedicalRecordTemplate);
        medicalRecordTemplate.setTitle(title);

        medicalRecordTemplateService.update(newMedicalRecordTemplate);

        return Response.ok(returnList(req));
    }

    /**
     * 删除病历模版
     * @param req 前端传递的request，要删除的病历模版id的列表
     * @return 删除的的病历模版列表
     */
    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        List id_list = (List)req.get("idArr");
        id_list.forEach(id->{
            MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById((int)id);
            if(medicalRecordTemplate!=null)
                medicalRecordTemplateService.delete((int)id);
        });

        return Response.ok(returnList(req));
    }

    private Map returnList(Map req){
        Map data = new HashMap();
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();
        data.put("personal",medicalRecordTemplateService.selectByUser(uid));
        data.put("department",medicalRecordTemplateService.selectByDepartment(department_id));
        data.put("hospital",medicalRecordTemplateService.selectByType(MedicalRecordStatus.Public));
        return data;
    }

    private String updateCheckTitle(MedicalRecordTemplate medicalRecordTemplate) {
        String title = medicalRecordTemplate.getTitle();
        List<MedicalRecordTemplate> list = medicalRecordTemplateService.selectByTitle(title);
        if (list.size() == 1 && list.get(0).getId() == medicalRecordTemplate.getId()) {
            return title;
        }
        while (medicalRecordTemplateService.selectByTitle(title).size() != 0) {
            title = title + "(1)";
        }
        return title;
    }

}
