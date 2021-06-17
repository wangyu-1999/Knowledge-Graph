package com.wangyu.kgbackend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * @author wongy
 * 用户信息
 */
@Entity
@Table(name = "t_user")
/*
因为是做前后端分离，而前后端数据交互用的是 json 格式。
那么 User 对象就会被转换为 json 数据。
而本项目使用 jpa 来做实体类的持久化，jpa 默认会使用 hibernate,
在 jpa 工作过程中，就会创造代理类来继承 User ，
并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性，
所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。
 */
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})

public class User {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    int id;
    /**
     * 用户名
     */
    @Column(name = "username",nullable = false)
    String username;
    /**
     * 加密后密码
     */
    @Column(name = "password",nullable = false)
    String password;
    /**
     * 加密的盐值
     */
    @Column(name = "salt")
    String salt;

    /**
     * 默认构造函数
     */
    public User() {}

    /**
     * 用于配合自定义查询的构造函数
     */
    public User(int id,String username) {
        this.id = id;
        this.username = username;
    }

    public String getSalt() { return salt; }

    public void setSalt(String salt) { this.salt = salt; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}