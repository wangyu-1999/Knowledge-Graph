package com.wangyu.kgbackend.service;

import com.wangyu.kgbackend.dao.UserDAO;
import com.wangyu.kgbackend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
这里实际上是对 UserDAO 进行了二次封装，一般来讲，
我们在 DAO 中只定义基础的增删改查操作，
而具体的操作，需要由 Service 来完成。
 * @author wongy
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public boolean isExist(String username) {
        User user = findByUserName(username);
        return null!=user;
    }

    public User findByUserName(String username) {
        return userDAO.findByUsername(username);
    }

    public User get(String username, String password){
        return userDAO.findByUsernameAndPassword(username, password);
    }

    public void addOrUpdate(User user) {
        userDAO.save(user);
    }

    public List<User> listUsers(){
        return userDAO.list();
    }
}
