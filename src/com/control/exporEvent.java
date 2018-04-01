package com.control;

import sun.text.resources.JavaTimeSupplementary;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class exporEvent implements ActionListener {
     JTable table = new JTable();
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand()=="exp"){
            TestWaitUnit wait = new TestWaitUnit();  //初始化wait
            ExportExcel exportExcel = new ExportExcel();  //初始化
            wait.setExportExcel(exportExcel);
            exportExcel.init(table);  //显示导出界面
            wait.init();  //进行导出显示界面
        }

    }
    public void setTable(JTable c){
        table = c;
    }

}
