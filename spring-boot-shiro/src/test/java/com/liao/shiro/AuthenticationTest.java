package com.liao.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


public class AuthenticationTest {

    /**
     * 1.用户的登录和退出
     */
    @Test
    public void testLoginAndLogOut(){

        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //创建SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //将secutiryManager设置当前的运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        //从SecurityUtils里边创建一个subject
        Subject subject = SecurityUtils.getSubject();

        //在认证提交前准备token（令牌）
        //模拟用户输入的账号和密码，将来是由用户输入进去从页面传送过来
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "111");

        try {
            //执行认证提交
            subject.login(token);
        } catch (AuthenticationException e) {
            //认证失败
            e.printStackTrace();
        }

        //是否认证通过
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);

        subject.logout();
    }


    /**
     * 2.
     */
    @Test
    public void testCustomRealm(){

        //创建securityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro1_basic.ini");

        //创建SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //将securityManager设置到当前的运行环境中
        SecurityUtils.setSecurityManager(securityManager);

        //从SecurityUtils里边创建一个
        Subject subject = SecurityUtils.getSubject();

        //在认证提交前准备token（令牌）
        //模拟用户输入的账号和密码，将来是由用户输入进去从页面传送过来
        UsernamePasswordToken token = new UsernamePasswordToken("zhangsan", "111");

        try {
            //执行认证提交
            subject.login(token);
        } catch (AuthenticationException e) {
            //认证失败
            e.printStackTrace();
        }

        //是否认证通过
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);

    }

}
