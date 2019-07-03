package edu.neu.his.bean.statistics;

import edu.neu.his.bean.expenseClassification.ExpenseClassificationService;
import edu.neu.his.bean.registration.OutpatientRegistrationService;
import edu.neu.his.bean.user.UserService;
import edu.neu.his.config.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private ExpenseClassificationService expenseClassificationService;

    @Autowired
    private OutpatientRegistrationService outpatientRegistrationService;

    @Autowired
    private UserService userService;

    @RequestMapping("/getFeeName")
    @ResponseBody
    public Map getFeeName(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int department_id = (int)req.get("department_id");
        List<Map<String,Object>> data = statisticsService.getFeeName(start_time,end_time,department_id);
        Map<String, Double> res = new HashMap<>();
        data.forEach(ele->{
           res.put((String) ele.get("fee_name"),(Double) ele.get("money"));
        });

        List<String> xList = expenseClassificationService.getName();

        Map result = new HashMap();
        result.put("xAxis",xList);
        result.put("series",res);
        return Response.ok(result);
    }

    @RequestMapping("/getByPrescribe")
    @ResponseBody
    public Map getByPrescribe(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int department_id = (int)req.get("department_id");
        List<Map<String,Object>> data = statisticsService.getByPrescribe(start_time,end_time,department_id);
        Map<String, Double> res = new HashMap<>();
        data.forEach(ele->{
            res.put((String) ele.get("fee_name"),(Double) ele.get("money"));
        });

        List<String> xList = expenseClassificationService.getName();

        Map result = new HashMap();
        result.put("xAxis",xList);
        result.put("series",res);
        return Response.ok(result);
    }

    @RequestMapping("/getTotalPrescribe")
    @ResponseBody
    public Map getTotalPrescribe(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        List<Map<Object,Object>> data = statisticsService.getTotalPrescribe(start_time,end_time);
        Map<Integer, Double> res = new HashMap<>();
        data.forEach(ele->{
            int department_id = (int)ele.get("department_id");
            res.put(department_id,(Double) ele.get("total"));
        });
        return Response.ok(res);
    }

    @RequestMapping("/getTotal")
    @ResponseBody
    public Map getTotal(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        List<Map<Object,Object>> data = statisticsService.getTotal(start_time,end_time);
        Map<Integer, Double> res = new HashMap<>();
        data.forEach(ele->{
            int department_id = (int)ele.get("execute_department_id");
            res.put(department_id,(Double) ele.get("total"));
        });
        return Response.ok(res);
    }

    @RequestMapping("/getTotalUser")
    @ResponseBody
    public Map getTotalUser(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        List<Map<Object,Object>> data = statisticsService.getTotalUser(start_time,end_time);
        Map<Integer, Double> res = new HashMap<>();
        data.forEach(ele->{
            int user_id = (int)ele.get("create_user_id");
            res.put(user_id,(Double) ele.get("total"));
        });
        return Response.ok(res);
    }

    @RequestMapping("/getTotalPatient")
    @ResponseBody
    public Map getTotalPatient(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int doctor_id = (int)req.get("_uid");
        List<Map<Object,Object>> data = statisticsService.getTotalPatient(start_time,end_time,doctor_id);
        Map<Integer, Double> res = new HashMap<>();
        data.forEach(ele->{
            int medical_record_id = (int)ele.get("medical_record_id");
            res.put(medical_record_id,(Double) ele.get("total"));
        });
        return Response.ok(res);
    }

    @RequestMapping("/getTotalRegister")
    @ResponseBody
    public Map getTotalRegister(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int doctor_id = (int)req.get("_uid");
        int department_id = userService.findByUid(doctor_id).getDepartment_id();
        List<Map<Object,Object>> data = statisticsService.getTotalRegister(start_time,end_time,doctor_id,department_id);
        Map<Integer, Double> res = new HashMap<>();
        data.forEach(ele->{
            int medical_record_id = (int)ele.get("medical_record_id");
            res.put(medical_record_id,(Double) ele.get("total"));
        });
        return Response.ok(res);
    }

    @RequestMapping("/statisticsByUser")
    @ResponseBody
    public Map statisticsByUser(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int user_id = (int)req.get("user_id");
        List<Map<String,Object>> data = statisticsService.statisticsByUser(start_time,end_time,user_id);
        Map<String, Double> res = new HashMap<>();
        data.forEach(ele->{
            res.put((String) ele.get("fee_name"),(Double) ele.get("money"));
        });

        List<String> xList = expenseClassificationService.getName();

        Map result = new HashMap();
        result.put("xAxis",xList);
        result.put("series",res);
        return Response.ok(result);
    }

    @RequestMapping("/statisticsByRegister")
    @ResponseBody
    public Map statisticsByRegister(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int doctor_id = (int)req.get("_uid");
        int department_id = userService.findByUid(doctor_id).getDepartment_id();
        int medical_record_id = (int)req.get("medical_record_id");
        List<Map<String,Object>> data = statisticsService.statisticsByRegister(start_time,end_time,medical_record_id,doctor_id,department_id);
        Map<String, Double> res = new HashMap<>();
        data.forEach(ele->{
            res.put((String) ele.get("fee_name"),(Double) ele.get("money"));
        });

        List<String> xList = expenseClassificationService.getName();

        Map result = new HashMap();
        result.put("xAxis",xList);
        result.put("series",res);
        return Response.ok(result);
    }

    @RequestMapping("/statisticsByDoctor")
    @ResponseBody
    public Map statisticsByDoctor(@RequestBody Map req){
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        int medical_record_id = (int)req.get("medical_record_id");
        List<Map<String,Object>> data = statisticsService.statisticsByDoctor(start_time,end_time,medical_record_id);
        Map<String, Double> res = new HashMap<>();
        data.forEach(ele->{
            res.put((String) ele.get("fee_name"),(Double) ele.get("money"));
        });

        List<String> xList = expenseClassificationService.getName();

        Map result = new HashMap();
        result.put("xAxis",xList);
        result.put("series",res);
        return Response.ok(result);
    }

    @RequestMapping("/getPatientName")
    @ResponseBody
    public Map getPatientName(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        return Response.ok(outpatientRegistrationService.findRegistrationById(medical_record_id));
    }
}
