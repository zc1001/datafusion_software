package com.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
public class allocationview extends JFrame  implements ActionListener,ItemListener{
    /*
    初始化设定，实现串口配置界面，包括初始化，结束,这里包括事件监听........；
    * */
    Frame f;   //初始化数据
    JDialog d;
    JComboBox comBox;  //下拉列表
    JComboBox comBox2;
    JTextField num; //通道数量
    JButton button_yes,button_no;
    String s,s2,snum;    //记录 combox 两个item内容

    public void init(){
        view();
        d.setVisible(true);
    }
     private void view(){

         AppMain panel = new AppMain();
         f = panel.getframe();
         d = new JDialog(f,"串口配置",true);
         Container c = d.getContentPane();
         d.setSize(350,400);
         FlowLayout fay = new FlowLayout(FlowLayout.CENTER);  //控件居中
         fay.setHgap(25);  //组件行间距
         fay.setVgap(30);  //组件纵向间距
         d.setLayout(fay); //控件居中
         /*
         * 窗口居中
         * */
         Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
         int width = 350;
         int height = 400;
         d.setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);


         /*int x = d.getX();
         int y = d.getY();
         System.out.println(x);
         System.out.println(y);*/


         /*
         * 选择串口*/
         c.add(new JLabel("请选择串口                    "));
         comBox = new JComboBox();
         comBox.addItem("  COM1  ");
         comBox.addItem("  COM2  ");
         comBox.addItemListener(this);
         c.add(comBox);


         /*
         选择通道
         * */
         c.add(new JLabel("请输入通道数            "));
         num = new JTextField(8);
        /* num.getDocument().addDocumentListener(new DocumentListener(){
                                                   public void changedUpdate(DocumentEvent e) {//这是更改操作的处理
                                                        snum = num.getText().trim();//trim()方法用于去掉你可能误输入的空格号
                                                   }
                                                   public void insertUpdate(DocumentEvent e) {//这是插入操作的处理
                                                       snum = num.getText().trim();
                                                   }
                                                   public void removeUpdate(DocumentEvent e) {//这是删除操作的处理
                                                       snum = num.getText().trim();
                                                   }
                                               }
         );*/
         c.add(num);


         /* 选择频率
         * */
         c.add(new JLabel("         请选择抽样频率          "));
         comBox2 = new JComboBox();
         comBox2.addItem("  1.0  ");
         comBox2.addItem("  1.5  ");
         comBox2.addItem("  2.0  ");
         comBox2.addItem("  2.5  ");
         comBox2.addItemListener(this);
         c.add(comBox2);


         /*
         button  确定 取消
         * */
         c.add(new JLabel("       "));
         button_yes = new JButton("确定");
         button_no = new JButton("取消");
         button_yes.setActionCommand("yes");
         button_no.setActionCommand("no");
         button_yes.addActionListener(this);
         button_no.addActionListener(this);
         c.add(button_yes);
         c.add(button_no);
     }


     /*
     * 建立事件监听
     * */


    public void actionPerformed(ActionEvent e)
    {
     if (e.getActionCommand().equals("yes")){
         snum = num.getText();
         System.out.println("最终结果:");
         System.out.println(s);
         System.out.println(s2);
         System.out.println(snum);
     }
     if(e.getActionCommand().equals("no")){
        d.setVisible(false);
     }
    }
    public void itemStateChanged(ItemEvent e){
        if(e.getStateChange() == ItemEvent.SELECTED)
        {
            s=(String)comBox.getSelectedItem();
          //  System.out.println(s);
            s2 = (String)comBox2.getSelectedItem();
           // System.out.println(s2);

          /*  String snum = num.getText();
            System.out.println(snum);*/
        }
    }

}