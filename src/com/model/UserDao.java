package com.model;
/*
*
* 这部分是将数据进行读取，以double的形式将之前的数据读取到一个二维数组之中 ，之后转换到一个二维 的object数组之中
*
*
* */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import  java.io.IOException;

public class UserDao {
    private Connection conn=null;
    private PreparedStatement ps=null;
    private ResultSet rs=null;
    int tnum = 0;
    String choose = new String();
    List<User> list=new ArrayList<User>();
    BufferedReader br;
    File file;
    String line = new String();   //创建读取文件的每一行字符串
    //查询所有用户
    //这里是根据时间找到数据的部分
    public List<User> queryAllUser(){
        //System.out.println("choose"+choose);   //格式为 2019-07-29  16_13_32.txt
        /*
        * 此部分为新代码
        * 根据选择的日期，从文件夹中找到数据文件，然后再存到表格中
        *
        * */
        String choose_time = choose.replace(" ","\\");  //更换文件名的格式，之前格式参考上面的注释
        if(choose_time.indexOf("D:\\saw_data\\") == -1)
            choose=  "D:\\saw_data\\" + choose_time;  //获取文件的地址
        //System.out.println(choose);
        double num[] = new double[tnum+1];
        long time = 1;
        //创建bufferread
        try{
            file = new File(choose);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            br= new BufferedReader(reader);
        }catch (Exception e) {
            e.printStackTrace();
        }
        int chanel = 0;   //哪个通道
        double chanel_data = 0;   //通道的数据
        String s = new String();  //数据转换的中间字符串
        boolean flag = false;    //对当前的数据组是否符合要求，即是否第一个数据通道1，
        int chanel_count = 0;     //对通道数进行计数，方便之后将数据存入特定位置的数组
        //进行指定文件中的数据的读取
        try{
            while((line = br.readLine()) != null)
            {
                //开始进行数据的转换
                s = line.substring(0,1);   //获取第一个字符串
                chanel = Integer.parseInt(s);   //进行数据转换
                s = line.substring(2,line.length());   //获取对应的数据
                chanel_data = string_to_double(chanel,s);   //通道数据的转化
                //System.out.println(chanel);
                //对数据进行判断，先用 flag进行预判，初始值是false，当判断已经符合数据要求之后将它改为true，当预判结果为数据已经符合要求，当前的通道数为1（一直寻找第一个通道数量为 1 的数据），
                //将他存到数组的相应位置
                if(flag == false)   //预判断当前的数据不符和要求
                {
                    if(chanel != 1)
                    {
                        continue;
                    }
                    else{
                        //数据的通道数为1，已找到要求数据的第一个数据，可以开始进行存储
                        flag = true;  //预判为真
                        num[chanel] = chanel_data;    //这个模块只会进行一次
                        chanel_count++;   //通道计数加1 当通道计数达到 通道数量的时候，表示num[tdnum] 数组已经存满，可以进行下一步的计算
                    }
                }
                else   // 数据符合要求  预判为真
                {
                    num[chanel] = chanel_data;
                    chanel_count ++;   //通道计数加1 当通道计数达到 通道数量的时候，表示num[tdnum] 数组已经存满，可以进行下一步的计算

                    flag = true;
                }

                if(chanel_count == tnum)  //每次进行判断 通道计数是否等于通道数量
                {
                    //当通道技术等于通道数量，表名num数组已经存满，可以进行下一步计算
                    chanel_count = 0;
                    num[0] = time;   //存入试验次数
                    time ++;
                    User user=new User();   //进一步处理
                    user.setTnum(tnum);  //传送通道数
                    user.setNum(num);

                    list.add(user);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }


        /*
        *
        * 以下代码为之前的代码 查询数据库的内容，将数据库的数据送入list
        * 在使用中可能有些数据并没有存入数据库，改为优先从文件中读取，之后能尝试加入 如果不能在文件导入，在尝试数据库导入，加判断
        * */
       /* String sql="select * from "+choose;  //查询

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
                *//*user.setTd1(rs.getInt(1));
                user.setTd2(rs.getInt(2));
                user.setTd3(rs.getInt(3));
                user.setTd4(rs.getInt(4));
                user.setTd5(rs.getInt(5));
                user.setTd6(rs.getInt(6));
                user.setTd7(rs.getInt(7));*//*
                user.setNum(num);
                list.add(user);

            }
           DbUtils.close(rs,ps,conn); //关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return list;
    }
    public double string_to_double(int chanel,String s)
    {
        //实现string类型 转换double
        //这一部分和continueRead 类中的转换规则一致
        int num = Integer.parseInt(s,16);
        double a = 0;   //最后的结果
        //通道不同，参数不同  目前在 显示、存储还没有更改
        if(chanel <=4)  //电化学通道
           a = Double.valueOf(num) * 0.03662-1.2-1200;
        else
            a = 0.00014305115 -1.2 - 1200;

        return a;
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