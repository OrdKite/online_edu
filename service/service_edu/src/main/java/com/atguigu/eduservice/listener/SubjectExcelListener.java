package com.atguigu.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectExcelListener不能交给spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    public EduSubjectService subjectService;
    public SubjectExcelListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {
    }

    //读取excel内容，一行一行进行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData==null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        EduSubject exitOneSubject = this.exitOneSubject(subjectService, subjectData.getOneSubjectName());
        if(exitOneSubject==null){//没有相同一级分类，进行添加
            exitOneSubject=new EduSubject();
            exitOneSubject.setParentId("0");
            exitOneSubject.setTitle(subjectData.getOneSubjectName());
            subjectService.save(exitOneSubject);
        }
        //添加二级分类
        //获取一级分类id值
        String pid=exitOneSubject.getId();
        EduSubject exitTwoSubject = this.exitTwoSubject(subjectService, subjectData.getTwoSubjectName(),pid);
        if(exitTwoSubject==null){//没有相同二级分类，进行添加
            exitTwoSubject=new EduSubject();
            exitTwoSubject.setParentId(pid);
            exitTwoSubject.setTitle(subjectData.getTwoSubjectName());
            subjectService.save(exitTwoSubject);
        }

    }
    //判断一级分类不能重复添加
    private EduSubject exitOneSubject(EduSubjectService subjectService,String name){
        QueryWrapper<EduSubject>wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject oneSubject=subjectService.getOne(wrapper);
        return oneSubject;
    }
    //判断二级分类不能重复添加
    private EduSubject exitTwoSubject(EduSubjectService subjectService,String name,String pid){
        QueryWrapper<EduSubject>wrapper=new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject twoSubject=subjectService.getOne(wrapper);
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
