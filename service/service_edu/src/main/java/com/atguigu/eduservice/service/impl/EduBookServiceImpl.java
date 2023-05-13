package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduBook;
import com.atguigu.eduservice.entity.EduBookDescription;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontvo.BookFrontVo;
import com.atguigu.eduservice.entity.frontvo.BookWebVo;
import com.atguigu.eduservice.entity.vo.BookInfoVo;
import com.atguigu.eduservice.entity.vo.BookPublishVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduBookMapper;
import com.atguigu.eduservice.service.EduBookDescriptionService;
import com.atguigu.eduservice.service.EduBookService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
@Service
public class EduBookServiceImpl extends ServiceImpl<EduBookMapper, EduBook> implements EduBookService {

    //图书描述注入
    @Autowired
    private EduBookDescriptionService bookDescriptionService;

    @Override
    public String saveBookInfo(BookInfoVo bookInfoVo) {
        //1 向课程表添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduBook eduBook = new EduBook();
        BeanUtils.copyProperties(bookInfoVo,eduBook);
        int insert = baseMapper.insert(eduBook);
        if(insert == 0) {
            //添加失败
            throw new GuliException(20001,"添加图书信息失败");
        }

        //获取添加之后图书id
        String cid = eduBook.getId();

        //2 向图书简介表添加课程简介
        //edu_course_description
        EduBookDescription bookDescription = new EduBookDescription();
        bookDescription.setDescription(bookInfoVo.getDescription());
        //设置描述id就是课程id
        bookDescription.setId(cid);
        bookDescriptionService.save(bookDescription);

        return cid;
    }
    //根据图书id查询课程基本信息
    @Override
    public BookInfoVo getBookInfo(String bookId) {
        //1 查询图书表
        EduBook eduBook = baseMapper.selectById(bookId);
        BookInfoVo bookInfoVo = new BookInfoVo();
        BeanUtils.copyProperties(eduBook,bookInfoVo);
        //2 查询描述表
        EduBookDescription bookDescription =bookDescriptionService.getById(bookId);
        bookInfoVo.setDescription(bookDescription.getDescription());

        return bookInfoVo;

    }
    //修改图书信息
    @Override
    public void updateBookInfo(BookInfoVo bookInfoVo) {
        //1 修改图书表
        EduBook eduBook = new EduBook();
        BeanUtils.copyProperties(bookInfoVo,eduBook);
        int update = baseMapper.updateById(eduBook);
        if(update == 0) {
            throw new GuliException(20001,"修改图书信息失败");
        }

        //2 修改描述表
        EduBookDescription description = new EduBookDescription();
        description.setId(bookInfoVo.getId());
        description.setDescription(bookInfoVo.getDescription());
        bookDescriptionService.updateById(description);
    }

    @Override
    public void removeCourse(String bookId) {

    }

    @Override
    public BookPublishVo publishBookInfo(String id) {
        //调用mapper
        BookPublishVo publishBookInfo = baseMapper.getPublishBookInfo(id);
        return publishBookInfo;
    }

    @Override
    public Map<String, Object> getBookFrontList(Page<EduBook> pageBook, BookFrontVo bookFrontVo) {
        //2 根据讲师id查询所讲课程
        QueryWrapper<EduBook> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(bookFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",bookFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(bookFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",bookFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(bookFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(bookFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(bookFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageBook,wrapper);

        List<EduBook> records = pageBook.getRecords();
        long current = pageBook.getCurrent();
        long pages = pageBook.getPages();
        long size = pageBook.getSize();
        long total = pageBook.getTotal();
        boolean hasNext = pageBook.hasNext();//下一页
        boolean hasPrevious = pageBook.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    public BookWebVo getBaseBookInfo(String bookId) {

            return baseMapper.getBaseBookInfo(bookId);
    }


}
