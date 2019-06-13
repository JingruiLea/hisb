package edu.neu.his.bean.exam;

import edu.neu.his.bean.medicalRecord.MedicalRecordService;
import edu.neu.his.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {
    @Autowired
    ExamMapper examMapper;

    @Autowired
    ExamItemMapper examItemMapper;

    @Autowired
    MedicalRecordService medicalRecordService;

    @Transactional
    public int insertItem(ExamItem item){
        return examItemMapper.insert(item);
    }

    @Transactional
    public boolean medicalRecordHasSubmit(Exam exam){
        String medicalRecordStatus = medicalRecordService.getStatusById(exam.getMedical_record_id());
        if(!Common.YITIJIAO.equals(medicalRecordStatus)){
            return false;
        }
        return true;
    }

    @Transactional
    public int insert(Exam exam){
        return examMapper.insert(exam);
    }

    @Transactional
    public List<Integer> getNonDrugItemIdListById(Integer id){
        List<ExamItem> itemList = examItemMapper.selectByExamId(id);
        return itemList.stream().map(ExamItem::getNon_drug_item_id).collect(Collectors.toList());
    }

    @Transactional
    public Exam selectById(Integer id){
        return examMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public int updateByPrimaryKey(Exam record){
        return examMapper.updateByPrimaryKey(record);
    }

    public Exam selectByMedicalRecordIdAndType(int medicalRecordId, int type){
        List<Exam> list = examMapper.selectAll();
        for(Exam exam:list){
            if(exam.getMedical_record_id() == medicalRecordId && exam.getType()==type){
                return exam;
            }
        }
        return null;
    }

}
