package com.control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    }

}
