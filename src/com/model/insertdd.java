package com.model;

import javax.swing.*;
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
    private static final String PASSWORD="111111";//密码
    Connection conn = null;
    PreparedStatement pstm =null;
    ResultSet rt = null;
    String fpath;  //文件夹名
    String Dname;   //数据表名
    String encoding="GBK";
    String s_place,s_temp,s_shi,s_gas,s_other,s_name;  //实验信息
    int tnum;
    int dnum = 3;   //每次传来多少数据
    File file ;  //创建file数组
    BufferedReader br;
    Double num[][] ;
    String sql;
    long batch_size = 1000;  //设定batch的大小 最后的batch大小是 batch_size * 通道数量  为了防止
    long batch ;          //每次循环的数量的大小，达到batch的数量进行一次存储
    long channel_number;  //long型的通道数量 用于batch大小的计算
    String create_time ;
    public void init(){
      // fpath 格式： "D:\\saw_data\\2018-03-09\\20-43-29";
        /*
         * 以下为新版的数据传送至数据库代码，
         * 之前的版本可见下面注释部分
         * 数据格式 为 通道数,数据（十六位），如 2,AFDE0 ,十六位数据共六位，转换方式可参考函数
         * 旧版可参考注释中的内容 对于新版的变量 改进 改变了readerbuffer 中的reader  之前是一个通道数量的数组br[] 现在改为一个变量 br
         * 旧版本的代码部分已经存在注释之中
         * */


        /*
        * 初始化
        * */

          //创建Bufferreader
          try{
              file = new File(fpath+".txt");
              InputStreamReader  reader = new InputStreamReader(new FileInputStream(file));
              br= new BufferedReader(reader);
          }catch (Exception e) {
              e.printStackTrace();
          }


        //  InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
       /*   for(int i=0;i<tnum;i++)
          {
              tpath = fpath+"\\"+Integer.toString(i+1)+"通道.txt";
              file = new File(tpath);
              if (file.isFile()&&file.exists()){
                  InputStreamReader  reader = new InputStreamReader(new FileInputStream(file));
                  br[i] = new BufferedReader(reader);
                 // reader.close();  //关闭资源
              }
          }
      }catch (Exception e){
          e.printStackTrace();
      }*/
        long startTime=System.currentTimeMillis();
      try{
           /*
           *
           * 此部分为新版本的数据存储代码
           * */
           //数据库初始化
          Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection(URL,NAME, PASSWORD);  //连接
          initSql();
          if(sql == null)
              System.out.println("sql语句是空，修改未成功，代码位置：insertdd，82行");
          pstm = conn.prepareStatement(sql);
          conn.setAutoCommit(false);
          //开始数据txt文件的读写
          String line;
          long count = 0;
          batch = batch_size * tnum;    //设定循环的大小 为batch_size * 通道数量 变量处可以更改batch_size，现在是1000
          double channel;    //通道数
          String data_16;   //16的数据
          double data;   //转换后的数据
          create_time = "";    //设定实验时间，需要根据之前的文件夹进行修改
          create_time += fpath.substring(14,24);   //截取实验的日期 yyyy-MM-DD
          create_time += fpath.substring(26,fpath.length());  //截取实验时间  HH_MM_mm
          while((line = br.readLine()) != null)
          {
              if(line == null)
                  continue;
              count ++;   //对计数器进行加1
              //存储通道数
              String s = line.substring(0,1);
              channel = Double.parseDouble(s);
              data_16 = line.substring(2,line.length());
              data = string_to_double(data_16);
              //存入数据 数据先后和数据库的列一致
              pstm.setString(1,create_time);
              pstm.setDouble(2,channel);
              pstm.setString(3,data_16);
              pstm.setDouble(4,data);
              pstm.addBatch();

              if(count % batch ==0)
              {
                  //如果达到了batch的大小 ，进行一次传送
                  pstm.executeBatch();
                  conn.commit();
              }

          }
          if(count % batch !=0)
          {
              //在进行一次存储，防止有剩余的数据 count不到8000
              pstm.executeBatch();
              conn.commit();
          }

          /*
           *此部分代码时配合之前的版本进行的更改，具体协议参考之前的版本
           *目前针对更新的版本进行重新编写，将数据传送到数据库
           * 数据格式 为 通道数,数据（十六位），如 2,AFDE0 ,十六位数据共六位，转换方式可参考函数
           *
           * */
          /*Class.forName("com.mysql.jdbc.Driver");
          conn = DriverManager.getConnection(URL,NAME, PASSWORD);  //连接
          initSql();
          pstm = conn.prepareStatement(sql);
          conn.setAutoCommit(false);

          //初始化语句


          if(sql==null)
              System.out.println("sql  is null");

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

                  //插入数据库

                  //dnum循环
                  for(int i=0;i<dnum;i++)
                  {
                      //tnum 循环
                      for(int j=0;j<tnum;j++)
                          pstm.setDouble((j+1),num[j][i]);  //放入double
                      pstm.addBatch();
                  }
                  num = new Double[tnum][dnum]; //重新定义
              }
              else
              {
                  num = new Double[tnum][dnum]; //重新定义
                  pstm.executeBatch();
                  conn.commit();
                  break;  //退出本组数据
              }

             // System.out.println(" ");*/


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
        System.out.println(" 类名 insertdd 第 137行 程序运行时间： "+(endTime-startTime)+"ms");
        JOptionPane.showMessageDialog(null, "数据已上传完成", "提示", JOptionPane.INFORMATION_MESSAGE);
        //关闭输入流
        try{
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }


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
    public Double[] changetoint(String line){
        Double arr[] = new Double[dnum];
        String[] ts = line.split(" ");
       // System.out.println(ts.length+"hello");
        for(int i=0;i<ts.length;i++)
            arr[i] = Double.parseDouble(ts[i]);  //类型转换
         return arr;
    }
    /*
    * 此函数负责实现sql语句的初始化，在全局变量sql 中直接进行更改，在上方的init()函数中直接进行使用 可创建数据库的链接
    * */
    public void initSql (){
        sql = "INSERT INTO ";
        sql += (Dname.replace('-','_')+"(");
        sql += "createtime,channelnumber,datafor16,datafordouble)"; //分别实验时间(String)，通道数(double)，16为的数据(string)，数据转化成double(double)
        sql += "values (?,?,?,?)";
        /* for(int t=0;t<(tnum-1);t++)
        {
            sql += "td"+String.valueOf(t+1)+",";
        }
        sql += "td"+String.valueOf(tnum)+")values(";
        for(int t=0;t<(tnum-1);t++)
        {
            sql +="?,";
        }
        sql += "?)";*/

    }

    public double string_to_double(String s)
    {
        //实现string类型 转换double
        //这一部分和continueRead 类中的转换规则一致
        int num = Integer.parseInt(s,16);
        double a = Double.valueOf(num);
        return a;
    }
    //传送通道数量
    public void setTnum(int i){
        tnum = i;
        channel_number = (long)i;  //这个变量用于计算batch的大小
    }
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
