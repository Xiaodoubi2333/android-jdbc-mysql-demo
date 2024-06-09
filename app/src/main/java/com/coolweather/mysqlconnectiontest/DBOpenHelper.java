package com.coolweather.mysqlconnectiontest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {
    private static String diver = "com.mysql.jdbc.Driver";
    //加入utf-8是为了后面往表中输入中文，表中不会出现乱码的情况
    private static String url = "jdbc:mysql://192.168.48.1:3306/test?characterEncoding=utf-8";
    private static String user = "root";//用户名
    private static String password = "123456";//密码

    /*     修改3：特殊权限组：任何device都可以访问SQL表.     */
//    private static String user = "android_user"; // 修改为你的用户名
//    private static String password = "password"; // 修改为你的密码


    /*
     * 连接数据库
     * */
    public static Connection getConn() {
//        Connection conn = null;
//        try {
//            Class.forName(diver);
//            conn = (Connection) DriverManager.getConnection(url,user,password);//获取连接
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

        /*          DB标准代码：测试、+DB链接功能            */
        Connection conn = null;
        try {
            Class.forName(diver);
            conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("成功建立与数据库的连接");
            } else {
                System.out.println("未能成功建立与数据库的连接");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("找不到数据库驱动类：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("连接数据库时出现异常：" + e.getMessage());
        }
        return conn;
    }
}
