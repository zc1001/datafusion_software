package com.control;
import javafx.scene.control.ComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.*;
import javax.swing.JComboBox;
import com.view.allocationview;

public class allocationEvent implements ActionListener,ItemListener {
    JComboBox jb1 ;
    JComboBox jb2;
    public void actionPerformed(ActionEvent e)
    {

        }
        public void itemStateChanged(ItemEvent e){
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
               /* allocationview allo = new allocationview();
                JComboBox jb = allo.getbox();
                String s=(String)jb.getSelectedItem();
                System.out.println(s);
                jb = allo.getbox2();
                String s2 = (String)jb.getSelectedItem();
                System.out.println(s2);*/
            }
        }
        public void setcombox(JComboBox c){
        jb1 = c;
        }
        public void setcombox2(JComboBox c){
        jb2 = c;
        }
    }

