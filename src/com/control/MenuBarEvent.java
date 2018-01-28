package com.control;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.view.AppMain;
import javax.swing.*;
import com.view.allocationview;
import com.view.newview;

public class MenuBarEvent implements ActionListener{
    private javax.swing.JDesktopPane JDeskTop = null;
    private String EventName = "";  //定义事件的名字
    public void setDeskTop(javax.swing.JDesktopPane deskTop) {
        this.JDeskTop = deskTop;
    }
    public void setEventName(String eventName) {
        this.EventName = eventName;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("saw_allocation") || EventName.equals("saw_allocation")){
             allocationview allo = new allocationview();
             allo.init();
        }
        if (e.getActionCommand().equals("saw_new") || EventName.equals("saw_new")){
          newview newvie = new newview();
          newvie.init();
        }
    }

}
