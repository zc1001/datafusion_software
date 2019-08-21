/*
*
*
* */
package com.view;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
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
import org.jfree.data.xy.XYSeriesCollection;

import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.Random;
import javax.swing.*;

public class CreateFindchart extends JFrame {
    public int channel = 0;
    public Object[][] q;
    public PanningChartPanel[] createpannel()
    {

        Font font = new Font("宋书",Font.PLAIN,15);
        PanningChartPanel[] chartPanel = new PanningChartPanel[channel];  //创建panel 用来进行的存放数据
       XYDataset[] dataset = new XYDataset[channel];  //在dataset里面加数据
        for(int i=0;i<channel;i++)
        {
            StandardChartTheme chartTheme = new StandardChartTheme("CN");
            // 设置标题字体
            chartTheme.setExtraLargeFont(font);
            // 设置图例的字体
            chartTheme.setRegularFont(font);
            chartTheme.setLargeFont(font);
            ChartFactory.setChartTheme(chartTheme);
            dataset = getdata(q,channel);   //获取dataset
            chartPanel[i] = new  PanningChartPanel(createChart(dataset[i]));

        }
        return chartPanel;
    }

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
    /*
    *
    * 添加鼠标机制的重写部分
    * */
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

        XYPlot plot = new PannableXYPlot(dataset, xAxis, yAxis, renderer);
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
    /*
    * 把数据进行存放，把数据从到dataset中，在createpanel函数中进行存储到chart，进一步进行存成panel
    *
    * */
    public XYDataset[] getdata(Object[][] a,int channel)  //参数分别为存储数据的数组，用于存放数据的dataset
    {
        XYSeriesCollection[] xySeriesCollection = new XYSeriesCollection[channel];
        for(int i=0;i<channel;i++)
        {
            // System.out.println("hello");
            XYSeries xyseries1 = new XYSeries("mv");
            for(int j=0;j<a.length;j++)   //j可以改成龙
            {
                xyseries1.add(j,(double)a[j][i+1]);  //在每一个dataset中存入数据
            }
            xySeriesCollection[i] = new XYSeriesCollection();
            xySeriesCollection[i].addSeries(xyseries1);

        }
        return xySeriesCollection;
    }
    public void setQ(Object[][] a){q = a;}   //传送相关的数组
    public void setChannel(int a){channel = a;}  //设定通道数量
}
