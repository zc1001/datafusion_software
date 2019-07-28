package com.control;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.model.insertdd;
import com.model.SqlConnection;
import com.model.SqlConnection;
import com.view.AppMain;
import javax.swing.*;
import com.view.allocationview;
import com.view.newview;
import com.view.JFSwingDynamicChart;
import com.control.ContinueRead;
import com.view.dataplay;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
public class MenuBarEvent extends JFrame implements ActionListener,Runnable{
    //AppMain y = new AppMain();
    AppMain j;
    JFrame frame; //获取当前页面
    JFSwingDynamicChart Jchart[];   //接收dataplay传来的 chart实例对象
    private javax.swing.JDesktopPane JDeskTop = null;
    private String EventName = "";  //定义事件的名字
    String filename;   //定义文件路径
    String createdate; //实验开始具体的哪一天
    String create_time; //实验开始的时间   最后准确的时间为createdate+create_time
    int tdnum;    //定义通道数量
    public void setDeskTop(javax.swing.JDesktopPane deskTop) {
        this.JDeskTop = deskTop;
    }
    public void setEventName(String eventName) {
        this.EventName = eventName;
    }
    dataplay dataview;   //接收传送数据界面，可以随时更改 传送给continue read
    /*先定义set 在APPmain中定义，传过来之后，使用本地的函数调用此函数，获取APPMain，然后处理,在函数中定义可以 OK
    * */
    public AppMain setAPP(AppMain appm){
       j = appm;
       return j;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        ContinueRead cRead = new ContinueRead();          //读取串口的信息
        if (e.getActionCommand().equals("saw_allocation") || EventName.equals("saw_allocation")){
             allocationview allo = new allocationview();
            allo.setAPPmain(j);
             allo.init();
             //在allocationview中设定可见性
            // j.changebuttonvisauble(new int[]{1,2,3});

        }
        if (e.getActionCommand().equals("saw_new") || EventName.equals("saw_new")){
          newview newvie = new newview();
          newvie.setAPPmain(j);
          newvie.init();
          //newview中设定可使用性

        }
        if (e.getActionCommand().equals("saw_about") || EventName.equals("saw_about")){
            (new Thread(this)).start();
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowevent) {
                    System.exit(0);
                }
            });
        }
        if(e.getActionCommand().equals("saw_start") || EventName.equals("saw_start")){
          j.changebuttonvisauble(new int[]{});  //全部按钮可用
           //创建当前数据文件夹
            Date date = new Date();
            //获取当前的日期作为文件夹
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  //设置要获取到什么样的
            createdate = sdf.format(date);   //获取String类型的时间
            filename = new String("D:\\saw_data\\");
            filename += createdate; //filename存储当前的文件夹
            File file = new File(filename);  //创建存放数据文件夹
            //System.out.println(file.getPath());
            if(!file.exists())
            {
                file.mkdirs();
            }
            else
                System.out.println("创建"+filename+"文件夹，文件夹已存在");
           // ContinueRead cRead = new ContinueRead();          //读取串口的信息
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH_mm_ss");  //设置要获取到什么样的
            create_time = sdf2.format(date);   //获取创建的具体时间 可作为创建文件的名称
            String data_filepath = filename + "\\"+create_time;    //创建文件的名称
            cRead.setFilepath(filename,data_filepath);  //同时完成文件的创建
            cRead.setJchart(Jchart);
            cRead.setDataview(dataview);  //传送dataview dataplay类型
            cRead.setFrame(frame);
             cRead.init();   //初始化

        }
        if(e.getActionCommand().equals("saw_end") || EventName.equals("saw_end")){
            j.changebuttonvisauble(new int[]{1,2});  //开始，暂停键不可用
            //System.out.println("ok");
            cRead.finish();   //结束进程、关闭串口
            tdnum = Integer.parseInt(j.getSnum());  //得到通道数量
            //创建和当前日期有关的数据库
            SqlConnection cd = new SqlConnection();
            cd.setTdnum(tdnum);  //传送通道数量
            cd.setFilepath(filename); // 传送文件夹名
            cd.setDaname(createdate+create_time);  //传送表名
            cd.creatsawdata();  //创建用于存放试验数据的表   存放试验信息的表已经在main函数中初始化
            //实现数据上传
            insertdd inserda = new insertdd();
            inserda.setFpath(filename+"\\"+create_time);  //filename 是文件夹的的地址  createtime 是创建的时间 （文件的名称）
            inserda.setTnum(tdnum);
            inserda.setDname(createdate+create_time);  //对应的已经创建好的数据库的表名 在上面的代码已经完成数据库表的创建 使用这个表名进行数据传输
            inserda.setgas(j.s_place,j.s_temp,j.s_shi,j.s_gas,j.s_other,j.s_name);  //传送实验条件
            //上传实验条件信息
              inserda.initxinxi();
              //实验数据
            /* 下一句代码  inserda.init()是传输数据到数据库的语句，之后需要更改，暂时屏蔽*/
             inserda.init();
           // cRead.finish();

        }

        if(e.getActionCommand().equals("saw_find") || EventName.equals("saw_find")){
            j.changeviewthird();
        }

    }
    public void setFrame(JFrame f){
        frame = f;
    }
    /*
    * 获取dataplay传来的chart实例对象
    * */
    public void setJchart(JFSwingDynamicChart Jc[]){
        Jchart = Jc;
        if(Jchart ==null)
            System.out.println(" Jchart null");
        else
            System.out.println("not null");
    }
   public void setDataview(dataplay a){
        dataview = a ; //传送dataview（类型是dataplay）
   }
    /*
    * run函数
    * */
    @Override
    public void run(){
        while(true)
        {
            try {
                Jchart[2].setNumber(Math.random()*100 );
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }


}
