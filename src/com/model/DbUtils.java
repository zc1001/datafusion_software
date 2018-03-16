package com.model;

import java.sql.*;

public class DbUtils {

    private  static  final String URL="jdbc:mysql://localhost:3306/dd?useUnicode=true&characterEncoding=utf-8";
    private  static  final String USER="root";
    private  static  final String PASSWORD="1111";//此处为数据库密码，更改为自己数据库的密码

    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //关闭方法
    public  static void close(ResultSet rs, Statement stat, Connection conn) throws SQLException{
        if(rs!=null){
          //  System.out.println("rs  关闭");
            rs.close();
        }
        if(stat!=null){
            stat.close();
            //System.out.println("stat 关闭");
        }
        if(conn!=null){
            conn.close();
            //System.out.println("conn 关闭");
        }
    }

}
