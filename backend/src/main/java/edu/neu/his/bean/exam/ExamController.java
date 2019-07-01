package edu.neu.his.bean.exam;

import edu.neu.his.auto.ExamItemResultMapper;
import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.bean.nondrug.NonDrugChargeItem;
import edu.neu.his.bean.outpatientCharges.OutpatientChargesRecord;
import edu.neu.his.config.Response;
import edu.neu.his.auto.OutpatientChargesRecordMapper;
import edu.neu.his.bean.nondrug.NonDrugChargeService;
import edu.neu.his.util.Common;
import edu.neu.his.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

/**
 * 实现检查/检验/处置管理的相关功能
 *
 */
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

    private String filePath;

    @Autowired
    ExamService examService;

    @Autowired
    ExamItemService examItemService;

    @Autowired
    NonDrugChargeService nonDrugChargeService;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Autowired
    OutpatientChargesRecordMapper outpatientChargesRecordMapper;

    @Autowired
    ExamItemResultService examItemResultMapper;

    /**
     * 根据类型查找全部的非药品项目
     * @param req 检查/检验/处置类型
     * @return 对应的非药品项目
     */
    @PostMapping("/allItems")
    public Map allItemsByType(@RequestBody Map req){
        List res = examService.allItemsByType((Integer) req.get("type"));
        return Response.ok(res);
    }

    /**
     * 作废检查/检验/处置
     * @param req 前端传递的request，包含检查/检验/处置id列表
     * @return 状态码
     */
    @PostMapping("/cancel")
    public Map cancel(@RequestBody Map req){
        List<Integer> ids = (List<Integer>) req.get("id");
        for (Integer id : ids) {
            Exam exam = examService.selectById(id);
            if(exam == null) return Response.error("没有该检查!");
            exam.setStatus(Common.YIZUOFEI);
            examService.updateByPrimaryKey(exam);
        }
        return Response.ok();
    }

    /**
     * 创建检查/检验/处置
     * @param req 前端传递的request，包含“type”,"medical_record_id","non_drug_id_list"字段
     * @return 状态码
     */
    @PostMapping("/create")
    public Map create(@RequestBody Map req){
//         = examService.selectByMedicalRecordIdAndType((int)req.get("medical_record_id"), (int)req.get("type"));
//        if(exam != null){
//            return Response.ok(exam);
//        }
        Exam exam = Utils.fromMap(req, Exam.class);
        if(!medicalRecordService.hasSubmit(exam.getMedical_record_id())){
            return Response.error("病历状态错误!");
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        for (Integer id: nonDrugIdList) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        exam.setCreate_time(Utils.getSystemTime());
        exam.setStatus(Common.ZANCUN);
        examService.insert(exam);
        Integer examId = exam.getId();
        List<Integer> resultList = new ArrayList<>();
        nonDrugIdList.forEach(nonDrugId -> {
            ExamItem examItem = new ExamItem();
            examItem.setExam_id(examId);
            examItem.setNon_drug_item_id(nonDrugId);
            examItem.setStatus(Common.WEIDENGJI);
            examItemService.insert(examItem);
            if(examItem.getId() > 0)
                resultList.add(examItem.getId());
        });
        Map<String, Object> res = new HashMap();
        res.put("id", examId);
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    /**
     * 更新检查/检验/处置
     * @param req 前端传递的request，包含“type”,"medical_record_id","non_drug_id_list"字段
     * @return 状态码
     */
    @PostMapping("/update")
    public Map update(@RequestBody Map req){
        int examId = (int) req.get("id");
        if(examService.selectById(examId)==null){
            return Response.error("没有该检查单!");
        }
        List<Integer> nonDrugIdList = (List<Integer>) req.get("non_drug_id_list");
        for (Integer id: nonDrugIdList) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        examService.deleteAllItemById(examId);
        nonDrugIdList.forEach(nonDrugId -> {
            ExamItem examItem = new ExamItem();
            examItem.setExam_id(examId);
            examItem.setNon_drug_item_id(nonDrugId);
            examItem.setStatus(Common.WEIDENGJI);
            examItemService.insert(examItem);
        });
        return Response.ok();
    }

    public Map addOne(@RequestBody Map map) throws IOException {
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }
        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        for (Integer id: nonDrugIds) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        nonDrugIds.forEach(id->{
            ExamItem examItem = new ExamItem();
            examItem.setStatus(Common.WEIDENGJI);
            examItem.setNon_drug_item_id(id);
            examItem.setExam_id(examId);
            examItemService.insert(examItem);
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    /**
     * 发送检查/检验/处置
     * @param map 前端传递的request，包含检查/检验/处置id
     * @return 状态码
     */
    @PostMapping("/send")
    public Map submit(@RequestBody Map map){
        List<Integer> examIds = (List<Integer>) map.get("id");
        for (Integer examId : examIds) {
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
        }
        return Response.ok();
    }

    /**
     * 删除检查/检验/处置
     * @param req 前端传递的request，包含需要删除的检查/检验/处置id
     * @return 状态码
     */
    @PostMapping("/delete")
    public Map delete(@RequestBody Map req){
        List<Integer> examIds = (List<Integer>) req.get("id");
        for (Integer examId : examIds) {
            if(examService.delete(examId) != 1){
                return Response.error("列表错误!");
            }
        }
        return Response.ok();
    }

    public Map delOne(@RequestBody Map map) {
        int examId = (int)map.get("exam_id");
        Exam exam = examService.selectById(examId);
        if(exam == null){
            return Response.error("找不到该检查/检验/处置单!");
        }
        if(!exam.getStatus().equals(Common.ZANCUN)){
            return Response.error("该检查/检验/处置单状态错误!");
        }

        List<Integer> nonDrugIds = (List<Integer>) map.get("non_drug_id");
        for (Integer id: nonDrugIds) {
            if(!nonDrugChargeService.exist(nonDrugChargeService.selectById(id))){
                return Response.error("列表错误!");
            }
        }
        nonDrugIds.forEach(nonDrugId->{
            ExamItem examItem = examItemService.selectByDetail(nonDrugId, examId);
            examItemService.deleteById(examItem.getId());
        });
        Map<String, Object> res = new HashMap();
        res.put("non_drug_item_id", examService.getNonDrugItemIdListById(exam.getId()));
        return Response.ok(res);
    }

    /**
     * 根据病历号和类型获取全部检查/检验/处置
     * @param req 前端传递的request，包含需要删除的检查/检验/处置的类型和病历号
     * @return 全部检查/检验/处置列表
     */
    @PostMapping("/list")
    public Map list(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        int type = (int)req.get("type");
        List res = examService.listByType(type, medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    /**
     * 根据病历号获取全部检查/检验/处置
     * @param req 前端传递的request，包含需要删除的检查/检验/处置的病历号
     * @return 全部检查/检验/处置列表
     */
    @PostMapping("/allExam")
    public Map allExam(@RequestBody Map req){
        int medicalRecordId = (int) req.get("medical_record_id");
        List res = examService.list(medicalRecordId, Utils.getSystemUser(req));
        return Response.ok(res);
    }

    /**
     * 登记检查/检验/处置
     * @param req 前端传递的request，包含检查/检验/处置id
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
     * 提交检查/检验结果
     * @param req 前端传递的request，检查/检验/处置结果
     * @param files 前端传递的request，文件路径名
     * @return 状态码
     */
    @PostMapping("submitResult")
    public Map submitResult(@RequestParam Map req, @RequestParam("file")MultipartFile[] files){
        int examItemId = Integer.parseInt((String) req.get("exam_item_id"));
        ExamItem examItem = examItemService.selectByPrimaryKey(examItemId);
        if(examItem == null){
            return Response.error("没有该检查!");
        }
        if(!examItem.getStatus().equals(Common.YIDENGJI)){
            return Response.error("该检查尚未登记!");
        }
        ExamItemResult examItemResult = Utils.fromMap(req, ExamItemResult.class);
        String DBFile = "";
        for (int i = 0; i < files.length; i++) {
            long time = new Date().getTime();
            String suffix = Utils.getFileSuffix(files[i].getOriginalFilename());
            String filename = examItemId + time + "." + suffix;
            FileOutputStream fos;
            try {
                String path = filePath + filename;
                File file = new File(path);
                fos = new FileOutputStream(file);
                fos.write(files[i].getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DBFile += files[i].getOriginalFilename() + "&" + filename + ";";
        }
        examItemResult.setFile(DBFile);
        examItemResultMapper.insertOrUpdate(examItemResult);
        examItem.setStatus("已完成");
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
        Map res = Utils.objectToMap(examItemResult);
        String files = examItemResult.getFile();
        String[] filenames = files.split(";");
        List fileList = new ArrayList();
        for (String filename : filenames) {
            String[] nameArr = filename.split("&");
            HashMap fileMap = new HashMap();
            String originName = nameArr[0];
            String saveName = nameArr[1];
            fileMap.put("originName", originName);
            fileMap.put("saveName", saveName);
            fileList.add(fileMap);
        }
        res.put("file", fileList);
        return Response.ok(res);
    }

    /**
     * 获得检查/检验结果文件
     * @param req 前端传递的request，包含文件名
     * @param response
     * @throws IOException 抛出文件IO异常
     */
    @RequestMapping("getResultFile")
    public void getResultFile(@RequestParam Map req, HttpServletResponse response) throws IOException {
        String filename = (String) req.get("filename");
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + filename);
        OutputStream os = response.getOutputStream();
        byte[] buff = new byte[1024];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath + filename));
        int i = bis.read(buff);
        while (i != -1) {
            os.write(buff, 0, buff.length);
            os.flush();
            i = bis.read(buff);
        }
        os.close();
    }
}
