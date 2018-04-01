package com.control;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Font;


public class ExportExcel {

    JTable table;
    FileOutputStream fos;
    JFileChooser jfc = new JFileChooser();

    public void init(JTable table) {
        this.table = table;
        jfc.addChoosableFileFilter(new FileFilter() {
            public boolean accept(File file) {
                return (file.getName().indexOf("xls") != -1);
            }

            public String getDescription() {
                return "Excel";
            }
        });

        jfc.showSaveDialog(null);
        File file = jfc.getSelectedFile();
        try {
            this.fos = new FileOutputStream(file + ".xls");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void export() {
        Workbook wb = new SXSSFWorkbook(1000);
        Sheet hs = wb.createSheet("sheet1");
        TableModel tm = table.getModel();
        int row = tm.getRowCount();
        int cloumn = tm.getColumnCount();  //表里面没有试验次数
       //  System.out.println("row " + row + "  column  " + cloumn);
        CellStyle style = wb.createCellStyle();
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        CellStyle style1 = wb.createCellStyle();
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setFillForegroundColor(HSSFColor.ORANGE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Font font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 15);
        font1.setBoldweight((short) 700);
        style1.setFont(font);

           for (int i = 0; i < row+1; i++) {
               Row hr = hs.createRow(i);
               for (int j = 0; j < cloumn; j++) {
                   if (i == 0) {
                       String value = tm.getColumnName(j);
                       // System.out.println("value " + value);
                       int len = value.length();
                       hs.setColumnWidth((short) j, (short) (len * 400));
                       HSSFRichTextString srts = new HSSFRichTextString(value);
                       Cell hc = hr.createCell((short) j);
                      // hc.setEncoding((short) 1);
                   hc.setCellStyle(style1);
                    hc.setCellValue(srts);
                   } else {
                       // System.out.println("vlue  " + tm.getValueAt(i - 1, j));
                       if (tm.getValueAt(i-1 , j) != null) {
                           String value = tm.getValueAt(i-1 , j).toString();
                           HSSFRichTextString srts = new HSSFRichTextString(value);
                           Cell hc = hr.createCell((short) j);
                           //  hc.setEncoding((short) 1);
                           // hc.setCellStyle(style);

                           if (value.equals("") || value == null) {
                               hc.setCellValue(new HSSFRichTextString(""));
                           } else {
                               hc.setCellValue(srts);
                           }
                       }
                   }
               }
           }



        try {
            wb.write(fos);
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}