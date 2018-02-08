package com.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;

/*
* 实现数据的曲线显示
*
*
*
* */
/*
*
* 接收数据类new 多个对象，然后在每一个类里面设定
* */
public class JFSwingDynamicChart extends JFrame  {
    public TimeSeries series;
    private double lastValue = 100.0;
    private Font font = new Font("宋书",Font.PLAIN,15);
    JPanel datapanel = new JPanel();
    BorderLayout border = new BorderLayout();
    /**
     * 构造
     */

    public JPanel getDatapanel(){
        createUI();
        //dynamicRun();
        return datapanel;
    }

    /**
     * 创建应用程序界面
     */

    public void createUI() {
        StandardChartTheme chartTheme = new StandardChartTheme("CN");
        // 设置标题字体
        chartTheme.setExtraLargeFont(font);
        // 设置图例的字体
        chartTheme.setRegularFont(font);
        chartTheme.setLargeFont(font);
        ChartFactory.setChartTheme(chartTheme);
        datapanel.setLayout(border);
       // add(datapanel);
        datapanel.setVisible(true);
        /*
        设置纵坐标
        * */
       /* NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
        numberAxis .setAutoTickUnitSelection(false);
        double unit=10d;//刻度的长度
        NumberTickUnit ntu= new NumberTickUnit(unit);
        numberAxis .setTickUnit(ntu);
        DateAxis xAxis = new DateAxis(xName);
        xAxis.setRange(start_time, end_time);
        xAxis.setAutoTickUnitSelection(false);
        xAxis.setTickUnit(new DateTickUnit(DateTickUnit.MINUTE,20));
        plot.setDomainAxis(xAxis);*/



        series = new TimeSeries("Math.random()-随机数据", Millisecond.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        ChartPanel chartPanel = new ChartPanel(createChart(dataset));
        //chartPanel.setPreferredSize(new Dimension(500, 270));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        datapanel.add(chartPanel,BorderLayout.CENTER);
        datapanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * 根据结果集构造JFreechart报表对象
     *
     * @param dataset
     * @return
     */
    public JFreeChart createChart(XYDataset dataset) {
        JFreeChart result = ChartFactory.createTimeSeriesChart("Swing动态折线图", "系统当前时间",
                "动态数值变化", dataset, true, true, false);
        XYPlot plot = (XYPlot) result.getPlot();
        ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);
        axis = plot.getRangeAxis();
        /*
        * 设定纵坐标
        * */
        axis.setRange(0.0, 300.0);
        return result;
    }

  /*  public void actionPerformed(ActionEvent e) {
    }*/

    /**
     * 动态运行
     */
    public void dynamicRun() {
        while (true) {
            double factor = 0.90 + 0.2 * Math.random();
            this.lastValue = this.lastValue * factor;
            Millisecond now = new Millisecond();
            System.out.println("h");
            /*
            * 设定一个set函数，接收数字并显示
            * */
          series.add(new Millisecond(), this.lastValue);
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void setNumber(double a){
        Millisecond now = new Millisecond();
        /*
         * 设定一个set函数，接收数字并显示
         * */
            series.add(new Millisecond(),a);



       /*try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
     //   System.out.println("hello  jfs"+System.currentTimeMillis());

    }


}
