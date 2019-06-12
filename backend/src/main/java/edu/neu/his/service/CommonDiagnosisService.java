package edu.neu.his.service;

import edu.neu.his.mapper.auto.CommonDiagnosisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonDiagnosisService {
    @Autowired
    private CommonDiagnosisMapper commonDiagnosisMapper;
}
