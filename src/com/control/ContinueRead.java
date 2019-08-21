package com.control;

import gnu.io.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Queue;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.view.dataplay;
import com.view.JFSwingDynamicChart;
import java.util.concurrent.TimeUnit;

import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.*;

public class ContinueRead extends Thread implements SerialPortEventListener { // SerialPortEventListener
    // 监听器,我的理解是独立开辟一个线程监听串口数据
    static CommPortIdentifier portId; // 串口通信管理类
    static Enumeration<?> portList; // 有效连接上的端口的枚举
    InputStream inputStream; // 从串口来的输入流
   static OutputStream outputStream;// 向串口输出的流
    JFSwingDynamicChart Jchart[];
    byte[] readBuffer;
    JFrame frame;
    int numBytes = -1;
    static SerialPort serialPort; // 串口的引用
    // 堵塞队列用来存放读到的数据
    Queue<Double> Queue = new LinkedBlockingQueue<Double>();
    String filepath;           //存放数据的文件夹 命名为当天的日期
    String data_filepath;    //存放数据的路径  命名为当天的时间
  // FileWriter fw;
   // File file[] ;
    File txt_file;  //用于存储数据文件
    int tdn = 9;
    int bytelenth = 232; //有效数据字节数组的长度,加上温度湿度
    int bytenum = 2;  //在队列中的数据的一组的长度 通道编号加数据
    int allbytelenth = 237; //总共的长度数据包
    dataplay dataview;
    public volatile boolean exit = false;   //判断进程是否停止
    int datalength = 9 ;   //数据包的长度 11个字节
    int byte_read_loc = 0;
    FileWriter fw;  //声明初始化的写入filewriter
    BufferedWriter bw;
    String com_num = "COM3",botelv = "9600",com_num2 = "COM_3";   //定义 com数  波特率  默认是这两个，之后在set函数会改
    // 堵塞队列用来存放读到的数据
    //private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();  //队列用来接收输入流转化为数字的数据 格式为$2$n
    private  BlockingQueue<Double> change_data_Queue = new LinkedBlockingQueue<Double>();
    @Override
    /**
     * SerialPort EventListene 的方法,持续监听端口上是否有数据流
     */
    public void serialEvent(SerialPortEvent event) {//

        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:// 当有可用数据时读取数据
                char[] readBuffer = new char[30];
                byte[] read = new byte[30];
                try {
                    int numBytes = -1;
                    while (inputStream.available() >= datalength) {
                        //当队列中的数据大于等于数据包的长度的时候可以进行读取
                        numBytes = inputStream.read(read);
                        //System.out.println(numBytes);
                        byte_read_loc = 0; //当前读取的byte的相对位置，
                        for(int i=0;i<numBytes;i++)
                        {
                            //转成char的格式
                            readBuffer[i] = (char)read[i];
                            //System.out.println(readBuffer[i]);
                        }

                        while(byte_read_loc<numBytes)   //当字节位置一直小于总的readeBuffer的长度
                        {
                            while((readBuffer[byte_read_loc]!='$'||readBuffer[byte_read_loc+2]!='$') && byte_read_loc < numBytes)  //找到符合数据头的数据包 查找数据包头位置
                                byte_read_loc++;
                            /*if(byte_read_loc > numBytes )
                                break; //当前位置大于readbuffer的总长度 退出本次循环*/
                            if(byte_read_loc+datalength>numBytes)  //如果当前位置加数据包的长度大于readbuffer的长度，数据包不完整 应该舍弃
                                break;
                            else
                            {
                                //开始进行数据的转换
                                int j = byte_read_loc;
                                String s = String.valueOf(readBuffer[j+1]);    //保存通道位置
                                double chanel = Double.parseDouble(s);   //通道数转化成double类型
                            //    System.out.println("通道数"+chanel);   //测试
                                change_data_Queue.add(chanel);  //加入通道数
                                char[] chanel_number = new char[6];
                                j +=  3;   //跳转到包含数据的位置
                                for(int i=0;i<6;i++)
                                    chanel_number[i] = readBuffer[j+i];
                                s  = s+ ','+ String.valueOf(chanel_number)+"\r\n";   //转换字符串
                                double number = char_to_double(chanel_number);  //转化成double
                                //System.out.println("数据"+number);       //测试
                                change_data_Queue.add(number);  //加入数字
                                StoreData(s);   //存储数据 格式为原始格式
                                byte_read_loc += datalength;

                            }


                        }
                        readBuffer = new char[30];
                        read = new byte[30];
                    }
                } catch (IOException e) {
                }
                break;
        }
    }

    /**
     *
     * 通过程序打开COM4串口，设置监听器以及相关的参数
     *
     * @return 返回1 表示端口打开成功，返回 0表示端口打开失败
     */
    public int startComPort() {
        // 通过串口通信管理类获得当前连接上的串口列表
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {

            // 获取相应串口对象
            portId = (CommPortIdentifier) portList.nextElement();

            System.out.println("设备类型：--->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
          //  System.out.println("12");
            // 判断端口类型是否为串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                // 判断如果COM4串口存在，就打开该串口  ,先打开串口，再打开软件的连接
                if (portId.getName().equals(com_num)) {       //之前的格式  "COM4"
                    try {
                        // 打开串口名字为COM_4(名字任意),延迟为2毫秒
                        serialPort = (SerialPort) portId.open(com_num2, 1000);    //之前的格式   "COM_4"
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置当前串口的输入输出流
                    try {
                        inputStream = serialPort.getInputStream();
                        outputStream = serialPort.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 给当前串口添加一个监听器
                    try {
                        serialPort.addEventListener(this);
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                        return 0;
                    }
                    // 设置监听器生效，即：当有数据时通知
                    serialPort.notifyOnDataAvailable(true);

                    // 设置串口的一些读写参数
                    try {
                        // 比特率、数据位、停止位、奇偶校验位
                        serialPort.setSerialPortParams(115200,
                                SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                        return 0;
                    }

                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        int chanel_number = 0;   //初始化通道的变量和数据的变量
        double data = 0;


        //System.out.println("--------------任务处理线程运行了--------------");

        //创建输入的文件
        /*try {
            fww = new FileWriter(file[0], true);
            bww = new BufferedWriter(fww);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        while (!exit) {
            // 如果堵塞队列中存在数据就将其输出
            if (change_data_Queue.size() >= bytenum) {

                try{
                    double a = change_data_Queue.take();  //提取double 下一步转换成int
                    chanel_number = (int)a;   //做强制转换成int
                    data = change_data_Queue.take();
                }catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }



                      /* String t = new String();
                       for (int i = 0; i < bytenum; i++) {
                            //  System.out.println(   Queue.peek());
                          // k = (int)(Queue.element());
                           //System.out.println(String.valueOf(Queue.element().intValue()));
                           t += String.valueOf((Queue.element()));
                        //  t  +=" ";  //空格做分
                          *//* t = t.substring(0,t.length() - 2);
                           t+=" ";*//*
                        //   System.out.println(t);
                         //  System.out.println(t);
                          // System.out.println(Queue.peek()+"  ");
                          System.out.println("229 "+"第"+ i+1 +"次"+Queue.element()); //输出测试
                               Jchart[time].setNumber(Queue.remove());

                            // Thread.sleep(0,10);
                           TimeUnit.NANOSECONDS.sleep(1);
                       }*/
                /*
                 * 开始数据传送到每一个表格中，开始传送*/
                /*for (int i = 0; i < tdn; i++) {
                    double tdshuju[] = new double[3]; //传送数据的数组‘
                    for (int j = 0; j < 3; j++) {
                        //System.out.println(Queue.element());
                        tdshuju[j] = Queue.remove();  //放入数据

                    }*/
                 //  System.out.println("第"+i+"通道       ");
                    datatransport(Jchart[chanel_number-1],data,chanel_number-1); //进行数据存储 通道数量从0为第一个 所以需要减法

                if(chanel_number == 10)
                try{
                   // System.out.println("温度是 在293行 第"+num+"个    "+"：    "+Queue.element());
                    dataview.textwendu.setText(String.valueOf(data));//温度
                    // Queue.remove();//温度、湿度
                   // System.out.println("湿度是 在296行 第"+num+"个    "+"：    "+Queue.element());
                   // num++; //定义计数
                    dataview.textshidu.setText(String.valueOf(Queue.remove()));  //湿度
                    //dataview.textshidu.updateUI();  //湿度内容更新
                    //dataview.textwendu.updateUI();    //温度内容更新
                }catch (Exception e){
                    e.printStackTrace();
                }

                //Queue.remove();

                      /* t+="\r\n";
                       try{
                           //;
                        // long startTime=System.currentTimeMillis();
                           *//*fw = new FileWriter(file[time],true);//可在后面加数据不覆盖
                           bw = new BufferedWriter (fw);
                           bw.write(t);*//*
                          // bw.newLine();
                           bww[time].write(t);
                         *//* bw.flush();
                           bw.close();*//*
                 *//* long endTime=System.currentTimeMillis();
                           System.out.println("程序运行时间： "+(endTime-startTime)+"ms");*//*
                       }catch (IOException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       //
                      if(num>100)
                      {
                          Thread t1 = new Thread(new Runnable(){
                              public void run(){
                                //  System.out.println("test 253 read");
                                  try{
                                      for(int i=0;i<7;i++)
                                      {
                                          bww[i].flush();
                                      }

                                  }catch (Exception e){
                                      e.printStackTrace();
                                  }

                              }
                          });
                          t1.start();
                          num = 0;
                      }


                       time++;
                       time %= 7;
                       num++;
                   }
               }
               for(int i=0;i<7;i++)
                   try {
                       bww[i].close();
                   }catch (Exception e){
                   e.printStackTrace();
                   }
*/

        /*} catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        /*for(int i=0;i<numBytes;i++)
        try {
            Jchart[time].setNumber(b[i]);
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }*/
            }
        }

            try {
                //bww.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }
    /*
    * 串口配置初始化
    * */
    public void init(){
        int k =startComPort();
        if(k==1)
        {
            exit = false;  //重新赋值
            start();
        }
        else
            JOptionPane.showMessageDialog(new JPanel(), "端口连接未成功，请确认已经插好串口设备 ", "警告",JOptionPane.WARNING_MESSAGE);
    }
 /*   public static void main(String[] args) {
        ContinueRead cRead = new ContinueRead();
        int i = cRead.startComPort();
        if (i == 1) {
            // 启动线程来处理收到的数据
            cRead.start();
            try {
                String st = "哈哈----你好";
                System.out.println("发出字节数：" + st.getBytes("gbk").length);
                outputStream.write(st.getBytes("gbk"), 0,
                        st.getBytes("gbk").length);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            return;
        }
    }*/

 public void setJchart(JFSwingDynamicChart[] j){
     Jchart = j;
 }
 public void setFrame(JFrame f){
     frame = f;
 }
        public void setFilepath(String path,String filename) {
        /*
        * 用于在特定的文件夹中创建文件，path 为文件夹的路径 命名为年月日，filename为文件，命名为年月日+时间
        * 并且创建读写的相关变量 filewriter bufferwriter
        * */
            filepath = path;
            data_filepath = filename;   //存储用于存放数据的路径
            //创建txt文件
            data_filepath = filename+".txt";;  //文件地址变量
            //System.out.println(data_filepath);
            txt_file = new File(data_filepath);
            if (!txt_file.isFile()) {
                try {
                    //System.out.println(fpath);
                    txt_file.createNewFile();
                } catch (IOException e) {
                    System.out.println("创建文件夹失败" + data_filepath);
                    e.printStackTrace();
                }
            }
            //用于创建相关的读写变量 此变量已经在全局声明，现在进行设定 只在一个文件中读写数据
            try{
                fw = new FileWriter(txt_file,true);//可在后面加数据不覆盖
                bw = new BufferedWriter (fw);  //创建bw
            }catch (IOException e){
                System.out.println("创建filewriter失败           代码位置：ContinueRead.Setfilepath");
                e.printStackTrace();
            }

            /*
            *
            * 下面的代码是之前用于创建动态的通道的之后也可以用
            * */
          /*  file = new File[tdn];   //此处当前的固定
            for(int i = 0;i<tdn;i++)   //循环 定义每一个文件，变量file[i]代表 通道i的文件
            {
                fpath = filepath+"//"+String.valueOf(i+1)+"通道"+".txt";
                file[i] = new File(fpath);  //fpath 存储相关的地址
                if (!file[i].isFile()) {
                    try {
                        //System.out.println(fpath);
                        file[i].createNewFile();
                    } catch (IOException e) {
                        System.out.println("创建文件夹失败"+fpath);
                        e.printStackTrace();
                    }
                }
            }*/
          //  filepath += "//通道.txt";
           /*file = new File(filepath);
            if (!file.isFile()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
           /* try {
                fw = new FileWriter(file);
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

        }

        /*public void store(String str){
            try {
                FileWriter fw = new FileWriter(filepath);
               //  BufferedWriter bw = new BufferedWriter(fw);
                System.out.println(str);
                fw.write(str);
                fw.flush();
               //fw.close();
            }catch (IOException e) {
                // TODO Auto-generated catch blocke.printStackTrace();
            }
        }*/
        /* public void finish(){
            try {
                fw.flush();
                fw.close();
            }catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

         }*/
        /*
        * 结束事件
        * */
        public void finish(){
            /*
            * 发送结束的信息
            * */
            exit = true;  //结束进程
            //关闭串口
            serialPort.close();
            //关闭bww
            try {
                if(bw != null)
                   bw.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    //实现六个char型的字符转换到double的实际数据 留出接口
    double char_to_double(char byte_num[])
    {
        String s = String.valueOf(byte_num);
        int num = Integer.parseInt(s,16);
        double a = Double.valueOf(num);
        return a;
    }
        /*
        * 传递dataplay的界面 为了变化湿度温度 */
        public void setDataview(dataplay a){
            dataview = a;
        }
        public void setCom_num(String s){
            if(s != null){
                com_num = s;
                com_num2 = s.substring(0,3) + '_' + s.substring(3,s.length());
            }


           // System.out.println(com_num +"    "+ com_num2);
        }
        public void setBotelv(String s){
            if(s != null)  //如果没有传入数，使用默认值
               botelv = s;
        }

        public void StoreData(String s)
        {
            //System.out.println(s);
          try{
              bw.write(s);   //存储数据
              bw.flush();
          }catch (IOException e){
              e.printStackTrace();
          }

        }
        /*
        * 处理传入的数据*/
        public void datatransport(JFSwingDynamicChart Jchart,double data,int chanel_number){
            new Thread(new Runnable() {
                @Override
                public void run() {
                   // String str = new String();  //字符串用于存储

                    try{


                         //  System.out.println(data[i]);

                            Jchart.setNumber(data); //传入数据
                            Thread.sleep(1);  //
                           // str+= String.valueOf(chanel_number);
                            //str+= ",";
                            //str += String.valueOf(data);  //放入str
                            //str += "\r\n";
                           // System.out.println("存入字符串："+str);
                        } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                   /* try{
                        bww.write(str);
                        bww.flush();  //刷新并关闭
                      //  bww.close();
                    }catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }*/
                }
            }).start();
        }

}