package edu.neu.his.service;

import edu.neu.his.bean.Registration;
import edu.neu.his.mapper.MedicalRecordMapper;
import edu.neu.his.mapper.OutpatientRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {
    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Autowired
    private MedicalRecordMapper medicalRecordMapper;

    @Transactional
    public List<Registration> findHistory(String type, String number){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumber(number);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumber(number);
    }

    @Transactional
    public List<Registration> find(String type, String number, String status){
        if(type.equals("id"))
            return outpatientRegistrationMapper.findRegistrationByIdNumberAndStatus(number, status);
        else
            return outpatientRegistrationMapper.findRegistrationByMedicalCertificateNumberAndStatus(number, status);
    }

    @Transactional
    public String getStatusById(Integer id){
        return medicalRecordMapper.selectByPrimaryKey(id).getStatus();
    }

}
