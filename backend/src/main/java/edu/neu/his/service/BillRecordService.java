package edu.neu.his.service;

import edu.neu.his.bean.BillRecord;
import edu.neu.his.mapper.BillRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BillRecordService {
    @Autowired
    private BillRecordMapper billRecordMapper;

    @Transactional
    public int insertBillRecord(BillRecord billRecord) {
        billRecordMapper.insert(billRecord);
        return billRecord.getId();
    }

    @Transactional
    public BillRecord findById(int id) {
        return billRecordMapper.find(id);
    }

}
