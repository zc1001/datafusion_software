//目前显示时间的jtable只能显示28个左右的时间，这个还没找到原因
//这部分代码在232行左右

package com.view;
import com.model.ReadMessage;
import com.model.SqlConnection;

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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.sql.ResultSet;
import com.control.findviewEvent;
import com.control.tuxingEvent;
import com.control.exporEvent;
import com.control.xainshiwait;
public class findview extends JFrame {
    JPanel mainpanel = new JPanel();   //主界面和layout
    BorderLayout mainlayout = new BorderLayout();
    JPanel contentPane=(JPanel) getContentPane();   //必须extend Jframe
    FlowLayout xinxilayout = new FlowLayout(FlowLayout.LEFT );
    public JFormattedTextField tfDate;   //查询日期的框
    JPanel xinxipanel = new JPanel();
    CardLayout cardlayout = new CardLayout();  //cardpanel 定义
    JPanel Cpanel = new JPanel(cardlayout);
    CardLayout C_tablelayout = new CardLayout(); //定义 查看实验信息的界面
    JPanel C_tablepanel = new JPanel(C_tablelayout);  //实验信息界面
    JPanel search = new JPanel();  //查询的panel
    CardLayout dalayout = new CardLayout();  //cardpanel 定义  查看数据的界面
    JPanel dapanel = new JPanel(dalayout);   //查看数据的界面 包括三种 实验信息界面、折线图界面、数据界面
    JScrollPane tablepanel;          //放入Jtable(查询实验时间用的)的界面
     JTable block;           //定义Jtable 包括初始 之后加入数据
    List<String> fname=new ArrayList<String>();   //所有文件名
    List<String> needfilename=new ArrayList<String>();//查到的文件名
    JPanel dnpanel = new JPanel();  //显示数据的界面
    JScrollPane tpanel = new JScrollPane();   //存放数据 数字形式存储的界面
    String date = new String();
    String choose = new String();  //选择的时间
    JTable datable;
    findviewEvent findviewevent = new findviewEvent();
    String datime = new String();  //实验时间用于在mysql查询数据
   public String shiyanxinxi[];   //存入实验信息
    JPanel[] tuxiangpanel;
    JPanel TXpanel = new JPanel();  //图像+控制条的和用panel
    JPanel TXzpanel = new JPanel(); //图像的总panel ，加入图像
    CardLayout TXzlayout = new CardLayout();  //布局
    Object queryd[][];   //存储数组
    JPanel control = new JPanel();  //条的panel
    tuxingEvent tuxevent ;  //条事件监听
    int tdnum ;  //获取通道数量
    xainshiwait wait = new xainshiwait(); //定义等待
    find_xinxiview Fxinxiview = new find_xinxiview();   //查看信息的界面
   // JTable block = new JTable(); //定义table

   /*
   * 查询功能的初始化
   * */

