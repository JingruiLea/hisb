package edu.neu.his.bean.billRecord;

import edu.neu.his.bean.billRecord.BillRecord;
import edu.neu.his.bean.billRecord.BillRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<BillRecord> findByUserIdAndTime(int user_id, String start_time, String end_time) {
        return billRecordMapper.findByUserIdAndTime(user_id,start_time,end_time);
    }
}
