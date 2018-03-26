package com.control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.view.findview;
public class tuxingEvent implements ActionListener {
    findview find = new findview();
    @Override
    public void actionPerformed(ActionEvent e){
    find.changetuxiang(e.getActionCommand());
    //System.out.println(e.getActionCommand()+"tuxiangevent");
    }
    public void setFind(findview h){
        find = h;
    }
}
