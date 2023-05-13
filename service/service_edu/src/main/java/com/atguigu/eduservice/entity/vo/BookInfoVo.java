package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookInfoVo {
    @ApiModelProperty(value = "图书ID")
    private String id;

    @ApiModelProperty(value = "图书作者ID")
    private String teacherId;

    @ApiModelProperty(value = "图书分类ID")
    private String subjectId;

    @ApiModelProperty(value = "一级分类级ID")
    private String subjectParentId;

    @ApiModelProperty(value = "图书名字")
    private String title;

    @ApiModelProperty(value = "图书销售价格，设置为0则可免费观看")
    private BigDecimal price;//0.01提高精度

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "图书封面图片路径")
    private String cover;

    @ApiModelProperty(value = "图书简介")
    private String description;
}
