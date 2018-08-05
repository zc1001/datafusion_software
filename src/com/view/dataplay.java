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
    * dataview界面初始化 动态，有时间监听传送字符串 在此进行界面转换
    * */


    JPanel mainpanel = new JPanel();   //主界面和layout
    BorderLayout mainlayout = new BorderLayout();
    JPanel contentPane=(JPanel) getContentPane();
    FlowLayout conlayout = new FlowLayout(FlowLayout.LEFT );
    JPanel control_panel = new JPanel();
    JPanel testpanel = new JPanel();   //tiaose
    JPanel data_panel = new JPanel(); //定义数据的界面
    CardLayout datalayout = new CardLayout(); //定义数据界面布局
    ButtonGroup group;
    public JTextField textshidu ;  //用来存放湿度的信息，实时刷新
    public JTextField textwendu ;  //用来存放温度的信息，实时刷新
    dataplayEvent dataevent = new dataplayEvent(); //事件监听
    AppMain j;   //接收APPmain
    String s_com,s_pinlv,snum;   //接收串口配置的字符串
    String s_place,s_temp,s_shi,s_gas,s_other,s_name; //接收实验条件的信息
    JFSwingDynamicChart JSchart = new JFSwingDynamicChart();   //测试用
    MenuBarEvent MenuEvent;   //接收主界面中的事件监听，为他传送chart对象
   JFSwingDynamicChart[] Jchart;    //随机定义的多个chart对象
   /*
   * 界面初始化
   * */
    public void viewinit(){
        dataevent.setDatview(this);   //把本界面传给事件监听 进行数据界面转换
        mainpanel.setLayout(mainlayout);
        conviewinit();
        dataviewinit();


    }

    public void dataviewinit(){
        data_panel.setLayout(datalayout);  //定义界面 Cardlayout
        int tdnum = Integer.parseInt(snum);  //获取通道的数量
         Jchart = new JFSwingDynamicChart[tdnum];  //新建多个对象
        for(int i=0;i<tdnum;i++)                        //实例化
        {
            Jchart[i] = new JFSwingDynamicChart();
        }
        for(int i=0;i<tdnum;i++)               //定义多个通道
        {
            Jchart[i].setgonum((i+1));   //向chart对象放数字 用于命名
           JPanel chartPanel = Jchart[i].getDatapanel();
           String goname = Integer.toString(i+1);    //为本界面设定名字 第一个界面 名字为 1
           data_panel.add(chartPanel,goname);
        }
        data_panel.setVisible(true);
        mainpanel.add(data_panel,BorderLayout.CENTER);
         changedt("1");   //初始化进入界面

    }
    /*
    * 转换通道,k 为第几通道，K为String
    * */
    public void changedt(String k){

        datalayout.show(data_panel,k);
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
            String rname = Integer.toString(i+1);  //设定第几通道 作为 command
            radio.setActionCommand(rname);
            radio.addActionListener(dataevent);
            group.add(radio);
            control_panel.add(radio);
        }
        String block = "                   ";
        Tnum %= 3;
        if(Tnum>0)
        {
            Tnum = 3-Tnum;
            for(int i = 0;i<Tnum;i++)
            {
                control_panel.add(new JLabel(block));
            }
        }
        /*
        * 初始化存放 温度、湿度，后续将刷新
        * */
        textshidu = new JTextField(8);
        textwendu = new JTextField(8);
        control_panel.add(new JLabel("实验温度："));
        control_panel.add(textwendu);
        control_panel.add(new JLabel("        "));
        control_panel.add(new JLabel("实验湿度："));
        control_panel.add(textshidu);
        /*实验信息
        * */
        JLabel b = new JLabel("实验信息                                        ");
        b.setFont(new Font("宋体",1,16));
        control_panel.add(b);
       // JLabel mess = new JLabel("<html>"+s_name+"<br>" + s_place+"<br>"+s_gas+"<br>"+s_shi+"<br>"+s_temp+"<br>"+s_other+"<br>"+"</html>",JLabel.CENTER);
      //  mess.setFont(new Font("宋体",0,15));
      //  control_panel.add(mess);
        JLabel name = new JLabel();
        JLabel place = new JLabel();
        JLabel gas = new JLabel();
        JLabel other = new JLabel();
        JLabel tem = new JLabel();
        JLabel sd = new JLabel();
        name.setSize(220,0);
        place.setSize(220,0);
        gas.setSize(220,0);
        other.setSize(220,0);
        tem.setSize(220,0);
        sd.setSize(220,0);
        try{
            JlabelSetText(name,s_name);
            JlabelSetText(place,s_place);
            JlabelSetText(gas,s_gas);
            JlabelSetText(other,s_other);
            JlabelSetText(tem,s_temp);
            JlabelSetText(sd,s_shi);
        }catch (Exception e){

        };
        control_panel.add(name);
        control_panel.add(place);
        control_panel.add(gas);
        control_panel.add(tem);
        control_panel.add(sd);
        control_panel.add(other);
    }

    void JlabelSetText(JLabel jLabel, String longString)
            throws InterruptedException {
        StringBuilder builder = new StringBuilder("<html>");
        char[] chars = longString.toCharArray();
        FontMetrics fontMetrics = jLabel.getFontMetrics(jLabel.getFont());
        int start = 0;
        int len = 0;
        while (start + len < longString.length()) {
            while (true) {
                len++;
                if (start + len > longString.length())break;
                if (fontMetrics.charsWidth(chars, start, len)
                        > jLabel.getWidth()) {
                    break;
                }
            }
            builder.append(chars, start, len-1).append("<br/>");
            start = start + len - 1;
            len = 0;
        }
        builder.append(chars, start, longString.length()-start);
        builder.append("</html>");
        jLabel.setText(builder.toString());
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
        MenuEvent.setJchart(Jchart);   //向MenuEvent传送JSchart

    }
    /*
    * 向menubar传送chart对象 （JFSwing）
    * */

    /*
    * 传送实验信息
    * */
    public void setsaw_massage(String s1,String s2,String s3,String s4,String s5,String s6){
        s_place = s1;
        s_temp = s2;
        s_shi = s3;
        s_gas = s4;
        s_other = s5;
        s_name = s6;
    }


}
