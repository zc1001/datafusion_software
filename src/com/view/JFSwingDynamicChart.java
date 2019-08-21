/*
*在原有的基础上，添加了鼠标的功能，主要是在view包下添加了pannable
* pannablexyplot  panningchartpanel 三个类，
* 在此类重写  createXYLineChart  createRandomSeries 两个函数，对 createChart函数进行更改，同时在createUI函数中加入了
* PanningChartPanel chartPanel = new PanningChartPanel(createChart(dataset));语句
* */
package com.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.data.xy.XYSeries;

import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Random;

import static org.jfree.chart.ChartFactory.createXYLineChart;

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
    int gonum = 1;
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


         String title = new String("第"+gonum+"通道");
        series = new TimeSeries(title, Millisecond.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection(this.series);
        PanningChartPanel chartPanel = new PanningChartPanel(createChart(dataset));  //这里进行更改，添加了鼠标
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

        JFreeChart chart = createXYLineChart("测量数据折线图", // chart title
                "当前时间", // x axis label
                "动态数值变化", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL, true, // include legend
                true, // tooltips
                false // urls
        );
        XYPlot plot = (XYPlot)chart.getPlot();

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)plot.getRenderer();
        renderer.setBaseShapesVisible(false);
        renderer.setBaseShapesFilled(false);

        // change the auto tick unit selection to integer units only...
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ValueAxis axis = plot.getDomainAxis();
        //   DateAxis domainAxis = (DateAxis)plot.getDomainAxis();
//设置时间间隔和时间轴显示格式：1个月一个间隔
        // domainAxis.setTickUnit(new DateTickUnit(DateTickUnit.SECOND, 5, new SimpleDateFormat("mm:ss")));
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);
        axis = plot.getRangeAxis();

        return chart;
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
       // Millisecond now = new Millisecond();
        /*
         * 设定一个set函数，接收数字并显示
         * */
            series.addOrUpdate(new Millisecond(),a);

           // System.out.println(now);




       /*try {
            Thread.currentThread().sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
     //   System.out.println("hello  jfs"+System.currentTimeMillis());

    }
    public void setgonum(int i){
        gonum = i;
    }


    public static JFreeChart createXYLineChart(String title, String xAxisLabel, String yAxisLabel,
                                               XYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips,
                                               boolean urls)
    {
     /*
     * 这一部分对横轴进行修改 改成timeAxis
     * */
        if (orientation == null)
        {
            throw new IllegalArgumentException("Null 'orientation' argument.");
        }
        ValueAxis timeAxis = new DateAxis(xAxisLabel);
        timeAxis.setLowerMargin(0.02D);
        timeAxis.setUpperMargin(0.02D);
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);

        XYPlot plot = new PannableXYPlot(dataset, timeAxis, yAxis, renderer);
        plot.setOrientation(orientation);
        if (tooltips)
        {
            renderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        }
        if (urls)
        {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);
        return chart;

    }



}
