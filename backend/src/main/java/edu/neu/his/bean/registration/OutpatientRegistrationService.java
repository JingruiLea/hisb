package edu.neu.his.bean.registration;

import edu.neu.his.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutpatientRegistrationService {


    @Autowired
    private OutpatientRegistrationMapper outpatientRegistrationMapper;

    @Transactional
    public List<User> findByDepartmentAndRegistrationLevel(int department_id, String title){
        return outpatientRegistrationMapper.findByDepartmentAndTitle(department_id,title);
    }

    @Transactional
    public int insertRegistration(Registration registration){
        outpatientRegistrationMapper.insert(registration);
        return  registration.getMedical_record_id();
    }

    @Transactional
    public Registration findRegistrationById(int medical_record_id){
        return outpatientRegistrationMapper.findRegistrationById(medical_record_id);
    }

    @Transactional
    public void updateStatus(Registration registration){
       outpatientRegistrationMapper.update(registration);
    }

    @Transactional
    public List<Registration> findByDoctor(int id){
        return outpatientRegistrationMapper.findRegistrationByDoctor(id);
    }

    @Transactional
    public List<Integer> findMedicalRecordIdByName(String name){
        return outpatientRegistrationMapper.findMedicalRecordIdByName(name);
    }
}
