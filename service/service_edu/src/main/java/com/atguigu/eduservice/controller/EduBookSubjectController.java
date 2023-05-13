package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduBookSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/eduservice/booksubject")
public class EduBookSubjectController {
    @Autowired
    private EduBookSubjectService bookSubjectService;

    //添加图书分类
    //获取上传过来的文件，把文件内容读取过来
    @PostMapping("addBookSubject")
    public R addSubject(MultipartFile file){
        //上传过来Excel文件

        bookSubjectService.savebookSubject(file,bookSubjectService);
        return R.ok();
    }

    //图书分类列表（树形）
    @GetMapping("getAllBookSubject")
    public R getAllSubject(){

        //list集合中的泛型是一级分类
        List<OneSubject> list=bookSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