    public void mainpanelinit(){
        mainpanel.setLayout(mainlayout);
        wait.setFind(this);  //传入find
       daviewini();
        xinxiinit();
        TXinit();

      //  tpanel.setVisible(true);
     //   mainpanel.add(tpanel,BorderLayout.CENTER);

    }
    /*
    * 数据界面初始化
    * */
    private void daviewini(){
        datable = new JTable(1000,8){
            public boolean isCellEditable(int row, int column) {return false;};  //表格重写 实现可以选中的功能
        };
        tpanel = new JScrollPane(datable);
        dapanel.add(tpanel,"dateview");
        mainpanel.add(dapanel);
    }
    /*
    * 图像界面初始化
    * */
    public void TXinit(){
        //放图像界面初始化
             TXzpanel = new JPanel();
             TXzpanel.setLayout(TXzlayout);
             TXzpanel.setVisible(true);
            // TXzpanel.setBackground(Color.BLACK);
             //放控制条 图像界面初始化
             TXpanel = new JPanel();
             TXpanel.setLayout(new BorderLayout());
             //条初始化
             control = new JPanel();
             control.setPreferredSize(new Dimension(TXpanel.getWidth(),30));
             control.setBackground(Color.GRAY);
             //加入两个界面
        TXpanel.add(control,BorderLayout.NORTH);
        TXpanel.add(TXzpanel,BorderLayout.CENTER);
        dapanel.add(TXpanel,"tuxiang");
    }
    /*
    * 转换data界面
    * */
    public void changedataview(String str){
        dalayout.show(dapanel,str);  //转换界面
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
       /* JLabel bl = new JLabel("                                                                                     ");
        xinxipanel.add(bl);*/
        xinxipanel.add(chaxun);
        xinxipanel.add(tfDate);
       ///xinxipanel.add(bl);
        xinxipanel.add(Cpanel);  //加入cardpanel
        Cpanel.setPreferredSize(new Dimension(260,600));//设定cpanel大小
        Cpanel.setBackground(Color.lightGray);
        tableini();
//        block.setEnabled(false);
        //事件监听
        block.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseClicked(MouseEvent e) {//仅当鼠标单击时响应

                //得到选中的行列的索引值
                if(e.getClickCount() == 2) {  //双击时监听
                    int r= block.getSelectedRow();

                    int c= block.getSelectedColumn();

                    //得到选中的单元格的值，表格中都是字符串

                    Object value= block.getValueAt(r, c);   //获取当前选择的值  在jtable中的
                    String info=value.toString();
                    choose = info;   //确定选中的值
                   // System.out.println(info);
                    //searchxinxi();  //查询实验信息
                    wait.init();//创建表格 等待开始
                    //创建查看实验信息的界面
                    find_ixnxiview();


                    //creattable();  //创建表格
                    //flushtuxiang();//  刷新图像
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
    * 创建查看信息的界面
    * */
    public void find_ixnxiview(){
        Fxinxiview.setXinxi(shiyanxinxi);  //传入实验信息
       Fxinxiview.init();
       JPanel Csec_panel = Fxinxiview.getPanel();
       C_tablepanel.add(Csec_panel,"sec");

    }
    /*
    * 实验信息的表格初始化
    * */
    public void tableini(){
        //定义三个选择框
        findviewevent = new findviewEvent();
        exporEvent exporevent = new exporEvent();
        exporevent.setTable(datable);
        findviewevent.setTnum(tdnum);  //传入通道数量
        findviewevent.setFind(this);
        ButtonGroup group = new ButtonGroup();
        JRadioButton radio2 = new JRadioButton("查看历史");
        JRadioButton radio1 = new JRadioButton("实验信息");
        JRadioButton radio3 = new JRadioButton("折线图");
        radio1.setBackground(Color.lightGray);
        radio2.setBackground(Color.lightGray);
        radio3.setBackground(Color.lightGray);
        radio1.setActionCommand("1");
        radio2.setActionCommand("2");
        radio3.setActionCommand("3");
        group.add(radio1);
        group.add(radio2);
        group.add(radio3);
        radio1.addActionListener(findviewevent);
        radio2.addActionListener(findviewevent);
        radio3.addActionListener(findviewevent);
        search.add(radio1);
        search.add(radio2);
        search.add(radio3);

        block = new JTable(30,1){
           public boolean isCellEditable(int row, int column) {return false;};  //表格重写 实现可以选中的功能
       };
      // block.setBackground(Color.lightGray);
      block.setPreferredSize(new Dimension(300,700));  //sheding
      //  block.getTableHeader().setVisible(false);

            block.getColumnModel().getColumn(0).setPreferredWidth(10);

        block.getColumnModel().getColumn(0).setHeaderValue("实验时间");
        tablepanel = new JScrollPane (block);
        tablepanel.setPreferredSize(new Dimension(260,500));  //设定JScrollpan 大小  主要是放入table
        C_tablepanel.add(tablepanel,"first");  //把 tablepanel 变成cardpanel
        C_tablelayout.first(C_tablepanel);
        search.setBackground(Color.lightGray);
        search.setPreferredSize(new Dimension(260,600));  //设定装入JScrollpan大小
        search.add(C_tablepanel);  //加入Card layout  panel
        Cpanel.add(search,"block");  //加入cardpanel

        JButton exp = new JButton("导出Excel");
        exp.setActionCommand("exp");
        exp.addActionListener(exporevent);
        search.add(exp);

    }
     /*
     * 获取需要文件夹的名字
     * */
     public void getfineme(String s){
         s = s.replace('_','-');
        // System.out.println("获取时间"+s+"huoqushijian");
        //获取所有文件
         String filepath = "D:\\saw_data";//D盘下的file文件夹的目录
          File file = new File(filepath);//File类型可以是文件也可以是文件夹
         File[] fileList = file.listFiles();//将该目录下的所有文件放置在一个File类型的数组中
         File[] data_filelist;   //每一个日期的文件夹中的所有文件
         //获取所有文件 fname中
         for (int i = 0; i < fileList.length; i++) {
             if (fileList[i].isDirectory() && (fileList[i].getName() != "message")) {//判断是否为文件夹
                 String file_data = fileList[i].getName();  //获取日期文件夹的名字  2019-07-29
                 data_filelist = fileList[i].listFiles();  //获取日期文件夹中的所有文件
                 for(int j=0; j<data_filelist.length;j++)
                 {
                     String file_time = data_filelist[j].getName();  //获取日期文件夹中的每一个日期的名字 08_35_18
                     //System.out.println("文件夹"+file_data+file_time);
                     fname.add(file_data + "  "+file_time);
                 }
             }
         }
         //查询所有的符合的文件夹名 并存放
         int num = fname.size();  //文件夹的数量
       //  System.out.println(num);
         if(s != null)
         {
             //s 不是空 表名这时已经选中了时间
             for(int i=0;i<num;i++)
             {
                 if((fname.get(i).indexOf(s) != -1)&& fname.get(i).indexOf("message")== -1)  //字符串之中 有对应的时间但没有 message 主要是为了避免message中的信息也出现在这里面
                     needfilename.add(fname.get(i));


             }
         }
         else
         {
             //s 是空表明没有选择日期 需要所有的日期
             for(int i=0;i<num;i++)
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
         Vector<String> vName = new Vector();
         vName.add("时间");   //列名称
         Vector vRow = new Vector();
         int n = needfilename.size();

         //这段代码有问题
         //目前显示时间的jtable只能显示28个左右的时间，这个还没找到原因
         for(int i=n-1;i>=0;i--)
         {
             vRow = new Vector();
             vRow.add(needfilename.get(i));
            // System.out.println(needfilename.get(i)+"206");
            // System.out.println(vRow.get(0));
             vData.add(vRow);
         }
        //  System.out.println(vData.size());
     /*  Object[]tablename = {"时间"};
       Object[] filname = new Object[needfilename.size()];
       for(int i=0;i<needfilename.size();i++)
           filname[i] = needfilename.get(i);*/
         // System.out.println(vRow.);
         DefaultTableModel m = new DefaultTableModel(vData,vName);
         block.setModel(m);

        /* DefaultTableModel tableModel = (DefaultTableModel) block.getModel();
         for(int i=0;i<needfilename.size();i++)
              tableModel.addRow(new Object[]{needfilename.get(i)});
         //block.updateUI();
         tablepanel.validate();
         SwingUtilities.updateComponentTreeUI(block);*/

     }
     /*
     * 查询mysql中的文件，查询实验信息，主要用于之后的查询以及显示实验信息
     * 现在改成了直接在相应的文件夹之中的message文件夹中的文件查询
     * */
     public void searchxinxi(){
         datime = choose;
         //SqlConnection seach = new SqlConnection();
         shiyanxinxi = new String[8];  //存入实验信息 分别是时间、名称、备注、地点、温度、湿度、气体、 实验数量是固定
         //shiyanxinxi =seach.TheSqlConnection(datime);
         ReadMessage readmessage = new ReadMessage();  //单独开键的类 用于传送文件夹中的实验信息
         shiyanxinxi = readmessage.Readmessage(datime);
         //获取实验信息
       // System.out.println(shiyanxinxi[7]);
         tdnum = Integer.parseInt(shiyanxinxi[7]);

     }
     //创建数字table 用于刷新数据
       public void creattable(){
           EventQueue.invokeLater(new Runnable() {
               public void run() {
                   try {
                       long startTime=System.currentTimeMillis();
                     table  tb = new table();  //获取数 和列的名字
                       tb.setTnum(tdnum); //放入通道数量
                       tb.setChoose(datime);  //传送实验时间
                       tb.init();  // 数据和列名字初始化
                       //dnpanel = tb.getContentPane();
                       //定义table
                       DefaultTableModel tableModel=new DefaultTableModel(tb.queryData(),tb.getHead()){
                           public boolean isCellEditable(int row, int column)   //不可编辑
                           {
                               return false;
                           }
                       };
                       datable.setModel(tableModel);  //刷新数据
                      // queryd = new Object[tb.queryData().length][tdnum+1];
                       queryd = tb.queryData();  //获取数据 每一列是需要的数据
                       tb.finish();
                       Thread t = new Thread(new Runnable(){
                           public void run(){
                               flushtuxiang();
                           }});
                       t.start();

                       //mainpanel.add(tpanel,BorderLayout.CENTER);
                      // frame.setVisible(true);
                       long endTime=System.currentTimeMillis();
                       System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
           });
          // queryd = tb.queryData();  //获取数据 每一列是需要的数据

       }
       /*
       * 设定图像
       * */
       public void flushtuxiang(){
           control.removeAll();  //条清除控件
           //加控制条
           ButtonGroup group = new ButtonGroup();
           tuxevent = new tuxingEvent(); //创建事件监听
           tuxevent.setFind(this);  //chuandicituxiang
           for(int i=0;i<tdnum;i++)
           {
               JRadioButton radio = new JRadioButton("通道"+String.valueOf(i+1)); //设定按钮
               radio.setBackground(Color.GRAY);
               radio.setActionCommand(Integer.toString(i+1));  //设定标记
               radio.addActionListener(tuxevent);  //事件监听
               group.add(radio);
               control.add(radio);
           }
           //创建 Teechart 并传入数据
           chartinit chartview = new chartinit();
           chartview.init(tdnum,queryd);
          /* TXzpanel = chartview.getTuxiangpanel();  //huoqu panel
           TXzlayout = chartview.getTuxianglayout(); //获取布局
           TXpanel.updateUI();*/
          TXzpanel.removeAll();  //移除全部组件
          tuxiangpanel = chartview.getPanel();  //获取panel
          for(int i=0;i<tuxiangpanel.length;i++)
          {
              TXzpanel.add(tuxiangpanel[i],Integer.toString(i+1));
          }
          // JPanel panel = chartview.getPanel();
          // dapanel.add(panel,"chart");
       }
       public void changetuxiang(String s){   //换tuxiang
           TXzlayout.show(TXzpanel,s);
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
                date = s;
               // date.replace('-','_');
                getfineme(s);
               //System.out.println(s);
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
    /*cpanel 信息界面转换
    * */
    public void Cpaenl_changesec(){C_tablelayout.previous(C_tablepanel);} //查看实验信息的界面 转换
    public void Cpanel_changefir(){C_tablelayout.first(C_tablepanel);}  //转换回之前的界面
    public JPanel getMainpanel() { return mainpanel; }
}
