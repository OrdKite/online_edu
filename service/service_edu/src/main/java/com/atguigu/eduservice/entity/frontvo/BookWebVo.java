package com.atguigu.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookWebVo {

    private String id;

    @ApiModelProperty(value = "图书标题")
    private String title;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "图书封面图片路径")
    private String cover;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "图书简介")
    private String description;

    @ApiModelProperty(value = "作者ID")
    private String teacherId;

    @ApiModelProperty(value = "作者姓名")
    private String teacherName;

    @ApiModelProperty(value = "作者资历,一句话说明讲师")
    private String intro;

    @ApiModelProperty(value = "作者头像")
    private String avatar;

    @ApiModelProperty(value = "课程一级类别ID")
    private String subjectLevelOneId;

    @ApiModelProperty(value = "类别一级名称")
    private String subjectLevelOne;

    @ApiModelProperty(value = "课程二级类别ID")
    private String subjectLevelTwoId;

    @ApiModelProperty(value = "类别二级名称")
    private String subjectLevelTwo;
}
