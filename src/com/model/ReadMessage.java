package com.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadMessage {
    String message[] = new String[8];  //8个信息，都用于存储 具体存储信息内容见下面注释
    BufferedReader br;
    String file_path ;
    String line;
    public String[] Readmessage(String s)
    {
        /*
        * 本函数用于读取D:\\saw_data\\message 中对应的实验信息，文件名文 s 对应的字符串
        * 返回一个字符串数组 分别是时间、名称、备注、地点、温度、湿度、气体、通道数量
        * 对于温度、湿度、名称、备注、气体、时间已经有前面的标题 “实验地点：”，在存储的时候直接存储，传查看的内容
         * */
        file_path = s;
        line = new String();
        try{
            //之前的file_path格式如下：2019-07-29  16_13_32.txt，中间不能有空格，实际文件里面没有空格 ，所以要更改
            file_path = file_path.replace(" ","");
            //System.out.println(file_path);
           File file = new File("D:\\saw_data\\message\\"+file_path);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(file));
            br= new BufferedReader(reader);
        }catch (Exception e) {
            e.printStackTrace();
        }
        //开始读取实验信息
        try{
            //我在文件中的信息顺序 和这个函数要求的不一样，所以要很臃肿的把每一个line 放到对应的字符串位置
            line = br.readLine();
            message[0] = line.replace("实验时间：","");    //实验时间
            line  = br.readLine();
            message[7] = line.substring(line.length()-1,line.length());   //通道数   只保存数字
            line = br.readLine();
            message[4] = line;  //温度
            line = br.readLine();
            message[1] = line;   //名称
            line = br.readLine();
            message[5] = line;  //湿度
            line = br.readLine();
            message[3] = line;  //地点
            line = br.readLine();
            message[6] = line;  //气体
            line = br.readLine();
            message[2] = line;  //备注
        }catch (Exception e){
            e.printStackTrace();
        }
        return message;
    }
}
