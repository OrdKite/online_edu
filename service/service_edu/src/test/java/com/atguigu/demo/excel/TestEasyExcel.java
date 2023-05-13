package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现Excel写操作
        //1.设置写入文件夹地址和Excel名称
       // String filename="E:\\write.xlsx";

        //2.调用easyExcel里面的方法实现写操作
        //第一个参数路径名称，第二个参数实体类class
       // EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());


        //实现Excel读操作
        String filename="E:\\write.xlsx";

        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }
    //创建方法让方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data=new DemoData();
            data.setSno(i);
            data.setSname("luck"+i);
            list.add(data);
        }
        return list;
    }
}
