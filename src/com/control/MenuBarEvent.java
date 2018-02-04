package com.control;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.view.AppMain;
import javax.swing.*;
import com.view.allocationview;
import com.view.newview;

public class MenuBarEvent extends JFrame implements ActionListener{
    //AppMain y = new AppMain();
    AppMain j;
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
            j.changeview();
        }

    }

}
