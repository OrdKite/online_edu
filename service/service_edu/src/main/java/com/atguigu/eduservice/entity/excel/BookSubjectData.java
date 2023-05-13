package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class BookSubjectData {
    @ExcelProperty(index = 0)
    private String oneBookSubjectName;

    @ExcelProperty(index = 1)
    private String twoBookSubjectName;
}
