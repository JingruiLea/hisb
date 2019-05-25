package edu.neu.his.service;

import edu.neu.his.bean.Registration_level;
import edu.neu.his.mapper.RegistrationLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegistrationLevelService {
    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    @Transactional
    public void updateDepartment(Registration_level registration_level) {
        registrationLevelMapper.update(registration_level);
    }

    @Transactional
    public List<Registration_level> findByName(String name) {
        return registrationLevelMapper.findByName(name);
    }

    @Transactional
    public void insertRegistration_level(Registration_level registration_level) {
        registrationLevelMapper.insert(registration_level);
    }

    @Transactional
    public List<Registration_level> findAll() {
        return registrationLevelMapper.findAll();
    }

    @Transactional
    public void deleteRegistration_level(int id) {
        registrationLevelMapper.delete(id);
    }
}
