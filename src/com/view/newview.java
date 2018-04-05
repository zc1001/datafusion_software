package com.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import com.Main;
import com.view.AppMain;


/*
* 实现新建实验中的输入条件，同时实现事件监听
* */
public class newview extends JFrame implements ActionListener {
    Frame f;   //初始化数据
    JDialog d;
    JTextArea area_place,area_temp,area_shi,area_gas,other,area_name;
    JButton button_yes,button_no;
    String s_place,s_temp,s_shi,s_gas,s_other,s_name;
    AppMain j;  //接收APPmain
    /*
    * 初始化
    * */
    public void init(){
      view();
        d.setVisible(true);
        d.setBackground(Color.lightGray);
    }

    private void view(){
        AppMain panel = new AppMain();
        f = panel.getframe();
        d = new JDialog(f,"新建实验",true);
        Container c = d.getContentPane();
        d.setSize(350,600);
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
        area_name.setLineWrap(true);
        area_name.setWrapStyleWord(true);
        c.add(new JScrollPane(area_name));

        c.add(new JLabel("实验地点"));
        area_place = new JTextArea(2, 20);
        area_place.setLineWrap(true);
        area_place.setWrapStyleWord(true);
        c.add(new JScrollPane(area_place));

        c.add(new JLabel("实验温度"));
        area_temp = new JTextArea(2,20);
        area_temp.setLineWrap(true);
        area_temp.setWrapStyleWord(true);
        c.add(new JScrollPane(area_temp));

        c.add(new JLabel("周边湿度"));
        area_shi  = new JTextArea(2,20);
        area_shi.setLineWrap(true);
        area_shi.setWrapStyleWord(true);
        c.add(new JScrollPane(area_shi));

        c.add(new JLabel("  实验气体"));
        area_gas = new JTextArea(3,20);
        area_gas.setLineWrap(true);
        area_gas.setWrapStyleWord(true);
        c.add(new JScrollPane(area_gas));

        c.add(new JLabel("        备注"));
        other = new JTextArea(3,20);
        other.setLineWrap(true);
        other.setWrapStyleWord(true);
        c.add(new JScrollPane(other));

        c.add(new JLabel(" "));
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
           //信息初始
            s_place = "实验地点: ";
            s_name = "实验名称: ";
            s_other = "备注: ";
            s_temp = "实验温度: ";
            s_shi = "周边湿度: ";
            s_gas = "实验气体: ";

            if(area_gas.getText() ==null)
                s_gas += "无";
            else
               s_gas = area_gas.getText();

            if(area_name.getText() ==null)
                s_name += "无";
            else
                s_name += area_name.getText();

            if(other.getText() ==null)
                s_other += "无";
            else
                s_other += other.getText();

            if(area_place.getText() ==null)
                s_place += "无";
            else
                s_place += area_place.getText();

            if(area_shi.getText() ==null)
                s_shi += "无";
            else
                s_shi += area_shi.getText();
            if(area_temp.getText() ==null)
                s_temp += "无";
            else
                s_temp += area_temp.getText();
           // System.out.println(s_gas  +s_name   +s_other  +s_place  +s_shi);

            j.setgas_message(s_place,s_temp,s_shi,s_gas,s_other,s_name);
            Main.ifnew = true;
            d.setVisible(false);
            dataviewstart();
            j.changebuttonvisauble(new int[]{2,3});  //重新设定可使用部分 按钮暂定 结束不可用

        }
        if(e.getActionCommand().equals("no")){
            d.setVisible(false);
        }
    }
    public void setAPPmain(AppMain appmain){
        j = appmain;
    }
    public void dataviewstart(){
            System.out.println("实验创建OK，from newview");
            j.changeview();

        /*
        * 要加else语句 如果没有写之前的配置不能开始创建实验
        * */

    }


}
