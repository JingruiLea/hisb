package edu.neu.his.bean.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 实现处理数据库中registration_level表的相关操作
 */
@Service
public class RegistrationLevelService {
    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    /**
     * 更新数据库中的一条相应的挂号等级记录
     * @param registration_level 挂号等级
     */
    @Transactional
    public void updateDepartment(RegistrationLevel registration_level) {
        registrationLevelMapper.update(registration_level);
    }

    /**
     * 从数据库中根据名称找到对应的挂号等级
     * @param name 挂号等级名称
     * @return  根据名称找到的对应挂号等级
     */
    @Transactional
    public RegistrationLevel findByName(String name) {
        return registrationLevelMapper.findByName(name);
    }

    /**
     * 向数据库中插入一条挂号等级记录
     * @param registration_level 要插入数据库中的RegistrationLevel对象
     */
    @Transactional
    public void insertRegistration_level(RegistrationLevel registration_level) {
        registrationLevelMapper.insert(registration_level);
    }

    /**
     * 查找数据库中所有挂号等级的列表
     * @return 返回查找到的所有挂号等级列表
     */
    @Transactional
    public List<RegistrationLevel> findAll() {
        List<RegistrationLevel> levels = registrationLevelMapper.findAll();
        Collections.sort(levels, (o1, o2) -> {
            if(o1.getSeq_num()>o2.getSeq_num()){
                return 1;
            }else if(o1.getSeq_num()<o2.getSeq_num()){
                return -1;
            }else{
                return 0;
            }
        });
        return levels;
    }

    /**
     * 根据id从数据库中删除对应挂号等级
     * @param id 返回查找到的所有挂号等级的列表
     */
    @Transactional
    public void deleteRegistration_level(int id) {
        registrationLevelMapper.delete(id);
    }

    /**
     * 从数据库中根据id找到对应的挂号等级
     * @param id 挂号等级id
     * @return 根据id找到的对应挂号等级
     */
    @Transactional
    public RegistrationLevel findById(int id) {
        return  registrationLevelMapper.findById(id);
    }

    /**
     * 判断该挂号等级的信息能否进行更新
     * @param registrationLevel 挂号等级
     * @return 返回是否能更新，true代表能，false代表不能
     */
    @Transactional
    public boolean canUpdate(RegistrationLevel registrationLevel) {
        int id_num = registrationLevelMapper.checkIdExists(registrationLevel.getId());
        int name_num = registrationLevelMapper.checkNameExists(registrationLevel.getName());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else if(name_num==1){
            RegistrationLevel d = registrationLevelMapper.findByName(registrationLevel.getName());
            if(d.getId() != registrationLevel.getId())
                return false;
            else
                return true;
        }else
            return true;
    }

    /**
     * 判断该挂号等级能否插入数据库中
     * @param registrationLevel 挂号等级
     * @return 返回是否能插入，true代表能，false代表不能
     */
    @Transactional
    public boolean canInsert(RegistrationLevel registrationLevel) {
        int id_num = registrationLevelMapper.checkIdExists(registrationLevel.getId());
        int name_num = registrationLevelMapper.checkNameExists(registrationLevel.getName());
        if(id_num>=1 || name_num>=1)
            return false;
        else
            return true;
    }

    /**
     * 判断该挂号等级的信息能否进行删除
     * @param id 挂号等级id
     * @return 返回是否能删除，true代表能，false代表不能
     */
    @Transactional
    public int canDelete(int id) {
        return registrationLevelMapper.checkIdExists(id);//0,不能删
    }

    /**
     * 查找默认的挂号等级
     * @return 查找到的默认的挂号等级
     */
    @Transactional
    public RegistrationLevel findDefault() {
        List<RegistrationLevel> list = registrationLevelMapper.findDefault();
        if(list.size()!=0)
            return  registrationLevelMapper.findDefault().get(0);
        else
            return registrationLevelMapper.findAll().get(0);
    }
}
