package edu.neu.his.controller.registeredTollCollectorController;

import edu.neu.his.bean.BillRecord;
import edu.neu.his.bean.OperateLog;
import edu.neu.his.bean.OutpatientChargesRecord;
import edu.neu.his.config.BillRecordStatus;
import edu.neu.his.config.OperateStatus;
import edu.neu.his.config.OutpatientChargesRecordStatus;
import edu.neu.his.config.Response;
import edu.neu.his.service.BillRecordService;
import edu.neu.his.service.ChargeAndRefundService;
import edu.neu.his.service.ExpenseClassificationService;
import edu.neu.his.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/collect")
    @ResponseBody
    public Map collect(@RequestBody Map req){
        OperateStatus.initOperateMap();

        Map data = new HashMap();
        List<Integer> IDsNotHave = new ArrayList<>();
        List<Integer> IDsHaveCharged = new ArrayList<>();
        List<OutpatientChargesRecord> recordsToCharge = new ArrayList<>();
        float cost = 0;
        String type = BillRecordStatus.Charge;
        int medical_record_id = (int)req.get("medical_record_id");

        String str = (String)req.get("charges_id_list");
        int beginIndex = str.indexOf('[') == 0 ? 1 : 0;
        int endIndex = str.lastIndexOf(']') + 1 == str.length() ? str.lastIndexOf(']') : str.length();
        str = str.substring(beginIndex, endIndex);
        String[] id_list = str.split(",");

        for(int i=0; i<id_list.length; i++){
            int id = Integer.parseInt(id_list[i]);
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
            data.put("ids_have_charged",IDsHaveCharged);
            return Response.Error(data);
        }else {
            //生成票据
            BillRecord billRecord = reqToBillRecord(req,type,cost);
            int bill_record_id = billRecordService.insertBillRecord(billRecord);

            //修改收费记录
            recordsToCharge.forEach(outpatientChargesRecord -> {
                outpatientChargesRecord.setStatus(OutpatientChargesRecordStatus.Charged);
                outpatientChargesRecord.setBill_record_id(bill_record_id);
                chargeAndRefundService.update(outpatientChargesRecord);

                //对每条收费记录生成对应的操作记录
                String operateType;
                if(outpatientChargesRecord.getType().equals("药品"))
                    operateType = OperateStatus.PrescribeMedicine;
                else {
                    int expenseClassificationId = outpatientChargesRecord.getExpense_classification_id();
                    operateType = OperateStatus.operateMap.get(expenseClassificationId);
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String create_time = df.format(new Date());
                OperateLog operateLog = new OperateLog((int)req.get("_uid"),medical_record_id,operateType,bill_record_id,outpatientChargesRecord.getCost(),create_time);
                operateLogService.insertOperateLog(operateLog);
            });

            return Response.Ok();
        }
    }

    private BillRecord reqToBillRecord(Map req,String type,float fee){
        BillRecord billRecord = new BillRecord();
        billRecord.setTruely_pay((float)req.get("truely_pay"));
        billRecord.setShould_pay((float)req.get("should_pay"));
        billRecord.setRetail_fee((float)req.get("retail_fee"));
        billRecord.setUser_id((int)req.get("_uid"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        billRecord.setCreat_time(df.format(new Date()));
        billRecord.setMedical_record_id((int)req.get("medical_record_id"));
        billRecord.setType(type);
        billRecord.setCost(fee);

        return billRecord;
    }
}
