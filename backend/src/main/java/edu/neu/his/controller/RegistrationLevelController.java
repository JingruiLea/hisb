package edu.neu.his.controller;

import edu.neu.his.bean.Registration_level;
import edu.neu.his.service.RegistrationLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registration")
public class RegistrationLevelController {
    @Autowired
    private RegistrationLevelService registrationLevelService;

    @GetMapping("/find/name")
    @ResponseBody
    public List<Registration_level> registrationFindByName(String name){
        return registrationLevelService.findByName(name);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Registration_level> listAllRegistration_level(){
        return registrationLevelService.findAll();
    }

    @PostMapping("/update")
    @ResponseBody
    public List<Registration_level>  updateRegistration_level(@RequestBody Registration_level registration_level){
        registrationLevelService.updateDepartment(registration_level);
        return registrationLevelService.findAll();
    }

    @PostMapping("/insert")
    @ResponseBody
    public List<Registration_level>  insertRegistration_level(@RequestBody Registration_level registration_level){
        registrationLevelService.insertRegistration_level(registration_level);
        return registrationLevelService.findAll();
    }

    @PostMapping("/delete")
    @ResponseBody
    public List<Registration_level>  deleteRegistration_level(int id){
        registrationLevelService.deleteRegistration_level(id);
        return registrationLevelService.findAll();
    }
}

