package edu.neu.his.bean.drug;

import edu.neu.his.config.Response;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drugInfoManagement")
public class DrugManageController {
    @Autowired
    DrugService drugService;

    @PostMapping("/add")
    @ResponseBody
    public Map add(@RequestBody Map req){
        Drug drug = Utils.fromMap(req,Drug.class);
        if(!drugService.existDrug(drug.getId())) {
            drugService.insertDrug(drug);
            return Response.ok();
        }else
            return Response.error("错误，该ID已存在");
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map delete(@RequestBody Map req){
        List ids = (List)req.get("data");
        ids.forEach(id->{
            if(drugService.existDrug((int)id))
                drugService.deleteDrug((int)id);
        });
        return Response.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map update(@RequestBody Map req) {
        Drug drug = Utils.fromMap(req, Drug.class);
        if(drugService.existDrug(drug.getId())) {
            drugService.updateDrug(drug);
            return Response.ok();
        }else
            return Response.error("错误，该药品不存在");
    }

    @PostMapping("/all")
    @ResponseBody
    public Map findAll(){
        return Response.ok(drugService.selectAllDrug());
    }

    /*
    @PostMapping("/getDrugInfoByName")
    @ResponseBody
    public Map getDrugInfoByName(@RequestBody Map req){
        String name = (String)req.get("input");
        return Response.ok(drugService.selectDrugByName(name));
    }
    */

    @PostMapping("/getDrugByPage")
    @ResponseBody
    public Map getDrugByPage(@RequestBody Map req){
        int pageNo = (int)req.get("page");
        int pageSize = (int)req.get("limit");
        return Response.ok(drugService.selectDrugByPage(pageNo,pageSize));
    }

    @PostMapping("/getDrugSize")
    @ResponseBody
    public Map getDrugSize(){
        return Response.ok(drugService.findSize());
    }

    @PostMapping("/search")
    @ResponseBody
    public Map search(@RequestBody Map req){
        int id = (int)req.get("id");
        String code = (String)req.get("code");
        String name = (String)req.get("name");
        String type = (String)req.get("type");
        List<Drug> list = drugService.search(id,code,name,type);
        return Response.ok(list);
    }
}
