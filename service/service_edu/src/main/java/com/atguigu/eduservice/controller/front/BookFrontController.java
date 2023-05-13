package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.client.OrdersClient;
import com.atguigu.eduservice.entity.EduBook;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.BookFrontVo;
import com.atguigu.eduservice.entity.frontvo.BookWebVo;
import com.atguigu.eduservice.entity.frontvo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduBookService;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

//import com.atguigu.eduservice.client.OrdersClient;

@RestController
@RequestMapping("/eduservice/bookfront")
//@CrossOrigin
public class BookFrontController {

    @Autowired
    private EduBookService bookService;

//    @Autowired
//    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //1 条件查询带分页图书课程
    @PostMapping("getFrontBookList/{page}/{limit}")
    public R getFrontBookList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) BookFrontVo bookFrontVo) {
        Page<EduBook> pageBook = new Page<>(page,limit);
        Map<String,Object> map = bookService.getBookFrontList(pageBook,bookFrontVo);
        //返回分页所有数据
        return R.ok().data(map);
    }

    //2 图书详情的方法
    @GetMapping("getFrontBookInfo/{bookId}")
    public R getFrontBookInfo(@PathVariable String bookId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询图书信息
        BookWebVo bookWebVo = bookService.getBaseBookInfo(bookId);

        //根据课程id和用户id查询当前课程是否已经支付过了
//        boolean buyCourse = ordersClient.isBuyCourse(bookId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("bookWebVo",bookWebVo);
    }

    //根据图书id查询图书信息
//    @PostMapping("getBookInfoOrder/{id}")
//    public CourseWebVoOrder getBookInfoOrder(@PathVariable String id) {
//        BookWebVo bookInfo = bookService.getBaseBookInfo(id);
//        BookWebVoOrder bookWebVoOrder = new BookWebVoOrder();
//        BeanUtils.copyProperties(bookInfo,courseWebVoOrder);
//        return courseWebVoOrder;
//    }
}












