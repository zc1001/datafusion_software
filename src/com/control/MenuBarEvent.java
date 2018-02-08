package com.control;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.view.AppMain;
import javax.swing.*;
import com.view.allocationview;
import com.view.newview;
import com.view.JFSwingDynamicChart;
public class MenuBarEvent extends JFrame implements ActionListener,Runnable{
    //AppMain y = new AppMain();
    AppMain j;
    JFrame frame; //获取当前页面
    JFSwingDynamicChart Jchart;   //接收dataplay传来的 chart实例对象
    private javax.swing.JDesktopPane JDeskTop = null;
    private String EventName = "";  //定义事件的名字
    public void setDeskTop(javax.swing.JDesktopPane deskTop) {
        this.JDeskTop = deskTop;
    }
    public void setEventName(String eventName) {
        this.EventName = eventName;
    }
    /*先定义set 在APPmain中定义，传过来之后，使用本地的函数调用此函数，获取APPMain，然后处理,在函数中定义可以 OK
    * */
    public AppMain setAPP(AppMain appm){
       j = appm;
       return j;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("saw_allocation") || EventName.equals("saw_allocation")){
             allocationview allo = new allocationview();
            allo.setAPPmain(j);
             allo.init();

        }
        if (e.getActionCommand().equals("saw_new") || EventName.equals("saw_new")){
          newview newvie = new newview();
          newvie.setAPPmain(j);
          newvie.init();

        }
        if (e.getActionCommand().equals("saw_about") || EventName.equals("saw_about")){
            (new Thread(this)).start();
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowevent) {
                    System.exit(0);
                }
            });
        }

    }
    public void setFrame(JFrame f){
        frame = f;
    }
    /*
    * 获取dataplay传来的chart实例对象
    * */
    public void setJchart(JFSwingDynamicChart Jc){
        Jchart = Jc;
        if(Jchart ==null)
            System.out.println(" Jchart null");
        else
            System.out.println("not null");
    }
    /*
    * run函数
    * */
    @Override
    public void run(){
        while(true)
        {
            try {
                Jchart.setNumber(Math.random()*100 );
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
