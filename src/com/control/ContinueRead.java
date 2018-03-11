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
    double b[]; //
    static SerialPort serialPort; // 串口的引用
    // 堵塞队列用来存放读到的数据
    private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>();
    Queue<Double> Queue = new LinkedBlockingQueue<Double>();
    int time  = 0;  //做测试 设置time为0-7 之中 随机输出数字
    String filepath;
  // FileWriter fw;
    File file[] ;
    int num = 5;
    int tdn = 7;
    int k;   //用于double 和int转化
    FileWriter fw;
    BufferedWriter bw;
    public volatile boolean exit = false;   //判断进程是否停止
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
                 readBuffer = new byte[50];
                try {
                    numBytes = -1;
                  //   fw = new FileWriter("D:\\saw_data\\2018-03-03 14-51-36\\通道.txt");
                    b =new double[20];
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                       if(numBytes>0)
                       {
                           //输出得到的数据
                           //System.out.println(numBytes);
                           String d =new String();
                           for(int i=0;i<numBytes;i++)
                           {
                               d+=String.valueOf(readBuffer[i]);
                            Queue.add((double)(readBuffer[i] - 48));//数字进入队列
                              // store((int)(readBuffer[i]-48));
                              // System.out.println((double) (readBuffer[i]-48)); //转换成double
                           }
                        //  Queue.add(bytes2Double(readBuffer));
                           //将数据写入txt中, 数据是assic
                           /*if(file ==null)
                               System.out.println("file is null 82");
                           FileWriter fw = new FileWriter(file,true);//可在后面加数据不覆盖
                           fw.write(d);
                           fw.flush();
                           fw.close();*/

                            //   fw.close();

                           //store(new String(readBuffer,"UTF-8"));
                           /*
                           * ceshi
                           * */
                           //传到JSchart中
                          // (new Thread(this)).start();
                           /*frame.addWindowListener(new WindowAdapter() {
                           @Override
                           public void windowClosing(WindowEvent windowevent) {
                               System.exit(0);
                           }
                       });*/

                           /*
                           *
                           * */
                           readBuffer = new byte[50];
                       }
                       /* if (numBytes > 0) {
                            msgQueue.add(new Date() + "真实收到的数据为：-----"
                                    + new String(readBuffer));
                            readBuffer = new byte[20];// 重新构造缓冲对象，否则有可能会影响接下来接收的数据
                        } else {
                            msgQueue.add("额------没有读到数据");
                        }*/
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
                // 判断如果COM4串口存在，就打开该串口
                if (portId.getName().equals("COM4")) {
                    try {
                        // 打开串口名字为COM_4(名字任意),延迟为2毫秒
                        serialPort = (SerialPort) portId.open("COM_4", 1000);

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
                        serialPort.setSerialPortParams(9600,
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


           //System.out.println("--------------任务处理线程运行了--------------");
           try {
               while (!exit) {
                   // 如果堵塞队列中存在数据就将其输出
                   if (Queue.size() > 20) {
                       String t = new String();
                       for (int i = 0; i < 20; i++) {
                            //  System.out.println(   Queue.peek());
                          // k = (int)(Queue.element());
                           //System.out.println(String.valueOf(Queue.element().intValue()));
                           t += String.valueOf((Queue.element()));
                          /* t = t.substring(0,t.length() - 2);
                           t+=" ";*/
                        //   System.out.println(t);
                         //  System.out.println(t);
                          // System.out.println(Queue.peek()+"  ");
                               Jchart[time].setNumber(Queue.remove());

                            // Thread.sleep(0,10);
                           TimeUnit.NANOSECONDS.sleep(1);
                       }
                       t+="\r\n";
                       try{
                           //;
                           fw = new FileWriter(file[time],true);//可在后面加数据不覆盖
                           bw = new BufferedWriter (fw);
                           bw.write(t);
                          // bw.newLine();
                           bw.flush();
                           bw.close();
                       }catch (IOException e) {
                           // TODO Auto-generated catch block
                           e.printStackTrace();
                       }
                       //


                       time++;
                       time %= 7;
                   }
               }
           }catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
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
        public void setFilepath(String path) {
            filepath = path;
            //创建txt文件
            String fpath = filepath;  //文件地址变量
            file = new File[tdn];   //此处当前的固定
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
            }
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
        }
        public  double bytes2Double(byte[] arr) {
            long value = 0;
            for (int i = 0; i < 8; i++) {
                value |= ((long) (arr[i] & 0xff)) << (8 * i);
            }
            return Double.longBitsToDouble(value);
        }
}