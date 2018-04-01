package com;

import com.view.*;
import com.model.SqlConnection;
import java.io.File;



public class Main {
public static boolean ifnew = false;
public static boolean ifallo = false;
    public static void main(String[] args) {
        System.out.println("Hello World!");
        File file = new File("d:\\saw_data");  //创建存放数据文件夹
        if(!file.exists())
        {
            file.mkdir();
        }

        //创建信息表 初始化
        SqlConnection csql = new SqlConnection();
        csql.creatmessage();

        AppMain mainpanel = new AppMain();
        mainpanel.APPinit();

    }
}
