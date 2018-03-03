package com.control;

import gnu.io.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Queue;
import java.util.TooManyListenersException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.view.dataplay;
import com.view.JFSwingDynamicChart;

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
    int num = 5;

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
                 readBuffer = new byte[30];
                try {
                    numBytes = -1;
                    b =new double[20];
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                       if(numBytes>0)
                       {
                           //输出得到的数据
                           //System.out.println(numBytes);
                           for(int i=0;i<numBytes;i++)
                           {
                            Queue.add((double)(readBuffer[i] - 48));//数字进入队列
                              // System.out.println((double) (readBuffer[i]-48)); //转换成double
                           }
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
                           readBuffer = new byte[20];
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
                        serialPort = (SerialPort) portId.open("COM_4", 2000);

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
               while (true) {
                   // 如果堵塞队列中存在数据就将其输出
                   if (Queue.size() > 5) {
                       for (int i = 0; i < num; i++) {
                            //  System.out.println(   Queue.peek());
                               Jchart[time].setNumber(Queue.remove());
                             Thread.sleep(1);
                       }
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

}