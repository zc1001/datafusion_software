package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;

/*
*
* 实现插入功能
*
* */
public class insertdd {
    private static final String URL="jdbc:mysql://localhost:3306/dd?rewriteBatchedStatements=true";//数据库连接字符串，这里的deom为数据库名
    private static final String NAME="root";//登录名
    private static final String PASSWORD="1111";//密码
    Connection conn = null;
    PreparedStatement pstm =null;
    ResultSet rt = null;
    String fpath;  //文件夹名
    String Dname;   //数据表名
    String encoding="GBK";
    String s_place,s_temp,s_shi,s_gas,s_other,s_name;  //实验信息
    int tnum;
    int dnum = 20;   //每次传来多少数据
    File file ;  //创建file数组
    BufferedReader[] br;
    Integer num[][] ;
    String sql;
    public void init(){
      // fpath = "D:\\saw_data\\2018-03-09 20-43-29";
        num = new Integer[tnum][dnum];
       String tpath ;
        br= new BufferedReader[tnum];
        /*
        * 初始化
        * */
      try{
          for(int i=0;i<tnum;i++)
          {
              tpath = fpath+"\\"+Integer.toString(i+1)+"通道.txt";
              file = new File(tpath);
              if (file.isFile()&&file.exists()){
                  InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
                  br[i] = new BufferedReader(reader);
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }
        long startTime=System.currentTimeMillis();

      try{
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection(URL,NAME, PASSWORD);  //连接
          initSql();
          pstm = conn.prepareStatement(sql);
          conn.setAutoCommit(false);
          /*
          * 初始化语句
          * */

         /* if(sql==null)
              System.out.println("sql  is null");*/

          String line = "hello";//初始化line
          //开始读数据
          while(line!=null){
              //读数据 存入数组
              for(int j=0;j<tnum;j++)
              {
                  line =  br[j].readLine();  //得到新的数据
                  if(line!=null)
                  {
                     // System.out.println("通道"+Integer.toString(j)+": "+line);
                     num[j] = changetoint(line);
                    // System.out.print("通道"+Integer.toString(j));
                     //for(int i=0;i<num.length;i++)
                       //System.out.print(num[i]+" ");
                  }
                  else
                      break;             //本组数据丢弃

              }
              //写数据
              if(line!=null)   //本组数据有效
              {
                  /*插入数据库
                   * */
                  //dnum循环
                  for(int i=0;i<dnum;i++)
                  {
                      //tnum 循环
                      for(int j=0;j<tnum;j++)
                          pstm.setInt((j+1),num[j][i]);
                      pstm.addBatch();
                  }
                  num = new Integer[tnum][dnum]; //重新定义
              }
              else
              {
                  num = new Integer[tnum][dnum]; //重新定义
                  pstm.executeBatch();
                  conn.commit();
                  break;  //退出本组数据
              }

             // System.out.println(" ");

          }
      }catch(Exception e){
          e.printStackTrace();
          throw new RuntimeException(e);
      }finally{
          if(pstm!=null){
              try {
                  pstm.close();
                  } catch (SQLException e) {
                  e.printStackTrace();
                  throw new RuntimeException(e);
                  }
                  }
                  if(conn!=null){
                               try {
                                   conn.close();
                                   } catch (SQLException e) {
                                   e.printStackTrace();
                                   throw new RuntimeException(e);
                                   }
                           }
                   }
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");


    }
    public void initxinxi(){
        String xinxisql = new String();
        xinxisql = "insert into message (s_time,name,con,place,tem,shi,gas,tdnum) values (?,?,?,?,?,?,?,?)";  //初始化语句
       try{
           Class.forName("com.mysql.jdbc.Driver");
           conn = DriverManager.getConnection(URL,NAME, PASSWORD);  //连接
           pstm = conn.prepareStatement(xinxisql);
           //插入信息
           pstm.setString(1,Dname);
           pstm.setString(2,s_name);
           pstm.setString(3,s_other);//这里备注改为试验条件
           pstm.setString(4,s_place);
           pstm.setString(5,s_temp);
           pstm.setString(6,s_shi);
           pstm.setString(7,s_gas);
           pstm.setInt(8,tnum);
           pstm.addBatch();
           pstm.executeBatch();
           pstm.close();
           conn.close();

       }catch(Exception e){
           e.printStackTrace();
       }


    }
    public Integer[] changetoint(String line){
        Integer arr[] = new Integer[dnum];
        String[] ts = line.split(".0");
       // System.out.println(ts.length+"hello");
        for(int i=0;i<ts.length;i++)
            arr[i] = Integer.parseInt(ts[i]);
         return arr;
    }
    public void initSql (){
        sql = "INSERT INTO ";
        sql += (Dname+"(");
        for(int t=0;t<(tnum-1);t++)
        {
            sql += "td"+String.valueOf(t+1)+",";
        }
        sql += "td"+String.valueOf(tnum)+")values(";
        for(int t=0;t<(tnum-1);t++)
        {
            sql +="?,";
        }
        sql += "?)";

    }
    //传送通道数量
    public void setTnum(int i){ tnum = i; }
    //传送文件夹名
    public void setFpath(String s){ fpath = s; }
    //传送数据表名
    public void setDname(String s){ Dname = s; }
    //传送实验信息
    public void setgas(String s1,String s2,String s3,String s4,String s5,String s6)
    {
        s_place = s1;
        s_temp = s2;
        s_shi = s3;
        s_gas = s4;
        s_other = s5;
        s_name = s6;
    }
}
