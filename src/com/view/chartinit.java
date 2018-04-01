package com.view;
import com.steema.teechart.TChart;
import com.steema.teechart.Zoom;
import com.steema.teechart.ZoomDirections;
import com.steema.teechart.styles.FastLine;
import com.steema.teechart.styles.Line;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
/*
* 创建图像的
* */
public class chartinit extends JFrame{
   int tnum;
   JPanel panel[];
   JPanel tuxiangpanel ;
   CardLayout tuxianglayout = new CardLayout();
   public void init(int g,Object[][] q){
       panel = new JPanel[g];
       tuxiangpanel = new JPanel();
       tuxiangpanel.setLayout(tuxianglayout);
       tnum = g;
       TChart chart[] = new TChart[tnum];
       for (int i=0;i<tnum;i++)
           chart[i] = new TChart();
       for(int i=0;i<tnum;i++){
           chart[i].addSeries(new FastLine());
       }
    //   System.out.println(tnum);
           //开线程
           new Thread(new Runnable(){
               public void run(){
                   for(int i=0;i<tnum;i++)
                   {
                       int a[] = new int[q.length];
                       for(int j=0;j<q.length;j++)
                           a[j] = (int)q[j][i+1];//存储dao a
                      // System.out.println(time);
                       chart[i].getSeries(0).add(a); //放入

                   }

               }
           }).start();

       for(int i=0;i<tnum;i++)
       {
           chart[i].getAspect().setView3D(false);
           chart[i].getAxes().getBottom().setAutomatic(true);
           chart[i].getZoom().setDirection(ZoomDirections.HORIZONTAL);
           Zoom s = new Zoom(chart[i].getChart());
           s.setActive(true);
       }
       for(int i=0;i<tnum;i++)
       {
           panel[i] = new JPanel();
           panel[i].setLayout(new BorderLayout());
           panel[i].add(chart[i],BorderLayout.CENTER);
       }
     //  panel[2].setBackground(Color.BLUE);
      /* for(int i=0;i<tnum;i++)
       {
           tuxiangpanel.add(panel[i],Integer.toString(i+1));
       }*/
       /*chart[0].getAspect().setView3D(false);
       chart[0].getSeries(0).add(as);
       chart[0].getAxes().getBottom().setAutomatic(true);
       chart[0].getZoom().setDirection(ZoomDirections.HORIZONTAL);
       Zoom s = new Zoom(chart[0].getChart());
       s.setActive(true);*/
      /* panel = new JPanel();
       panel.setLayout(new BorderLayout());
       panel.add(chart[0],BorderLayout.CENTER);*/
   }
  //获取
  /*  public JPanel getTuxiangpanel() {
        return tuxiangpanel;
    }

    public CardLayout getTuxianglayout(){
       return tuxianglayout;
    }*/

    public JPanel[] getPanel() {
        return panel;
    }
}
