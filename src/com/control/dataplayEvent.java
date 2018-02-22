package com.control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import com.view.dataplay;
public class dataplayEvent implements ActionListener,ItemListener {
 /*
 * 实现事件监听，将监听到的对象的command传送到dataplay 转换界面*/
   dataplay datview;
    @Override
    public void actionPerformed(ActionEvent e){
     String ename = e.getActionCommand(); //获取command
    // System.out.println(ename);  //测试
     datview.changedt(ename);    //使用获取的command转换界面 第一个界面 是1
    }
    public void itemStateChanged(ItemEvent e){


    }
    /*
    * 接收dataplay传来的view用作转换界面
    * */
    public void setDatview(dataplay view){
        datview = view;
    }
}
