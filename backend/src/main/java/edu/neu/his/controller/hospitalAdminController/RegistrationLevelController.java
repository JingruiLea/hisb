package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.Registration_level;
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
    public Map updateRegistration_level(@RequestBody Registration_level registration_level){
        registrationLevelService.updateDepartment(registration_level);
        return Response.Ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map  insertRegistration_level(@RequestBody Registration_level registration_level){
        registrationLevelService.insertRegistration_level(registration_level);
        return Response.Ok();
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteRegistration_level(@RequestBody Map req){
        List<Map> registration_levels = (List<Map>)req.get("data");
        registration_levels.forEach(registration_level -> registrationLevelService.deleteRegistration_level((int)registration_level.get("id")));
        return Response.Ok();
    }
}

