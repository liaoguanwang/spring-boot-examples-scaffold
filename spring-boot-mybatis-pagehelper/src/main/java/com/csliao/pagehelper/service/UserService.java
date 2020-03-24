package com.csliao.pagehelper.service;

import com.csliao.pagehelper.model.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Classname UserService
 * @Description
 * @Date 2020/3/24 22:08
 * @Created by csliao
 */


public interface UserService {
    /**
     * 查询所有用户
     * @return
     */
    List<User> selectAllUser();

    List<User> findAllUserByPage(int pageNum, int pageSize);

    PageInfo<User> findAllUserByPageP(int pageNum, int pageSize);

}
