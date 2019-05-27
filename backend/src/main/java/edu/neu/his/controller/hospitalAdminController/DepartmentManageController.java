package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.Department;
import edu.neu.his.config.Response;
import edu.neu.his.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//3.1 科室管理
@RestController
@RequestMapping("/departmentManage")
public class DepartmentManageController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/findByName")
    @ResponseBody
    public Map departmentFindByName(@RequestBody Map req){
        String name = (String)req.get("name");
        return Response.Ok(departmentService.findDepartmentByName(name));
    }

    @GetMapping("/getAll")
    @ResponseBody
    public Map listAllDepartment(){
        Map data = new HashMap();
        data.put("department_classification",departmentService.findAllClassification());
        data.put("department",departmentService.findAll());
        return Response.Ok(data);
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateDepartment(@RequestBody Map req){
        Map res = new HashMap();
        int id = (int)req.get("id");
        String code = (String)req.get("code");
        String name = (String)req.get("name");
        String classification = (String)req.get("classification");
        int classification_id = departmentService.findClassificationIdByName(classification);

        if(canUpdate(id,name,code,classification)) {
            Department department = new Department(id, code, name, classification, isClinical(name),classification_id);
            departmentService.updateDepartment(department);
            return Response.Ok();
        }else{
            return Response.Error("编号冲突 或 该类别不存在!");
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertDepartment(@RequestBody Map req){
        String code = (String)req.get("code");
        String name = (String)req.get("name");
        String classification = (String)req.get("classification");
        int classfication_id = departmentService.findClassificationIdByName(classification);
        Department department = new Department();
        department.setCode(code);
        department.setName(name);
        department.setClassification(classification);
        department.setClassification_id(classfication_id);
        department.setIs_clinical(isClinical(classification));

        if(canOperate(name,code,classification)){
            department.setIs_clinical(isClinical(name));
            departmentService.insertDepartment(department);
            return Response.Ok();
        }else {
            return Response.Error("编号冲突 或 该类别不存在");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteDepartment(@RequestBody Map req){
        ArrayList departments = (ArrayList)req.get("data");
        for(int i=0; i<departments.size(); i++) {
            int id = (int)((Map<String,Object>)departments.get(i)).get("id");
            departmentService.deleteDepartment(id);
        }
        return Response.Ok();
    }

    private boolean isClinical(String name){
        if(name.equals("超声科")||name.equals("检验科")||name.equals("手术室")
                ||name.equals("核医学科")||name.equals("放射科"))
            return false;
        else
            return true;
    }

    private boolean canOperate(String name, String code, String classification){
        List<Department> list = departmentService.findAll();
        for(int i=0; i<list.size(); i++){
            if(list.get(i).getName().equals(name)||list.get(i).getCode().equals(code))
                return false;
        }

        List<String> cla = departmentService.findAllClassification();
        for(int i=0; i<cla.size(); i++){
            if(cla.get(i).equals(classification))
                return true;
        }
        return false;
    }

    private boolean canUpdate(int id, String name, String code, String classification){
        List<Department> list = departmentService.findAll();
        for(int i=0; i<list.size(); i++){
            if((list.get(i).getName().equals(name)||list.get(i).getCode().equals(code))
                    && list.get(i).getId()!=id)
                return false;
        }

        List<String> cla = departmentService.findAllClassification();
        for(int i=0; i<cla.size(); i++){
            if(cla.get(i).equals(classification))
                return true;
        }
        return false;
    }

}
