package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.MedicalRecordTemplate;
import edu.neu.his.config.Auth;
import edu.neu.his.config.MedicalRecordStatus;
import edu.neu.his.config.Response;
import edu.neu.his.service.MedicalRecordTemplateService;
import edu.neu.his.service.UserService;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("outpatientDoctor/medicalRecordTemplate")
public class MedicalRecordTemplateController {
    @Autowired
    private MedicalRecordTemplateService medicalRecordTemplateService;

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @ResponseBody
    public Map list(@RequestBody Map req){
        int type = (int)req.get("type");
        if(type == MedicalRecordStatus.SelectByUserId){
            int uid = Auth.uid(req);
            return Response.Ok(medicalRecordTemplateService.SelectByUserId(uid));
        }else if(type == MedicalRecordStatus.SelectByDepartmentId){
            int department_id = (int)req.get("department_id");
            return Response.Ok(medicalRecordTemplateService.SelectByDepartmentId(department_id));
        }else
            return Response.Ok(medicalRecordTemplateService.SelectAll());
    }

    @GetMapping("/detail")
    @ResponseBody
    public Map detail(int id){
        return Response.Ok(medicalRecordTemplateService.SelectById(id));
    }

    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        MedicalRecordTemplate medicalRecordTemplate = new MedicalRecordTemplate();
        int type = (int)req.get("type");
        String name = (String)req.get("name");
        while (medicalRecordTemplateService.SelectByName(name).size()!=0){
            name = name + "(1)";
        }
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        medicalRecordTemplate.setType(type);
        medicalRecordTemplate.setName(name);
        medicalRecordTemplate.setUser_id(uid);
        medicalRecordTemplate.setDepartment_id(department_id);
        medicalRecordTemplate = init(medicalRecordTemplate);

        medicalRecordTemplateService.insert(medicalRecordTemplate);

        return returnList(type,uid,department_id);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) throws IOException {
        int id = (int)req.get("id");
        int type = (int)req.get("type");
        int uid = Auth.uid(req);

        if(medicalRecordTemplateService.SelectById(id)==null)
            return Response.Error("错误，该病历模板不存在");

        MedicalRecordTemplate medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        medicalRecordTemplateService.update(medicalRecordTemplate);

        return returnList(type,uid,medicalRecordTemplate.getDepartment_id());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        int id = (int)req.get("id");
        int type = (int)req.get("type");
        int uid = Auth.uid(req);
        MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.SelectById(id);
        if(medicalRecordTemplate==null)
            return Response.Error("错误，该病历模板不存在");

        medicalRecordTemplateService.delete(id);

        return returnList(type,uid,medicalRecordTemplate.getDepartment_id());
    }

    private MedicalRecordTemplate init(MedicalRecordTemplate medicalRecordTemplate){
        medicalRecordTemplate.setAllergy_history("");
        medicalRecordTemplate.setChief_complaint("");
        medicalRecordTemplate.setChinese_initial_diagnosis("");
        medicalRecordTemplate.setCurrent_medical_history("");
        medicalRecordTemplate.setCurrent_treatment_situation("");
        medicalRecordTemplate.setEnd_diagnosis("");
        medicalRecordTemplate.setPast_history("");
        medicalRecordTemplate.setWestern_initial_diagnosis("");
        medicalRecordTemplate.setPhysical_examination("");
        medicalRecordTemplate.setCreate_time(Utils.getSystemTime());
        return medicalRecordTemplate;
    }

    private Map returnList(int type, int uid, int department_id){
        if(type == MedicalRecordStatus.SelectByUserId){
            return Response.Ok(medicalRecordTemplateService.SelectByUserId(uid));
        }else if(type == MedicalRecordStatus.SelectByDepartmentId){
            return Response.Ok(medicalRecordTemplateService.SelectByDepartmentId(department_id));
        }else
            return Response.Ok(medicalRecordTemplateService.SelectAll());
    }
}
