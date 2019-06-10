package edu.neu.his.controller.registeredTollCollectorController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.his.bean.BillRecord;
import edu.neu.his.bean.OperateLog;
import edu.neu.his.bean.OutpatientChargesRecord;
import edu.neu.his.bean.Registration;
import edu.neu.his.config.*;
import edu.neu.his.service.BillRecordService;
import edu.neu.his.service.ChargeAndRefundService;
import edu.neu.his.service.OperateLogService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/TollCollector")
public class ChargeAndRefundController {
    @Autowired
    private ChargeAndRefundService chargeAndRefundService;

    @Autowired
    private BillRecordService billRecordService;

    @Autowired
    private OperateLogService operateLogService;

    @GetMapping("/info")
    @ResponseBody
    public Map info(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        return Response.Ok(chargeAndRefundService.findByMedicalRecordId(medical_record_id));
    }

    @GetMapping("/historyInfo")
    @ResponseBody
    public Map historyInfo(@RequestBody Map req){
        int medical_record_id = (int)req.get("medical_record_id");
        String start_time = (String)req.get("start_time");
        String end_time = (String)req.get("end_time");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String create_time = df.format(new Date());
        if(start_time.compareTo(end_time)>=0 || end_time.compareTo(create_time)>0)
            return Response.Error("错误，开始时间不小于结束时间或结束时间大于当前时间");

        return Response.Ok(chargeAndRefundService.findByMedicalRecordIdAndTime(medical_record_id,start_time,end_time));
    }

    @PostMapping("/collect")
    @ResponseBody
    public Map collect(@RequestBody Map req) throws IOException {
        OperateStatus.initOperateMap();
        int uid = Auth.uid(req);

        Map data = new HashMap();
        List<Integer> IDsNotHave = new ArrayList<>();
        List<Integer> IDsHaveCharged = new ArrayList<>();
        List<OutpatientChargesRecord> recordsToCharge = new ArrayList<>();
        float cost = 0;
        String type = BillRecordStatus.Charge;
        int medical_record_id = (int)req.get("medical_record_id");

        List id_list = (List)req.get("charges_id_list");

        for(int i=0; i<id_list.size(); i++){
            Integer id = (Integer)id_list.get(i);
            OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundService.findByMedicalRecordIdAndId(medical_record_id,id);
            if(outpatientChargesRecord==null){
                IDsNotHave.add(id);
            }else if(!outpatientChargesRecord.getStatus().equals(OutpatientChargesRecordStatus.ToCharge)){
                IDsHaveCharged.add(id);
            }else {
                recordsToCharge.add(outpatientChargesRecord);
                cost += outpatientChargesRecord.getCost();
            }
        }

        if(!IDsHaveCharged.isEmpty() || !IDsNotHave.isEmpty()){
            data.put("ids_not_have",IDsNotHave);
            data.put("ids_have_charged_or_refunded",IDsHaveCharged);
            return Response.Error(data);
        }else {
            //生成票据
            ObjectMapper billRecordMapper = new ObjectMapper();
            billRecordMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String json = JSONObject.fromObject(req).toString();
            BillRecord billRecord = billRecordMapper.readValue(json, BillRecord.class);
            billRecord.setType(type);
            billRecord.setCost(cost);
            billRecord.setMedical_record_id(medical_record_id);
            billRecord.setUser_id(uid);

            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //修改收费记录
            recordsToCharge.forEach(outpatientChargesRecord -> {
                outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Charged);
                outpatientChargesRecord.setBill_record_id(bill_record_id);
                chargeAndRefundService.update(outpatientChargesRecord);

                //对每条收费记录生成对应的操作记录
                String operateType;
                if(outpatientChargesRecord.getType()==0)
                    operateType = OperateStatus.PrescribeMedicine;
                else {
                    int expenseClassificationId = outpatientChargesRecord.getExpense_classification_id();
                    operateType = OperateStatus.operateMap.get(expenseClassificationId);
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String create_time = df.format(new Date());
                OperateLog operateLog = new OperateLog(uid,medical_record_id,operateType,bill_record_id,outpatientChargesRecord.getCost(),create_time);
                operateLogService.insertOperateLog(operateLog);
            });

            return Response.Ok();
        }
    }

    @PostMapping("/return")
    @ResponseBody
    public Map refund(@RequestBody Map req){
        OperateStatus.initOperateMap();
        int uid = Auth.uid(req);

        Map data = new HashMap();
        List<Integer> IDsNotHave = new ArrayList<>();
        List<Integer> IDsNotCharged = new ArrayList<>();
        List<OutpatientChargesRecord> recordsToRefund = new ArrayList<>();
        float cost = 0;
        String type = BillRecordStatus.Refund;
        int medical_record_id = (int)req.get("medical_record_id");

        List id_list = (List)req.get("charges_id_list");

        for(int i=0; i<id_list.size(); i++){
            Integer id = (Integer)id_list.get(i);
            OutpatientChargesRecord outpatientChargesRecord = chargeAndRefundService.findByMedicalRecordIdAndId(medical_record_id,id);
            if(outpatientChargesRecord==null){
                IDsNotHave.add(id);
            }else if(!outpatientChargesRecord.getStatus().equals(OutpatientChargesRecordStatus.Charged)){
                IDsNotCharged.add(id);
            }else {
                recordsToRefund.add(outpatientChargesRecord);
                cost -= outpatientChargesRecord.getCost();
            }
        }

        if(!IDsNotCharged.isEmpty() || !IDsNotHave.isEmpty()){
            data.put("ids_not_have",IDsNotHave);
            data.put("ids_not_charged_or_have_refunded",IDsNotCharged);
            return Response.Error(data);
        }else {
            //生成票据
            BillRecord billRecord = new BillRecord();
            billRecord.setCost(cost);
            billRecord.setType(type);
            billRecord.setMedical_record_id(medical_record_id);
            billRecord.setUser_id(uid);
            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //修改收费记录
            recordsToRefund.forEach(outpatientChargesRecord -> {
                outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Refunded);
                outpatientChargesRecord.setBill_record_id(bill_record_id);
                chargeAndRefundService.update(outpatientChargesRecord);

                //对每条收费记录生成对应的操作记录
                String operateType;
                if(outpatientChargesRecord.getType()==0)
                    operateType = OperateStatus.PrescribeMedicine;
                else {
                    int expenseClassificationId = outpatientChargesRecord.getExpense_classification_id();
                    operateType = OperateStatus.operateMap.get(expenseClassificationId);
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String create_time = df.format(new Date());
                OperateLog operateLog = new OperateLog(uid,medical_record_id,operateType,bill_record_id,0-outpatientChargesRecord.getCost(),create_time);
                operateLogService.insertOperateLog(operateLog);
            });

            return Response.Ok();
        }
    }


}
