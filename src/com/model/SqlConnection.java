package com.model;

import java.sql.*;

public class SqlConnection {
    //这里是SqlConnection 类

    /*
     *java连接mysql数据库
     *1、加载驱动程序
     *2、数据库连接字符串"jdbc:mysql://localhost:3306/数据库名?"
     *3、数据库登录名
     *3、数据库登录密码
     */

    private static final String URL="jdbc:mysql://localhost:3306/dd";//数据库连接字符串，这里的deom为数据库名
    private static final String NAME="root";//登录名
    private static final String PASSWORD="1111";//密码
    int tdnum;
    String filepath;  //文件夹路径
    String daname;   //创建的数据库名
    PreparedStatement pst = null;
    Connection conn = null;       //连接
    Statement stmt = null;
    String searsql = new String();
    String datime = new String();
    String shiyanx[];
    ResultSet rs;
    public String[] TheSqlConnection(String s)
    {
        shiyanx = new String[8];  //存储实验信息
        datime = s;  //获取实验时间进行试验信息查询
        //1.加载驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("未能成功加载驱动程序，请检查是否导入驱动程序！");
            //添加一个println，如果加载驱动异常，检查是否添加驱动，或者添加驱动字符串是否错误
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(URL, NAME, PASSWORD);
          //  System.out.println("获取数据库连接成功！");
            /*stmt = conn.createStatement();
                      if(0 == stmt.executeLargeUpdate(creatsql))
                          {
                              System.out.println("成功创建表！");
                          }
                      else
                      {
                              System.out.println("创建表失败！");
                          }
                      //
                       stmt.close();
                       conn.close();
                       System.out.println("//关闭资源");*/
        } catch (SQLException e) {
            System.out.println("获取数据库连接失败！");
            //添加一个println，如果连接失败，检查连接字符串或者登录名以及密码是否错误
            e.printStackTrace();
        }
        //初始化数据库查找
        searsql = "select * from message where s_time='"+datime+"';";
        try{
            pst = (PreparedStatement) conn.prepareStatement(searsql);
             rs = pst.executeQuery();
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            if(rs.next())
            {
                for(int i=0;i<7;i++)
                    shiyanx[i] = rs.getString(i+1);
                shiyanx[7] = String.valueOf(rs.getInt(8));
            }
            else
                System.out.println("rs is null in the Sqlconne 81");

        }catch(Exception e){
            e.printStackTrace();
        }
        if(conn!=null)
        {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                conn=null;
            }
        }
        try {
            rs.close();
            pst.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return shiyanx;
    }
   /* public void finish(){
        //数据库打开后就要关闭
        if(conn!=null)
        {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                conn=null;
            }
        }
        try {
            rs.close();
            pst.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
*/
    public void creatmessage(){
         String creatsql = "CREATE TABLE message("
                    + "s_time varchar(50) not null,"
                    +"name varchar(50),"
                    +"con varchar(100),"
                    +"place varchar(50),"
                    +"tem varchar(50),"
                    +"shi varchar(50),"
                    +"gas varchar(50),"
                    +"tdnum int(4) not null"
                     + ")charset=utf8;";
         //System.out.println(creatsql);
       try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(URL, NAME, PASSWORD);
          /* if(conn == null)
               System.out.println("null");*/
           ResultSet rs = conn.getMetaData().getTables(null, null, "message", null);
           //判断表是否存在
           if (rs.next()) {
               System.out.println("表已存在 message");
           }else {
               System.out.println("创建表");
               stmt = conn.createStatement();
               if(0 == stmt.executeUpdate(creatsql))
               {
                   System.out.println("成功创建表！");
               }
               else
               {
                   System.out.println("创建表失败~");
               }
               stmt.close();
               conn.close();
               System.out.println("关闭资源");
           }

       }catch(Exception e){
           System.out.println("创建表失败！");
           e.printStackTrace();
       }


    }
    /*
    * test*/
      public void creatsawdata(){
          //初始化 语句
          String creatsql = "CREATE TABLE "+daname+"(";
          for(int i=0;i<(tdnum-1);i++)
          {
              creatsql += ("td"+String.valueOf(i+1)+" int not null,");
          }
           creatsql +=("td"+String.valueOf(tdnum)+" int not null");
           creatsql += ")charset=utf8;";

          try{
              Class.forName("com.mysql.jdbc.Driver");
              conn = DriverManager.getConnection(URL, NAME, PASSWORD);
          /* if(conn == null)
               System.out.println("null");*/
              ResultSet rs = conn.getMetaData().getTables(null, null, daname, null);
              //判断表是否存在
              if (rs.next()) {
                  System.out.println("表已存在 ");
              }else {
                  System.out.println("创建表");
                  stmt = conn.createStatement();
                  if(0 == stmt.executeUpdate(creatsql))
                  {
                      System.out.println("成功创建表！");
                  }
                  else
                  {
                      System.out.println("创建表失败~");
                  }
                  stmt.close();
                  conn.close();
                  System.out.println("关闭资源");
              }

          }catch(Exception e){
              System.out.println("创建表失败！");
              e.printStackTrace();
          }
          }
          //传入通道数量
          public void setTdnum(int i){ tdnum = i; }
          //传送 文件夹名
          public void setFilepath(String s){ filepath = s; }
          // 传送数据表名
          public void setDaname(String s){ daname = s; };
}
