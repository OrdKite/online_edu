package com.atguigu.demo.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//实体类
@Data//生成getset方法
public class DemoData {
    //设置Excel表头
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

}
