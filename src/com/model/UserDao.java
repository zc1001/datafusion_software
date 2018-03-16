package com.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private Connection conn=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;

    //查询所有用户
    public List<User> queryAllUser(){
        String sql="select * from da";
        List<User> list=new ArrayList<User>();
        try {
            conn=DbUtils.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
           // System.out.println(ps.toString());
            while(rs.next()){
                User user=new User();
                user.setTd1(rs.getInt(1));
                user.setTd2(rs.getInt(2));
                user.setTd3(rs.getInt(3));
                user.setTd4(rs.getInt(4));
                user.setTd5(rs.getInt(5));
                user.setTd6(rs.getInt(6));
                user.setTd7(rs.getInt(7));
                list.add(user);
            }
           DbUtils.close(rs,ps,conn); //关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}