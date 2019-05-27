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
        return Response.Ok(settlementCategoryService.findAll());
    }

    @PostMapping("/delete")
    @ResponseBody
    public Map deleteSettlementCategory(@RequestBody Map req) {
        List<Map> settlementCategories = (List<Map>) req.get("data");
        settlementCategories.forEach(settlementCategory -> settlementCategoryService.deleteSettlementCategoryByName((String) settlementCategory.get("name")));
        return Response.Ok();
    }

    @PostMapping("/add")
    @ResponseBody
    public Map addAllSettlementCategory(@RequestBody Map req) {
        String name = (String) req.get("name");
        settlementCategoryService.addSettlementCategory(name);
        return Response.Ok();
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateAllSettlementCategory(@RequestBody Map req) {
        String name = (String) req.get("name");
        int id = (int)req.get("id");
        SettlementCategory settlementCategory = new SettlementCategory();
        settlementCategory.setId(id);
        settlementCategory.setName(name);
        settlementCategoryService.update(settlementCategory);
        return Response.Ok();
    }

}
