package edu.neu.his.controller.registeredTollCollectorController;

import edu.neu.his.bean.Registration;
import edu.neu.his.config.Response;
import edu.neu.his.service.RegistrationService;
import edu.neu.his.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public Map findAll(){
        return Response.Ok(registrationService.findAll());
    }

    @PostMapping("/delete")
    public Map delete(int id){
        registrationService.deleteByPrimaryKey(id);
        return Response.Ok();
    }

    @GetMapping("/select")
    public Map select(int id){
        Registration registration = registrationService.selectByprimaryKey(id);
        return Response.Ok(registration);
    }

    @PostMapping("/insert")
    public Map insert(@RequestBody Registration registration){
        registrationService.insert(registration);
        return Response.Ok();
    }

    @GetMapping("/syncDoctorList")
    public Map doctorList(int departmentId, int registrationLevelId){
        List doctorList = userService.selectDoctorList(departmentId, registrationLevelId);
        return Response.Ok("");
    }
}
