package com.view;
import javax.swing.*;
import java.awt.*;
import javax.swing.JRadioButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.String;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
public class findview extends JFrame {
    JPanel mainpanel = new JPanel();   //主界面和layout
    BorderLayout mainlayout = new BorderLayout();
    JPanel contentPane=(JPanel) getContentPane();   //必须extend Jframe
    FlowLayout xinxilayout = new FlowLayout(FlowLayout.LEFT );
    public JFormattedTextField tfDate;   //查询日期的框
    JPanel xinxipanel = new JPanel();
    CardLayout cardlayout = new CardLayout();  //cardpanel 定义
    JPanel Cpanel = new JPanel(cardlayout);
    JPanel search = new JPanel();  //查询的panel
    CardLayout dalayout = new CardLayout();  //cardpanel 定义  查看数据的界面
    JPanel dapanel = new JPanel(dalayout);   //查看数据的界面
    JScrollPane tablepanel;
    JTable block;           //定义Jtable 包括初始 之后加入数据
    List<String> fname=new ArrayList<String>();   //所有文件名
    List<String> needfilename=new ArrayList<String>();//查到的文件名
    JPanel dnpanel = new JPanel();  //显示数据的界面
    JScrollPane tpanel = new JScrollPane();
    String choose = new String();  //选择的时间
    JTable datable;
   // JTable block = new JTable(); //定义table
    public void mainpanelinit(){
        mainpanel.setLayout(mainlayout);
       daviewini();
        xinxiinit();

      //  tpanel.setVisible(true);
     //   mainpanel.add(tpanel,BorderLayout.CENTER);

    }
    /*
    * 数据界面初始化
    * */
    private void daviewini(){
        datable = new JTable(39,8){
            public boolean isCellEditable(int row, int column) {return false;};  //表格重写 实现可以选中的功能
        };
        tpanel = new JScrollPane(datable);
        dapanel.add(tpanel,"dateview");
        mainpanel.add(dapanel);
    }
    /*
    * 信息界面初始化
    * */
    public void xinxiinit(){
   /*
   *设定信息界面大小
   * */
        xinxipanel.setLayout(xinxilayout);
        Dimension frameSize = contentPane.getSize();
        xinxipanel.setPreferredSize(new Dimension(300,frameSize.height));  //只能用这个改
        xinxilayout.setHgap(15);
        xinxipanel.setBackground(Color.LIGHT_GRAY);
        xinxipanel.setVisible(true);
        mainpanel.add(xinxipanel,BorderLayout.WEST);

        /*
        * 设定初始查询界面
        * */
        JLabel chaxun = new JLabel("请选择实验时间");
       // chaxun.setBounds(15, 60, 80, 30);
        initTfDate();//初始化 文本框
        JLabel bl = new JLabel("                                                                                     ");
        xinxipanel.add(bl);
        xinxipanel.add(chaxun);
        xinxipanel.add(tfDate);
        xinxipanel.add(bl);
        xinxipanel.add(Cpanel);  //加入cardpanel
        Cpanel.setPreferredSize(new Dimension(260,600));//设定cpanel大小
        Cpanel.setBackground(Color.lightGray);
        tableini();
//        block.setEnabled(false);
        //事件监听
        block.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应

                //得到选中的行列的索引值
                if(e.getClickCount() == 2) {
                    int r= block.getSelectedRow();

                    int c= block.getSelectedColumn();

                    //得到选中的单元格的值，表格中都是字符串

                    Object value= block.getValueAt(r, c);

                    String info=value.toString();
                    choose = info;
                    System.out.println(info);
                    creattable();  //创建表格
                  /*  if(tpanel==null)
                        System.out.println("null");*/


                   // javax.swing.JOptionPane.showMessageDialog(null,info);
                }
                else
                    return;
            }
        });

       cardlayout.show(Cpanel,"block");
        /*
        * 初始化cardpanel
        * */


    }
    /*
    * 实验信息的表格初始化
    * */
    public void tableini(){
       block = new JTable(25,1){
           public boolean isCellEditable(int row, int column) {return false;};  //表格重写 实现可以选中的功能
       };
      block.setPreferredSize(new Dimension(300,700));  //sheding
      //  block.getTableHeader().setVisible(false);

            block.getColumnModel().getColumn(0).setPreferredWidth(10);

        block.getColumnModel().getColumn(0).setHeaderValue("实验时间");
        JScrollPane tablepanel = new JScrollPane (block);
        tablepanel.setPreferredSize(new Dimension(260,500));  //设定JScrollpan 大小  主要是放入table
        search.setBackground(Color.lightGray);  //
        search.setPreferredSize(new Dimension(260,600));  //设定装入JScrollpan大小
        search.add(tablepanel);
        Cpanel.add(search,"block");  //加入cardpanel
        /*
        * 加入；两个button
        * */
        JButton yes = new JButton("确定");
        JButton no = new JButton("取消");
        search.add(yes);
        search.add(no);
    }
     /*
     * 获取需要文件夹的名字
     * */
     public void getfineme(String s){
        //获取所有文件
         String filepath = "D:\\saw_data";//D盘下的file文件夹的目录
          File file = new File(filepath);//File类型可以是文件也可以是文件夹
         File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
         //获取所有文件 fname中
         for (int i = 0; i < fileList.length; i++) {
             if (fileList[i].isDirectory()) {//判断是否为文件夹
                 String mm = fileList[i].getPath();
                 mm = mm.substring(12,mm.length());
                 fname.add(mm);
             }
         }
         //查询所有的符合的文件夹名 并存放
         int num = fname.size();  //文件夹的数量
       //  System.out.println(num);
         for(int i=0;i<num;i++)
         {
             if(fname.get(i).indexOf(s) != -1)
                 needfilename.add(fname.get(i));

         }
         /*int a = needfilename.size();
         for(int i=0;i<a;i++)
         {
             System.out.println(needfilename.get(i));
         }*/
         flusht();

         //清空
         fname.clear();
         needfilename.clear();
     }
     /*
     * 更新数据到信息table
     * */
     public void flusht(){
         Vector vData = new Vector();
         Vector<String> vName = new Vector<String>();
         vName.add("时间");   //列名称
         Vector vRow = new Vector();
         int n = needfilename.size();
         for(int i=0;i<n;i++)
         {
             vRow.add(needfilename.get(i));
             vData.add(vRow);
         }

         DefaultTableModel model = new DefaultTableModel(vData, vName);
         block.setModel(model);
     }
     //创建table 用于刷新数据
       public void creattable(){
           EventQueue.invokeLater(new Runnable() {
               public void run() {
                   try {
                       long startTime=System.currentTimeMillis();
                       table tb = new table();
                       //dnpanel = tb.getContentPane();
                       //定义table
                       DefaultTableModel tableModel=new DefaultTableModel(tb.queryData(),tb.getHead()){
                           public boolean isCellEditable(int row, int column)   //不可编辑
                           {
                               return false;
                           }
                       };
                       datable.setModel(tableModel);  //刷新数据
                       //mainpanel.add(tpanel,BorderLayout.CENTER);
                      // frame.setVisible(true);
                       long endTime=System.currentTimeMillis();
                       System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           });
       }
    /*
     * initTfDate 初始化JFormattedTextField tfDate;
     */
    public void initTfDate() {
        tfDate = new JFormattedTextField();
        tfDate.setPreferredSize(new Dimension(120,23));  //Borderlayout 中只能用这个
        tfDate.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent mouseevent) { //添加文本框单击事件
// TODO Auto-generated method stub
//System.out.println("------MyTest-----");
                DateChooser mDateChooser = new DateChooser();
                mDateChooser.setjFormattedTextField(tfDate);
                mDateChooser.init();
                //设置DateChooser弹出位置
                Point p = tfDate.getLocationOnScreen();
                p.y = p.y + 30;
                mDateChooser.showDateChooser(p);
                tfDate.requestFocusInWindow();
                String s = tfDate.getText();  //获取时间
                getfineme(s);
               // System.out.println(s);
            }

            public void mouseEntered(MouseEvent mouseevent) {
// TODO Auto-generated method stub
            }

            public void mouseExited(MouseEvent mouseevent) {
// TODO Auto-generated method stub
            }

            public void mousePressed(MouseEvent mouseevent) {
// TODO Auto-generated method stub
            }

            public void mouseReleased(MouseEvent mouseevent) {
// TODO Auto-generated method stub
            }
        });
    }

    /*返回 mainpanel
    * */

    public JPanel getMainpanel() { return mainpanel; }
}
