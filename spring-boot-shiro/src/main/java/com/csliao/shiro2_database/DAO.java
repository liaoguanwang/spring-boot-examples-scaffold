package com.csliao.shiro2_database;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DAO {

    public DAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //&serverTimezone=GMT%2B8
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/shiro?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8",
                "root", "123456");
    }

    public String getPassword(String username){
        String sql = "select password from user where name = ?";
        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getString("password");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public Set<String> listRoles(String userName){
        Set<String> roles = new HashSet<>();
        String sql = "select r.name from user u " +
                "left join user_role ur on u.id = ur.uid " +
                "left join role r on r.id = ur.rid " +
                "where u.name = ?";
        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                roles.add(rs.getString(1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roles;
    }

    public Set<String> listPermissions(String userName){
        Set<String> permissions = new HashSet<>();
        String sql = "select p.name from user u " +
                "left join user_role ur on u.id = ur.uid " +
                "left join role r on r.id = ur.rid " +
                "left join role_permission rp on r.id = rp.rid " +
                "left join permission p on p.id = rp.pid " +
                "where u.name = ?";

        try(Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                permissions.add(rs.getString(1));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return permissions;
    }

    public static void main(String[] args) {
        System.out.println(new DAO().listRoles("zhangsan"));
        System.out.println(new DAO().listRoles("lisi"));
        System.out.println(new DAO().listPermissions("zhangsan"));
        System.out.println(new DAO().listPermissions("lisi"));
    }
}
