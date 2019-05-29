package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.Department;
import edu.neu.his.config.Response;
import edu.neu.his.service.DepartmentService;
import edu.neu.his.util.ExcelImportation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


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
        Department department = req2Department(req);
        if(canUpdate(department)) {
            departmentService.updateDepartment(department);
            return Response.Ok();
        }else{
            return Response.Error("编号冲突 或 该类别不存在!");
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Map insertDepartment(@RequestBody Map req){
        Department department = req2Department(req);
        if(canInsert(department)) {
            departmentService.insertDepartment(department);
            return Response.Ok();
        }else{
            return Response.Error("编号冲突 或 该类别不存在!");
        }
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map  deleteDepartment(@RequestBody Map req){
        ArrayList departments_id = (ArrayList)req.get("data");
        for(int i=0; i<departments_id.size(); i++) {
            int id = (int)departments_id.get(i);
            departmentService.deleteDepartment(id);
        }
        return Response.Ok();
    }

    @PostMapping("/import")
    @ResponseBody
    public Map batchImport(@RequestParam("file") MultipartFile file) {
        String pathName = "/home/xuranus/Desktop/";//想要存储文件的地址
        String pname = file.getOriginalFilename();//获取文件名（包括后缀）
        pathName += pname;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(pathName);
            fos.write(file.getBytes()); // 写入文件
            System.out.println("文件上传成功");
            if(departmentService.importFromFile(pathName))
                return Response.Ok();
            else
                return Response.Error("解析失败");
        } catch (Exception e) {
            e.printStackTrace();
            return Response.Error("上传失败");
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                return Response.Error("上传失败");
            }
        }
    }

    private Department req2Department(Map req) {
        int id = (int)req.get("id");
        String pinyin = (String)req.get("pinyin");
        String name = (String)req.get("name");
        int classification_id = (int)req.get("classification_id");
        String type = (String)req.get("type");
        return new Department(id,pinyin,name,type,classification_id);
    }

    private boolean canInsert(Department department){
        return !departmentService.exist(department);
    }

    private boolean canUpdate(Department department){
        return departmentService.exist(department);
    }

}
