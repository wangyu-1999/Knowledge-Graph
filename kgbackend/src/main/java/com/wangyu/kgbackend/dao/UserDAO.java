package com.wangyu.kgbackend.dao;

import com.wangyu.kgbackend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wongy
 */
public interface UserDAO extends JpaRepository<User,Integer> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 对应的User对象
     */
    User findByUsername(String username);

    /**
     * 通过用户名和密码查找对应的对象
     * @param username 用户名
     * @param password 密码
     * @return 对应的User对象
     */
    User findByUsernameAndPassword(String username,String password);

    /**
     * 列出所有的表项
     * @return 用户密码表
     */
    @Query(value = "select new User(u.id,u.username) from User u")
    List<User> list();
}