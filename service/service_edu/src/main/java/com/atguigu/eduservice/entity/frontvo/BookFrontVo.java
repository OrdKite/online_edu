package com.atguigu.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookFrontVo {

    @ApiModelProperty(value = "图书名称")
    private String title;

    @ApiModelProperty(value = "作者id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
