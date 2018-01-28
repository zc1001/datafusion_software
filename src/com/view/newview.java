package com.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
/*
* 实现新建实验中的输入条件，同时实现事件监听
* */
public class newview extends JFrame implements ActionListener {
    Frame f;   //初始化数据
    JDialog d;
    JTextArea area_place,area_temp,area_shi,area_gas,other,area_name;
    JButton button_yes,button_no;
    String s_place,s_temp,s_shi,s_gas,s_other,s_name;
    /*
    * 初始化
    * */
    public void init(){
      view();
        d.setVisible(true);
    }

    private void view(){
        AppMain panel = new AppMain();
        f = panel.getframe();
        d = new JDialog(f,"新建实验",true);
        Container c = d.getContentPane();
        d.setSize(350,400);
        FlowLayout fay = new FlowLayout(FlowLayout.CENTER);  //控件居中
        fay.setHgap(25);  //组件行间距
        fay.setVgap(10);  //组件纵向间距
        d.setLayout(fay); //控件居中
        /*
         * 窗口居中
         * */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 350;
        int height = 400;
        d.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);

        /*
        * 组件
        * */


        c.add(new JLabel("实验名称"));
        area_name = new JTextArea(2,20);
        c.add(area_name);

        c.add(new JLabel("实验地点"));
        area_place = new JTextArea(2, 20);
        c.add(area_place);

        c.add(new JLabel("实验温度"));
        area_temp = new JTextArea(2,20);
        c.add(area_temp);

        c.add(new JLabel("周边湿度"));
        area_shi  = new JTextArea(2,20);
        c.add(area_shi);

        c.add(new JLabel("实验气体"));
        area_gas = new JTextArea(3,20);
        c.add(area_gas);

        c.add(new JLabel(" 备注 "));
        other = new JTextArea(3,20);
        c.add(other);

        c.add(new JLabel("       "));
        button_yes = new JButton("确定");
        button_no = new JButton("取消");
        button_yes.setActionCommand("yes");
        button_no.setActionCommand("no");
        button_yes.addActionListener(this);
        button_no.addActionListener(this);
        c.add(button_yes);
        c.add(button_no);

    }
   /*
   * 事件监听 在这里更改
   * */
    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("yes")){
            s_gas = area_gas.getText();
            s_name = area_name.getText();
            s_other = other.getText();
            s_place = area_place.getText();
            s_shi = area_shi.getText();
            s_temp = area_temp.getText();
            System.out.println(s_gas  +s_name   +s_other  +s_place  +s_shi);
            d.setVisible(false);
        }
        if(e.getActionCommand().equals("no")){
            d.setVisible(false);
        }
    }

}
