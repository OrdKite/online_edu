package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduBook;
import com.atguigu.eduservice.entity.frontvo.BookFrontVo;
import com.atguigu.eduservice.entity.frontvo.BookWebVo;
import com.atguigu.eduservice.entity.vo.BookInfoVo;
import com.atguigu.eduservice.entity.vo.BookPublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
public interface EduBookService extends IService<EduBook> {
    //添加课程基本信息的方法
    String saveBookInfo(BookInfoVo bookInfoVo);

    BookInfoVo getBookInfo(String bookId);

    void updateBookInfo(BookInfoVo bookInfoVo);

    void removeCourse(String bookId);

    BookPublishVo publishBookInfo(String id);

    Map<String, Object> getBookFrontList(Page<EduBook> pageBook, BookFrontVo bookFrontVo);

    BookWebVo getBaseBookInfo(String bookId);
}
