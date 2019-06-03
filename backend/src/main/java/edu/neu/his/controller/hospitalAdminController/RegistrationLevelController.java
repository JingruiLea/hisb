package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.RegistrationLevel;
import edu.neu.his.config.Response;
import edu.neu.his.service.RegistrationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registrationLevelManagement")
public class RegistrationLevelController {
    @Autowired
    private RegistrationLevelService registrationLevelService;

    @GetMapping("/all")
    @ResponseBody
    public Map listAllRegistration_level(){
        return Response.Ok(registrationLevelService.findAll());
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateRegistration_level(@RequestBody RegistrationLevel registration_level){
        registrationLevelService.updateDepartment(registration_level);
        return Response.Ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map  insertRegistration_level(@RequestBody RegistrationLevel registration_level){
        registrationLevelService.insertRegistration_level(registration_level);
        return Response.Ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteRegistration_level(@RequestBody Map req){
        List<Integer> registration_level_ids = (List<Integer>)req.get("data");
        registration_level_ids.forEach(id -> registrationLevelService.deleteRegistration_level(id));
        return Response.Ok();
    }
}

