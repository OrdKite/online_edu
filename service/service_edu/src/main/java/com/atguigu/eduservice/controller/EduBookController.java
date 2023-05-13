package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduBook;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.BookInfoVo;
import com.atguigu.eduservice.entity.vo.BookPublishVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.service.EduBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/eduservice/book")
public class EduBookController {
    @Autowired
    private EduBookService bookService;
    //添加课程基本信息的方法

    @PostMapping("addBookInfo")
    public R addBookInfo(@RequestBody BookInfoVo bookInfoVo){
        String id=bookService.saveBookInfo(bookInfoVo);
        return R.ok().data("bookId",id);
    }

    //根据课程id查询图书基本信息
    @GetMapping("getBookInfo/{bookId}")
    public R getBookInfo(@PathVariable String bookId) {
        BookInfoVo bookInfoVo = bookService.getBookInfo(bookId);
        return R.ok().data("bookInfoVo",bookInfoVo);
    }
    //修改图书信息
    @PostMapping("updateBookInfo")
    public R updateBookInfo(@RequestBody BookInfoVo bookInfoVo) {
        bookService.updateBookInfo(bookInfoVo);
        return R.ok();
    }

    //根据图书id查询图书确认信息
    @GetMapping("getPublishBookInfo/{id}")
    public R getPublishBookInfo(@PathVariable String id) {
        BookPublishVo bookPublishVo = bookService.publishBookInfo(id);
        return R.ok().data("publishBook",bookPublishVo);
    }
//
    //图书最终发布
    //修改课程状态
    @PostMapping("publishBook/{id}")
    public R publishBook(@PathVariable String id) {
        EduBook eduBook = new  EduBook();
        eduBook.setId(id);
        eduBook.setStatus("Normal");//设置课程发布状态
        bookService.updateById(eduBook);
        return R.ok();
    }
//
    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public R getBookList() {
        List<EduBook> list = bookService.list(null);
        return R.ok().data("list",list);
    }
//
    //删除课程
    @DeleteMapping("{bookId}")
    public R deleteBook(@PathVariable String bookId) {
        bookService.removeById(bookId);
        return R.ok();
    }


}

