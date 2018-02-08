package com.view;

import com.sun.deploy.panel.JreDialog;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
import javax.swing.JRadioButton;
import java.lang.String;
import java.awt.event.ActionListener;
import com.control.dataplayEvent;
import java.awt.event.ItemListener;
import com.view.AppMain;
import com.view.JFSwingDynamicChart;
import com.control.MenuBarEvent;
public class dataplay extends JFrame {
    /*
    * 实现数据传输界面,曲线显示，和控制界面，实现初始化界面，chart对象
    * 接收AppMain传送来的事件监听，向事件监听传送chart对象
    * */


    JPanel mainpanel = new JPanel();   //主界面和layout
    BorderLayout mainlayout = new BorderLayout();
    JPanel contentPane=(JPanel) getContentPane();
    FlowLayout conlayout = new FlowLayout(FlowLayout.LEFT );
    JPanel control_panel = new JPanel();
    JPanel testpanel = new JPanel();   //tiaose
    ButtonGroup group;
    dataplayEvent dataevent = new dataplayEvent(); //事件监听
    AppMain j;   //接收APPmain
    String s_com,s_pinlv,snum;   //接收串口配置的字符串
    String s_place,s_temp,s_shi,s_gas,s_other,s_name; //接收实验条件的信息
    JFSwingDynamicChart JSchart = new JFSwingDynamicChart();
    MenuBarEvent MenuEvent;   //接收主界面中的事件监听，为他传送chart对象
   /*
   * 界面初始化
   * */
    public void viewinit(){
        mainpanel.setLayout(mainlayout);
        dataviewinit();
        conviewinit();
    }

    public void dataviewinit(){
        testpanel = JSchart.getDatapanel();
        mainpanel.add(testpanel,BorderLayout.CENTER);

    }
    /*
     * 接收APPmain
     * */
    public void setAPPpanel(AppMain appmain){
        j = appmain;
    }

    private void conviewinit(){
        /*
        *    实现控制界面的设定
        * */
        /*
        * 设定界面大小
        * */
       control_panel.setLayout(conlayout);
        Dimension frameSize = contentPane.getSize();
        control_panel.setPreferredSize(new Dimension(250,frameSize.height));  //只能用这个改
        conlayout.setHgap(15);
        mainpanel.add(control_panel,BorderLayout.EAST);
        /*
         * 获取串口信息
         * */
        s_com = j.getS_com();
        s_pinlv = j.getS_pinlv();
        snum = j.getSnum();
        int Tnum = Integer.parseInt(snum); //通道数量强制转换
        /*
        * 设定组件
        * */
        control_panel.add(new JLabel("请选择通道：                                     "));
        group = new ButtonGroup();
        String[] rad_name = new String[Tnum] ;
        for(int i=0;i<rad_name.length;i++)
            rad_name[i] = "通道"+(i+1);
        for(int i=0;i<rad_name.length;i++)
        {
            JRadioButton radio = new JRadioButton(rad_name[i]);
            radio.setActionCommand(rad_name[i]);
            radio.addItemListener(dataevent);
            group.add(radio);
            control_panel.add(radio);
        }
        String block = "         ";
        Tnum %= 3;
        if(Tnum>0)
        for(int i = 0;i<Tnum;i++)
        {
            control_panel.add(new JLabel(block));
        }
    }

    /*
    * 传送此panel APPmain中使用
    * */
    public JPanel getMainpanel() {
        return mainpanel;
    }
/*
    */
/*
    * 获取chart对象
    * *//*

    public JFSwingDynamicChart getJSchart(){
        return JSchart;
    }
*/
    /*
    * 获取MenuBarEvent 对象 向他传送实例的chart对象
    * */
    public void setMenuEvent(MenuBarEvent menu){
        MenuEvent = menu;
        MenuEvent.setJchart(JSchart);   //向MenuEvent传送JSchart
    }
    /*
    * 向menubar传送chart对象 （JFSwing）
    * */

}
