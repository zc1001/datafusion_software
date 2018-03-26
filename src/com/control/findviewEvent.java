package com.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.view.findview;


public class findviewEvent implements ActionListener {
    int tnum;
    findview find;

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("1")){
            find.changedataview("dateview");
        }
        if(e.getActionCommand().equals("3")){
            find.changedataview("tuxiang");
        }

    }
    /*
    * 传入通道数量*/
    public void setTnum(int g){
        tnum = g;
    }
    //传入findview界面
    public void setFind(findview f){
        find  = f;
    }
}
