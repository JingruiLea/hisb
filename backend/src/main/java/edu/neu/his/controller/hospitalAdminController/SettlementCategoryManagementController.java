package edu.neu.his.controller.hospitalAdminController;

import edu.neu.his.bean.SettlementCategory;
import edu.neu.his.config.Response;
import edu.neu.his.service.SettlementCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/settlementCategoryManagement")
public class SettlementCategoryManagementController {
    @Autowired
    SettlementCategoryService settlementCategoryService;

    @GetMapping("/all")
    @ResponseBody
    public Map getAllSettlementCategory() {
        return Response.ok(settlementCategoryService.findAll());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteSettlementCategory(@RequestBody Map req) {
        List<Integer> settlementCategoryIds = (List<Integer>) req.get("data");
        settlementCategoryIds.forEach(id -> settlementCategoryService.deleteSettlementCategoryById(id));
        return Response.ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addAllSettlementCategory(@RequestBody Map req) {
        String name = (String) req.get("name");
        int id = (int) req.get("id");
        settlementCategoryService.addSettlementCategory(new SettlementCategory(id,name));
        return Response.ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateAllSettlementCategory(@RequestBody Map req) {
        String name = (String) req.get("name");
        int id = (int)req.get("id");
        SettlementCategory settlementCategory = new SettlementCategory(id,name);
        settlementCategoryService.update(settlementCategory);
        return Response.ok();
    }

}
