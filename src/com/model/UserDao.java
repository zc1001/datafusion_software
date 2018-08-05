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
    int tnum = 0;
    String choose = new String();
    List<User> list=new ArrayList<User>();
    //查询所有用户
    public List<User> queryAllUser(){
        String sql="select * from "+choose;  //查询

        try {
            conn=DbUtils.getConnection();
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
           // System.out.println(ps.toString());
            int time  = 1;
            while(rs.next()){
                User user=new User();
                user.setTnum(tnum);  //传送通道数

                double num[] = new double[tnum+1];
                num[0] = time;
                for(int i=1;i<tnum+1;i++)
                    num[i] = rs.getDouble(i);
                time++;  //shiyancishu
                /*user.setTd1(rs.getInt(1));
                user.setTd2(rs.getInt(2));
                user.setTd3(rs.getInt(3));
                user.setTd4(rs.getInt(4));
                user.setTd5(rs.getInt(5));
                user.setTd6(rs.getInt(6));
                user.setTd7(rs.getInt(7));*/
                user.setNum(num);
                list.add(user);

            }
           DbUtils.close(rs,ps,conn); //关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
   public void finish(){
        list.clear();
   }
   //得到实验时间
   public void setChoose(String s){
        choose = s;
   }

   public void setTnum(int a){
        tnum = a;
   }
}