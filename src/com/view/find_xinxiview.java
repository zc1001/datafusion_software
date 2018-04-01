package com.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;
import java.awt.*;
/*
* 实现 查看实验信息的界面返回PANEL
* */
public class find_xinxiview  {
    JButton bu = new JButton();  //初始化button
    JPanel panel = new JPanel(); //最后返回的panel
    String xinxi[];  //存放信息
    //初始化
    public void init(){
        panel.setPreferredSize(new Dimension(260,500));  //只能用这个改
        panel.setBackground(Color.lightGray);
        panel.setVisible(true);
        panel.setLayout(new FlowLayout());
        //实验信息
        JLabel b = new JLabel("实验信息                  ");
        b.setFont(new Font("宋体",1,16));
        panel.add(b);
        // JLabel mess = new JLabel("<html>"+s_name+"<br>" + s_place+"<br>"+s_gas+"<br>"+s_shi+"<br>"+s_temp+"<br>"+s_other+"<br>"+"</html>",JLabel.CENTER);
        //  mess.setFont(new Font("宋体",0,15));
        //  control_panel.add(mess);
        JLabel name = new JLabel();
        JLabel place = new JLabel();
        JLabel gas = new JLabel();
        JLabel other = new JLabel();
        JLabel tem = new JLabel();
        JLabel sd = new JLabel();
        JLabel tim = new JLabel();
        name.setSize(220,0);
        place.setSize(220,0);
        gas.setSize(220,0);
        other.setSize(220,0);
        tem.setSize(220,0);
        sd.setSize(220,0);
        xinxi[0] = "实验时间: "+xinxi[0];
        xinxi[6]= "气体： "+xinxi[6];
         //信息设置
        name.setText(xinxi[0]);
        place.setText(xinxi[1]);
        gas.setText(xinxi[2]);
        other.setText(xinxi[3]);
        tem.setText(xinxi[4]);
        sd.setText(xinxi[5]);
        tim.setText(xinxi[6]);
        createJLabelWithWrapWidth(220,name);
        createJLabelWithWrapWidth(220,place);
        createJLabelWithWrapWidth(220,gas);
        createJLabelWithWrapWidth(220,other);
        createJLabelWithWrapWidth(220,tem);
        createJLabelWithWrapWidth(220,sd);
        createJLabelWithWrapWidth(220,tim);
        /*try{
            JlabelSetText(name,xinxi[0]);
            JlabelSetText(place,xinxi[1]);
            JlabelSetText(gas,xinxi[2]);
            JlabelSetText(other,xinxi[3]);
            JlabelSetText(tem,xinxi[4]);
            JlabelSetText(sd,xinxi[5]);
           // JlabelSetText(tim,xinxi[6]);
        }catch (Exception e){
            e.printStackTrace();
        };*/
        panel.add(name);
        panel.add(place);
        panel.add(gas);
        panel.add(other);
        panel.add(tem);
        panel.add(sd);
        panel.add(tim);

    }
    public JLabel createJLabelWithWrapWidth(int width, JLabel label){
        if (width <= 0 || label == null){
            return label;
        }
        String text = label.getText();
        if (!text.startsWith("<html>")){
            StringBuilder strBuilder = new StringBuilder("<html>");
            strBuilder.append(text);
            strBuilder.append("</html>");
            text = strBuilder.toString();
        }
        label.setText(text);
        View labelView = BasicHTML.createHTMLView(label, label.getText());
        labelView.setSize(width, 100);
        label.setPreferredSize(new Dimension(width, (int) labelView.getMinimumSpan(View.Y_AXIS)));
        return label;
    }


    //传入信息
    public void setXinxi(String a[]){
        xinxi = a;
    }
    public JPanel getPanel(){
        return panel;
    }

    }


