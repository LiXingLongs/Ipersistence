package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    User findByCondition(User user);

    List<User> findALl();

    void insert(User user);

    void update(User user);

    void delete(User user);
}
