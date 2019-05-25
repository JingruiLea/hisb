package edu.neu.his.service;

import edu.neu.his.bean.User;
import edu.neu.his.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void addUser(User user) {
        userMapper.addUser(user);
        int new_id = userMapper.getUidByUserName(user.getUsername());
        user.setUid(new_id);
        userMapper.addUserInfo(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userMapper.deleteUserInfo(id);
        userMapper.deleteUser(id);
    }

    @Transactional
    public void updateUser(User user) {
        userMapper.updateUserInfo(user);
        userMapper.updateUser(user);
    }

    @Transactional
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
