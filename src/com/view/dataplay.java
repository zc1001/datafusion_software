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

public class dataplay extends JFrame {
    /*
    * 实现数据传输界面,曲线显示，和控制界面
    * */
    JPanel mainpanel = new JPanel();   //主界面和layout
    BorderLayout mainlayout = new BorderLayout();
    JPanel contentPane=(JPanel) getContentPane();
    FlowLayout conlayout = new FlowLayout(FlowLayout.LEFT );
    JPanel control_panel = new JPanel();
    JPanel testpanel = new JPanel();   //tiaose
    ButtonGroup group;
    dataplayEvent dataevent = new dataplayEvent(); //事件监听

   /*
   * 界面初始化
   * */
    public void viewinit(){
        mainviewinit();
        conviewinit();
    }

    public void mainviewinit(){
        mainpanel.setLayout(mainlayout);
        testpanel.setBackground(Color.GRAY);
        mainpanel.add(testpanel,BorderLayout.CENTER);

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
        * 设定组件
        * */
        control_panel.add(new JLabel("请选择通道：                                     "));
        group = new ButtonGroup();
        String[] rad_name = new String[8] ;
        for(int i=0;i<rad_name.length;i++)
            rad_name[i] = "通道"+(i+1);
        for(int i=0;i<rad_name.length;i++)
        {
            JRadioButton radio = new JRadioButton(rad_name[i]);
            radio.setActionCommand(rad_name[i]);
            radio.addItemListener(dataevent);
            control_panel.add(radio);
        }


    }

    public JPanel getMainpanel() {
        return mainpanel;
    }
}
