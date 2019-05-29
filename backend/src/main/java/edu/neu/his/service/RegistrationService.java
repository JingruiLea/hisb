package edu.neu.his.service;

import edu.neu.his.bean.Registration;
import edu.neu.his.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private RegistrationMapper registrationMapper;


    public List<Registration> findAll(){
       return registrationMapper.selectAll();
    }

    public void insert(Registration registration){
        registrationMapper.insert(registration);
    }

    public void deleteByPrimaryKey(int id){
        registrationMapper.deleteByPrimaryKey(id);
    }

    public Registration selectByprimaryKey(int id) {
        return registrationMapper.selectByPrimaryKey(id);
    }
}
