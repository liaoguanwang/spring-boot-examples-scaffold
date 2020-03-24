package com.csliao.pagehelper.controller;

import com.csliao.pagehelper.model.User;
import com.csliao.pagehelper.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname UserController
 * @Description
 * @Date 2020/3/24 21:55
 * @Created by csliao
 */

@RestController
@RequestMapping("page")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("users")
    public List<User> users(){
        List<User> users = userService.selectAllUser();
        return users;
    }

    @GetMapping("pagehelper")
    public List<User> pageHelper(){
        List<User> users = userService.findAllUserByPage(1, 3);
        return users;
    }

    @GetMapping("pageinfo")
    public PageInfo<User> pageInfo(){
        PageInfo<User> pageInfo = userService.findAllUserByPageP(1, 3);
        return pageInfo;
    }


}
