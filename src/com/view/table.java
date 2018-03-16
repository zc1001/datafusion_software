package com.view;


import com.model.*;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class table extends JFrame {
    private String head[]=null;
    private Object [][]data=null;
    private UserDao user=new UserDao();
   public table(){
       head=new String[] {
               "td1", "td2", "td3", "td4", "td5", "td6", "7",
       };
   }

  /*  *//*
    * 初始化
    * *//*
    *//*public void init(){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    long startTime=System.currentTimeMillis();
                    table frame = new table();
                    frame.setVisible(true);
                    long endTime=System.currentTimeMillis();
                    System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*//*
    *//*
    * 函数
    * *//*
    public table(){
       *//* setResizable(false);

        setTitle("\u673A\u7968\u9884\u8BA2\u7CFB\u7EDF");
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 300);
        Dimension   us=this.getSize();
        Dimension them=Toolkit.getDefaultToolkit().getScreenSize();

        int   x=(them.width-us.width)/2;
        int   y=(them.height-us.height)/2;

        this.setLocation(x, y); //dingyizuobiao
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);*//*
       //定义panel
        contentPane = new JPanel();
        scrollPane = new JScrollPane();
      //  scrollPane.setBounds(0,0,700,250);

        table = new JTable();

        table.setBorder(new LineBorder(new Color(0, 0, 0)));


        DefaultTableModel tableModel=new DefaultTableModel(queryData(),head){
            public boolean isCellEditable(int row, int column)   //不可编辑
            {
                return false;
            }
        };
        table.setModel(tableModel);

        scrollPane.setViewportView(table);
       *//* GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)
                                .addGap(66))
        );
        contentPane.setLayout(gl_contentPane);*//*
       contentPane.add(scrollPane);
    }*/
    //生成表格数据
    /**
     * @return
     */
    public Object[][] queryData(){
        List<User> list=user.queryAllUser();
        data=new Object[list.size()][head.length];
        System.out.println(list.size());
        for(int i=0;i<list.size();i++){

            data[i][0]=list.get(i).getTd1();
            data[i][1]=list.get(i).getTd2();
            data[i][2]=list.get(i).getTd3();
            data[i][3]=list.get(i).getTd4();
            data[i][4]=list.get(i).getTd5();
            data[i][5]=list.get(i).getTd6();
            data[i][6]=list.get(i).getTd7();

        }
        list.clear(); //清楚list空间
        return data;
    }


    public String[] getHead() {

        return head;
    }
}
