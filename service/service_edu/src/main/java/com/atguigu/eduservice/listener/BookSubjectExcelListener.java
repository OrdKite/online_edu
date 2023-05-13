package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduBookSubject;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.BookSubjectData;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduBookSubjectService;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class BookSubjectExcelListener extends AnalysisEventListener<BookSubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public EduBookSubjectService eduBookSubjectService;
    public BookSubjectExcelListener(EduBookSubjectService eduBookSubjectService) {
        this.eduBookSubjectService = eduBookSubjectService;
    }
    public BookSubjectExcelListener() {

    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(BookSubjectData bookSubjectData, AnalysisContext analysisContext) {
        if(bookSubjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        EduBookSubject exitBookOneSubject = this.exitOneBookSubject(eduBookSubjectService, bookSubjectData.getOneBookSubjectName());
        if(exitBookOneSubject==null){//没有相同一级分类，进行添加
            exitBookOneSubject=new EduBookSubject();
            exitBookOneSubject.setParentId("0");
            exitBookOneSubject.setTitle(bookSubjectData.getOneBookSubjectName());
            eduBookSubjectService.save(exitBookOneSubject);
        }
        //添加二级分类
        //获取一级分类id值
        String pid=exitBookOneSubject.getId();
        EduBookSubject exitTwoBookSubject = this.exitTwoBookSubject(eduBookSubjectService, bookSubjectData.getTwoBookSubjectName(),pid);
        if(exitTwoBookSubject==null){//没有相同二级分类，进行添加
            exitTwoBookSubject=new EduBookSubject();
            exitTwoBookSubject.setParentId(pid);
            exitTwoBookSubject.setTitle(bookSubjectData.getTwoBookSubjectName());
            eduBookSubjectService.save(exitTwoBookSubject);
        }

    }
    //判断一级分类不能重复添加
    private EduBookSubject exitOneBookSubject(EduBookSubjectService eduBookSubjectService,String name){
        QueryWrapper<EduBookSubject>wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduBookSubject oneBookSubject=eduBookSubjectService.getOne(wrapper);
        return oneBookSubject;
    }
    //判断二级分类不能重复添加
    private EduBookSubject exitTwoBookSubject(EduBookSubjectService eduBookSubjectService,String name,String pid){
        QueryWrapper<EduBookSubject>wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduBookSubject twoBookSubject=eduBookSubjectService.getOne(wrapper);
        return twoBookSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
