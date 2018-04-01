package com.control;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.view.findview;
/**
 * 测试旋转等待提示框
 * @author ZYH
 * @time:2017年7月5日
 */
public class xainshiwait extends JDialog {
    private JPanel contentPane;
    findview findc ;


    /**
     * Launch the application.
     */
   /* public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TestWaitUnit frame = new TestWaitUnit();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
   public void setFind(findview a){
       findc = a;
   }



    /**
     * Create the frame.
     */
    public void init() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        int windowWidth = this.getWidth(); //获得窗口宽
        int windowHeight = this.getHeight(); //获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示

        // JPanel panel = new JPanel();
        //contentPane.add(panel, BorderLayout.NORTH);

        //旋转等待显示
        final WaitUtil waitUtil = new WaitUtil();
        SwingWorker<String, Void> sw = new SwingWorker<String, Void>() {

            // StringBuffer sb = new StringBuffer();

            @Override
            protected String doInBackground() throws Exception {
                //-------------模拟任务开始---------------
                       /* for(int i = 0; i < 100; i++){
                            sb.append("" + i);
                            System.out.println(sb.toString());
                            Thread.sleep(50);
                        }*/
                long startTime=System.currentTimeMillis();
                       findc. searchxinxi();
                findc.creattable();  //创建表格
                long endTime=System.currentTimeMillis();
                System.out.println("程序运行时间： "+(endTime-startTime)+"ms");
                //--------------模拟任务结束--------------
                return " find view ok";
            }

            @Override
            protected void done() {
                //将耗时任务执行完得到的结果移至done来进行处理，处理完关闭旋转等待框
                try {
                    String result = get();
                   // System.out.println(result);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 关闭旋转等待框
                if(waitUtil != null) {
                    waitUtil.dispose();
                }
            }
        };
        sw.execute();
        waitUtil.setVisible(true);  //将旋转等待框WaitUnit设置为可见

    }
}
