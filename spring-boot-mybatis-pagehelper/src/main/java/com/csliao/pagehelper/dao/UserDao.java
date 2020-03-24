package com.csliao.pagehelper.dao;

import com.csliao.pagehelper.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Classname UserDao
 * @Description
 * @Date 2020/3/24 22:07
 * @Created by csliao
 */

@Mapper
public interface UserDao {

    List<User> selectUsers();

}
