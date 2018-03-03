package com;

import com.view.*;
import java.io.File;
import java.io.IOException;
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
        AppMain mainpanel = new AppMain();
        mainpanel.APPinit();

    }
}
