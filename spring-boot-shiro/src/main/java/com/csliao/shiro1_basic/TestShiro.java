package com.csliao.shiro1_basic;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestShiro {

    public static void main(String[] args) {
        
        User zhangsan = new User();
        zhangsan.setName("zhangsan");
        zhangsan.setPassword("123");
        
        User lisi = new User();
        lisi.setName("lisi");
        lisi.setPassword("1234");
        
        User wangwu = new User();
        wangwu.setName("wangwu");
        wangwu.setPassword("wrongpassword");

        List<User> users = new ArrayList();
        users.add(zhangsan);
        users.add(lisi);
        users.add(wangwu);

        String roleAdmin = "admin";
        String roleProductManager = "productManager";
        List<String> roles = new ArrayList();
        roles.add(roleAdmin);
        roles.add(roleProductManager);

        String permitAddProduct = "addProduct";
        String permitAddOrder = "addOrder";
        List<String> permits = new ArrayList();
        permits.add(permitAddProduct);
        permits.add(permitAddOrder);

        User user;
        Iterator var12 = users.iterator();
        while(var12.hasNext()) {
            user = (User)var12.next();
            if (login(user)) {
                System.out.printf("%s \t成功登陆，用的密码是 %s\t %n", user.getName(), user.getPassword());
            } else {
                System.out.printf("%s \t失败，用的密码是 %s\t %n", user.getName(), user.getPassword());
            }
        }

        System.out.println("-------分割线------");

        var12 = users.iterator();
        String permit;
        Iterator var14;
        while(var12.hasNext()) {
            user = (User)var12.next();
            var14 = roles.iterator();

            while(var14.hasNext()) {
                permit = (String)var14.next();
                if (login(user)) {
                    if (hasRole(user, permit)) {
                        System.out.printf("%s\t 拥有角色: %s\t%n", user.getName(), permit);
                    } else {
                        System.out.printf("%s\t 不拥有角色: %s\t%n", user.getName(), permit);
                    }
                }
            }
        }

        System.out.println("-------分割线------");
        var12 = users.iterator();

        while(var12.hasNext()) {
            user = (User)var12.next();

            var14 = permits.iterator();
            while(var14.hasNext()) {
                permit = (String)var14.next();
                if (login(user)) {
                    if (isPermitted(user, permit)) {
                        System.out.printf("%s\t 拥有权限: %s\t%n", user.getName(), permit);
                    } else {
                        System.out.printf("%s\t 不拥有权限: %s\t%n", user.getName(), permit);
                    }
                }
            }
        }

    }

    private static boolean hasRole(User user, String role) {
        Subject subject = getSubject(user);
        return subject.hasRole(role);
    }

    private static boolean isPermitted(User user, String permit) {
        Subject subject = getSubject(user);
        return subject.isPermitted(permit);
    }

    private static Subject getSubject(User user) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro1_basic.ini");
        SecurityManager sm = (SecurityManager)factory.getInstance();
        SecurityUtils.setSecurityManager(sm);
        Subject subject = SecurityUtils.getSubject();
        return subject;
    }

    private static boolean login(User user) {
        Subject subject = getSubject(user);
        if (subject.isAuthenticated()) {
            subject.logout();
        }

        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());

        try {
            subject.login(token);
        } catch (AuthenticationException var4) {
            return false;
        }

        return subject.isAuthenticated();
    }
}
