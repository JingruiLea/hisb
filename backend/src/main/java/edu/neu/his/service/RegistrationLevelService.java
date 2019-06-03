package edu.neu.his.service;

import edu.neu.his.bean.RegistrationLevel;
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
    public void updateDepartment(RegistrationLevel registration_level) {
        registrationLevelMapper.update(registration_level);
    }

    @Transactional
    public List<RegistrationLevel> findByName(String name) {
        return registrationLevelMapper.findByName(name);
    }

    @Transactional
    public void insertRegistration_level(RegistrationLevel registration_level) {
        registrationLevelMapper.insert(registration_level);
    }

    @Transactional
    public List<RegistrationLevel> findAll() {
        return registrationLevelMapper.findAll();
    }

    @Transactional
    public void deleteRegistration_level(int id) {
        registrationLevelMapper.delete(id);
    }

    @Transactional
    public RegistrationLevel findById(int id) {
        return  registrationLevelMapper.findById(id);
    }
}
