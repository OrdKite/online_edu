package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduBook;
import com.atguigu.eduservice.entity.frontvo.BookWebVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.BookPublishVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
public interface EduBookMapper extends BaseMapper<EduBook> {

    public BookPublishVo getPublishBookInfo(String bookId);

    BookWebVo getBaseBookInfo(String bookId);

    //根据课程id，编写sql语句查询课程信息
//    CourseWebVo getBaseBookInfo(String bookId);
}
