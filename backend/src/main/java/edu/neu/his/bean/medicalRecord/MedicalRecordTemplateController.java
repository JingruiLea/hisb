package edu.neu.his.bean.medicalRecord;

import edu.neu.his.bean.registration.Registration;
import edu.neu.his.config.Auth;
import edu.neu.his.bean.registration.RegistrationConfig;
import edu.neu.his.config.Response;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medicalRecordTemplate")
public class MedicalRecordTemplateController {
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;

    @Autowired
    private UserService userService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @PostMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        return Response.ok(returnList(req));
    }

    @PostMapping("/detail")
    @ResponseBody
    public Map detail(int id){
        return Response.ok(medicalRecordTemplateService.selectById(id));
    }

    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        req = Utils.initMap(req);
        MedicalRecordTemplate medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        String name = (String)req.get("name");
        name = checkName(name);
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        medicalRecordTemplate.setName(name);
        medicalRecordTemplate.setUser_id(uid);
        medicalRecordTemplate.setDepartment_id(department_id);
        medicalRecordTemplate.setCreate_time(Utils.getSystemTime());

        medicalRecordTemplateService.insert(medicalRecordTemplate);

        return Response.ok(returnList(req));
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        int id = (int)req.get("id");
        MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById(id);
        if(medicalRecordTemplate==null)
            return Response.error("错误，该病历模板不存在");

        req = Utils.initMap(req);
        String time = medicalRecordTemplate.getCreate_time();
        medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        medicalRecordTemplate.setCreate_time(time);

        String name = checkName(medicalRecordTemplate.getName());
        medicalRecordTemplate.setName(name);

        medicalRecordTemplateService.update(medicalRecordTemplate);

        return Response.ok(returnList(req));
    }

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

    private String checkName(String name){
        while (medicalRecordTemplateService.selectByName(name).size()!=0){
            name = name + "(1)";
        }
        return name;
    }

}
