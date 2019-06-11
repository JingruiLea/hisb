package edu.neu.his.controller.outpatientMedicalRecordController;

import edu.neu.his.bean.Exam;
import edu.neu.his.bean.ExamItem;
import edu.neu.his.bean.NonDrugChargeItem;
import edu.neu.his.bean.OutpatientChargesRecord;
import edu.neu.his.config.Response;
import edu.neu.his.mapper.auto.OutpatientChargesRecordMapper;
import edu.neu.his.service.ExamItemService;
import edu.neu.his.service.ExamService;
import edu.neu.his.service.NonDrugChargeService;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exam")
public class ExamController {
    /*
    drop table if exists exam;
    create table exam
    (
        id                int not null auto_increment primary key,
        medical_record_id int not null references medical_record (id),
        type              int not null, # 0 检查 1 检验 2 处置
        create_time       varchar(50),
        user_id           int,
        status            varchar(50)   # 暂存, 已作废, 已提交
    );
     */

    @Autowired
    ExamService examService;

    @Autowired
    ExamItemService examItemService;

    @Autowired
    NonDrugChargeService nonDrugChargeService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @PostMapping("/create")
    public Map create(@RequestBody Map req){
        Exam exam;
        exam = Utils.fromMap(req, Exam.class);
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        exam.setCreate_time(Utils.getSystemTime());
        exam.setStatus(Common.ZANCUN);
        Integer examId = examService.insert(exam);
        List<Integer> resultList = new ArrayList<>();
        nonDrugIdList.forEach(nonDrugId -> {
            ExamItem examItem = new ExamItem();
            examItem.setExam_id(examId);
            examItem.setNon_drug_item_id(nonDrugId);
            examItem.setStatus(Common.WEIDENGJI);
            int itemId = examItemService.insert(examItem);
            if(itemId > 0)
                resultList.add(itemId);
        });
        Map<String, Object> res = new HashMap();
        res.put("id", examId);
        res.put("non_drug_item_id", resultList);
        return Response.ok(res);
    }

    @PostMapping("/addOne")
    public Map addOne(@RequestBody Map map) throws IOException {
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }
        ExamItem examItem = Utils.fromMap(map, ExamItem.class);
        examItem.setStatus(Common.WEIDENGJI);
        examItemService.insert(examItem);
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    @PostMapping("/submit")
    public Map submit(@RequestBody Map map){
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }
        exam.setStatus(Common.YITIJIAO);
        examService.updateByPrimaryKey(exam);
        List<Integer> nonDrugIdList = examService.getNonDrugItemIdListById(examId);
        nonDrugIdList.forEach(itemId->{
            NonDrugChargeItem nonDrugChargeItem = nonDrugChargeService.selectById(itemId);
            OutpatientChargesRecord record = new OutpatientChargesRecord();
            record.setCreate_time(Utils.getSystemTime());
            record.setMedical_record_id(exam.getMedical_record_id());
            record.setBill_record_id(0);
            record.setItem_id(itemId);
            record.setType(Common.RECORD_TYPE_JIANCHA);
            record.setExpense_classification_id(nonDrugChargeItem.getExpense_classification_id());
            record.setStatus(Common.WEIJIAOFEI);
            record.setQuantity(1);
            record.setCost(nonDrugChargeItem.getFee());
            record.setCollect_time("");
            record.setExecute_department_id(Utils.getSystemUser(map).getDepartment_id());
            record.setCreate_time(Utils.getSystemTime());
            record.setCollect_time("");
            record.setReturn_time("");
            record.setCreate_user_id(Utils.getSystemUser(map).getUid());
            record.setCollect_user_id(0);
            record.setReturn_user_id(0);
            outpatientChargesRecordMapper.insert(record);
        });
        return Response.ok();
    }

    @PostMapping("/delOne")
    public Map delOne(@RequestBody Map map) {
        int examId = (int)map.get("exam_id");
        int nonDrugId = (int)map.get("non_drug_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }
        ExamItem examItem = examItemService.selectByDetial(nonDrugId, examId);
        if(examItem == null){
            return Response.error("没有该检查!");
        }
        examItemService.deleteById(examItem.getId());
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

}
