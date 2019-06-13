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
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        return Response.ok(medicalRecordTemplateService.selectByUser(uid,department_id));
    }

    @PostMapping("/detail")
    @ResponseBody
    public Map detail(int id){
        return Response.ok(medicalRecordTemplateService.selectById(id));
    }

    @PostMapping("/create")
    @ResponseBody
    public Map create(@RequestBody Map req){
        MedicalRecordTemplate medicalRecordTemplate = new MedicalRecordTemplate();
        int type = (int)req.get("type");
        String name = (String)req.get("name");
        while (medicalRecordTemplateService.selectByName(name).size()!=0){
            name = name + "(1)";
        }
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        medicalRecordTemplate.setType(type);
        medicalRecordTemplate.setName(name);
        medicalRecordTemplate.setUser_id(uid);
        medicalRecordTemplate.setDepartment_id(department_id);
        medicalRecordTemplate = init(medicalRecordTemplate);

        int medical_record_id = medicalRecordTemplateService.insert(medicalRecordTemplate);
        Registration registration = outpatientRegistrationService.findRegistrationById(medical_record_id);
        registration.setId_number(RegistrationConfig.registrationFinished);
        outpatientRegistrationService.updateStatus(registration);

        return Response.ok(medicalRecordTemplateService.selectByUser(uid,department_id));
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        int id = (int)req.get("id");
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();

        if(medicalRecordTemplateService.selectById(id)==null)
            return Response.error("错误，该病历模板不存在");

        MedicalRecordTemplate medicalRecordTemplate = Utils.fromMap(req,MedicalRecordTemplate.class);
        medicalRecordTemplateService.update(medicalRecordTemplate);

        return Response.ok(medicalRecordTemplateService.selectByUser(uid,department_id));
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        int uid = Auth.uid(req);
        int department_id = userService.findByUid(uid).getDepartment_id();
        List id_list = (List)req.get("idArr");
        id_list.forEach(id->{
            MedicalRecordTemplate medicalRecordTemplate = medicalRecordTemplateService.selectById((int)id);
            if(medicalRecordTemplate!=null)
                medicalRecordTemplateService.delete((int)id);
        });

        return Response.ok(medicalRecordTemplateService.selectByUser(uid,department_id));
    }

    private MedicalRecordTemplate init(MedicalRecordTemplate medicalRecordTemplate){
        medicalRecordTemplate.setAllergy_history("");
        medicalRecordTemplate.setChief_complaint("");
        medicalRecordTemplate.setCurrent_medical_history("");
        medicalRecordTemplate.setCurrent_treatment_situation("");
        medicalRecordTemplate.setPast_history("");
        medicalRecordTemplate.setPhysical_examination("");
        medicalRecordTemplate.setCreate_time(Utils.getSystemTime());
        return medicalRecordTemplate;
    }

}
