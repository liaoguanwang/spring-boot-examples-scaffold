package com.csliao.pagehelper.service;

import com.csliao.pagehelper.dao.UserDao;
import com.csliao.pagehelper.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname UserServiceImpl
 * @Description
 * @Date 2020/3/24 22:08
 * @Created by csliao
 */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public List<User> selectAllUser() {
        return userDao.selectUsers();
    }

    @Override
    public List<User> findAllUserByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectUsers();
        return users;
    }

    @Override
    public PageInfo<User> findAllUserByPageP(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userDao.selectUsers();
        PageInfo<User> pageInfo = new PageInfo<>(users);
        return pageInfo;
    }
}
