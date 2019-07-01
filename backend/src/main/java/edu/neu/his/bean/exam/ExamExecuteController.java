package edu.neu.his.bean.exam;

import edu.neu.his.bean.registration.OutpatientRegistrationMapper;
import edu.neu.his.bean.registration.Registration;
import edu.neu.his.config.Response;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 实现录入检查/检验/处置的相关功能
 *
 */
@RestController
@RequestMapping("/examExcute")
public class ExamExecuteController {

    @Autowired
    ExamItemService examItemService;

    @Autowired
    ExamService examService;


    @Autowired
    OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired ExamItemResultService examItemResultMapper;

    /**
     * 根据姓名/身份证/医保/病例号获取挂号列表
     * @param req 前端传递的request，包含类型（姓名/身份证/医保/病例号）和它的值
     * @return 对应的挂号列表
     */
    @RequestMapping("/searchRegistration")
    public Map searchRegistration(@RequestBody Map req){
        int type = (int) req.get("type");
        String input = (String) req.get("input");
        List res = new ArrayList();
        Registration registration = null;
        switch (type){
            case 0:
                res = outpatientRegistrationMapper.findMedicalRecordLikeName(input);
                break;
            case 1:
                res = outpatientRegistrationMapper.findRegistrationByIdNumber(input);
                break;
            case 2:
                res = outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(input);
                break;
            case 3:
                registration = outpatientRegistrationMapper.findRegistrationById(Integer.parseInt(input));
                break;
        }
        if(registration != null){
            res.add(registration);
        }
        return Response.ok(res);
    }

    /**
     * 登记检查/检验
     * @param req 前端传递的request，为检查/检验id
     * @return 状态码
     */
    @PostMapping("register")
    public Map register(@RequestBody Map req){
        List<Integer> examItemIds = (List<Integer>) req.get("exam_item_id");
        if(examItemService.register(examItemIds)){
            return Response.ok();
        }else {
            return Response.error("列表错误!");
        }
    }

    /**
     * 获取全部检查/检验
     * @param req 前端传递的request，为病历号
     * @return 全部检查/检验列表
     */
    @PostMapping("/allExam")
    public Map allExam(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        List res = examService.list(medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    /**
     * 提交检查/检验结果
     * @param req 前端传递的request，检查/检验结果
     * @return 状态码
     */
    @PostMapping("submitResult")
    public Map submitResult(@RequestParam Map req){
        int examItemId = (int) req.get("exam_item_id");
        ExamItem examItem = examItemService.selectByPrimaryKey(examItemId);
        if(examItem == null){
            return Response.error("没有该检查!");
        }
        if(!examItem.getStatus().equals(Common.YIDENGJI)){
            return Response.error("该检查尚未登记!");
        }
        ExamItemResult examItemResult = Utils.fromMap(req, ExamItemResult.class);
        String DBFile = "";
        List<Map> images = (List<Map>) req.get("images");
        for (Map image : images) {
            String file = (String) image.get("name");
            file += "&";
            file += (String) image.get("url");
            file += ";";
            DBFile += file;
        }
        examItemResult.setFile(DBFile);
        examItemResultMapper.insertOrUpdate(examItemResult);
        examItem.setStatus(Common.YIWANCHENG);
        examItemService.update(examItem);
        return Response.ok();
    }

    /**
     * 获得检查/检验结果
     * @param req 前端传递的request，包含检查/检验id
     * @return 对应的检查/检验结果
     */
    @PostMapping("getResult")
    public Map getResult(@RequestBody Map req){
        int examItemId = (int)req.get("exam_item_id");
        ExamItemResult examItemResult = examItemResultMapper.selectByExamItemId(examItemId);
        if(examItemResult == null)return Response.error("该检查结果尚未录入!");
        Map res = Utils.objectToMap(examItemResult);
        String files = examItemResult.getFile();
        String[] filenames = files.split(";");
        List fileList = new ArrayList();
        for (String filename : filenames) {
            String[] nameArr = filename.split("&");
            HashMap fileMap = new HashMap();
            String name = nameArr[0];
            String url = nameArr[1];
            fileMap.put("name", name);
            fileMap.put("url", url);
            fileList.add(fileMap);
        }
        res.put("images", fileList);
        return Response.ok(res);
    }
}
