package edu.neu.his.bean.user;

import edu.neu.his.bean.registration.RegistrationLevel;
import edu.neu.his.bean.registration.RegistrationLevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实现处理数据库中user表的相关操作
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RegistrationLevelMapper registrationLevelMapper;

    /**
     * 向数据库中插入一条用户记录
     * @param user 要插入数据库中的User对象
     */
    @Transactional
    public void addUser(User user) {
        userMapper.addUser(user);
        int new_id = userMapper.getUidByUserName(user.getUsername());
        user.setUid(new_id);
        userMapper.addUserInfo(user);
    }

    /**
     * 根据id从数据库中删除对应用户
     * @param id 要删除的用户的id
     */
    @Transactional
    public void deleteUser(int id) {
        userMapper.deleteUserInfo(id);
        userMapper.deleteUser(id);
    }

    /**
     *  更新数据库中的一条相应的用户记录
     * @param user 要进行更新的User对象
     */
    @Transactional
    public void updateUser(User user) {
        userMapper.updateUserInfo(user);
        userMapper.updateUser(user);
    }

    /**
     * 查找数据库中所有用户的列表
     * @return 返回查找到的所有用户列表
     */
    @Transactional
    public List<User> allUsers() {
        return userMapper.findAll();
    }

    public List selectDoctorList(int departmentId, int registrationLevelId) {
        RegistrationLevel registration_level = registrationLevelMapper.find(departmentId);
        if(registration_level.getName().equals("专家号")){
           return userMapper.selectDoctorList(departmentId, "主治医生");
        }
        return userMapper.selectDoctorList(departmentId, "");
    }

    /**
     * 查找数据库中所有角色的列表
     * @return 返回查找到的所有角色列表
     */
    @Transactional
    public List<Role> allRoles() {return userMapper.allRoles();}

    /**
     * 从数据库中根据ID找到对应的用户
     * @param uid 用户ID
     * @return 根据ID找到的对应用户
     */
    @Transactional
    public User findByUid(int uid) {
        return userMapper.find(uid);
    }

    /**
     * 判断该用户能否插入数据库中
     * @param user 要插入数据库的User对象
     * @return 返回是否能插入，true代表能，false代表不能
     */
    @Transactional
    public boolean canInsert(User user) {
        int id_num = userMapper.checkIdExists(user.getUid());
        int name_num = userMapper.checkNameExists(user.getUsername());
        if(id_num>=1|| name_num>=1)
            return false;
        else
            return true;
    }

    /**
     * 判断该用户的信息能否进行更新
     * @param user 要更新的User对象
     * @return 返回是否能更新，true代表能，false代表不能
     */
    @Transactional
    public boolean canUpdate(User user) {
        int id_num = userMapper.checkIdExists(user.getUid());
        int name_num = userMapper.checkNameExists(user.getUsername());
        if(id_num==0 || name_num>1 || id_num>1)
            return false;
        else
            return true;
    }

    /**
     * 判断该用户的信息能否进行删除
     * @param id 要删除的User对象
     * @return 返回是否能删除，true代表能，false代表不能
     */
    @Transactional
    public int canDelete(int id) {
        return userMapper.checkIdExists(id);//0,不能删
    }
}
