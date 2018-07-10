package com;

import com.view.*;
import com.model.SqlConnection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;
import org.jfree.data.time.Millisecond;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.io.File;
import java.util.Enumeration;


public class Main {
public static boolean ifnew = false;
public static boolean ifallo = false;
public static int Ptime = 0;  //记录开始按钮的次数 大于一则表示已经开始，是暂停之后再次点击开始，不可重新连接，在结束之后可设定数量为0
    public static void main(String[] args) {
      //  Millisecond now = new Millisecond();
        //System.out.println(now);
        try {
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible", false);
            //BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("set skin fail!");
        }
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
