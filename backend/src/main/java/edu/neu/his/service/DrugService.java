package edu.neu.his.service;

import edu.neu.his.bean.Drug;
import edu.neu.his.mapper.DrugMapper;
import edu.neu.his.util.Importable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugService implements Importable<Drug> {
    @Autowired
    DrugMapper drugMapper;

    @Override
    public void insert(Drug instance) {
        drugMapper.insert(instance);
    }
}
