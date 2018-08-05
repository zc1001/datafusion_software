package com.model;


public class User {
    private int td1,td2,td3,td4,td5,td6,td7;
    int tnum = 0;  //通道数
    double num[];
    Object NUM2[];
    public int getTd1() {
        return td1;
    }
    public void setTd1(int id) {
        this.td1 = id;
    }
    public int getTd2() {
        return td2;
    }
    public void setTd2(int age) {
        this.td2 = age;
    }
    public int getTd3() {
        return td3;
    }
    public void setTd3(int name) {
        this.td3 = name;
    }
    public int getTd4() {
        return td4;
    }
    public void setTd4(int password) {
        this.td4 = password;
    }
    public int getTd5() {
        return td5;
    }
    public void setTd5(int sex) {
        this.td5 = sex;
    }
    public int getTd6() {
        return td6;
    }
    public void setTd6(int address) {
        this.td6 = address;
    }
    public int getTd7(){
        return  td6;
    }
    public void setTd7(int t){
        this.td7 = t;
    }

    public void setNum(double[] a){
        num = a;
        for(int i=0;i<tnum+1;i++)
            NUM2[i] = (Object)num[i];
    }
    public Object[] getNum(){
        return  NUM2;
    }

    public void setTnum(int i){
        tnum = i;
        num = new double[tnum+1]; //数组定义
        NUM2 = new Object[tnum+1];  //数组定义
    }


}
