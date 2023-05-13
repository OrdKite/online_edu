package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduBookSubject;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.BookSubjectData;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.BookSubjectExcelListener;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduBookSubjectMapper;
import com.atguigu.eduservice.service.EduBookSubjectService;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
@Service
public class EduBookSubjectServiceImpl extends ServiceImpl<EduBookSubjectMapper, EduBookSubject> implements EduBookSubjectService {


    //添加图书分类
    //获取上传过来的文件，把文件内容读取过来
    @Override
    public void savebookSubject(MultipartFile file, EduBookSubjectService bookSubjectService) {
            try{
                //文件输入流
                InputStream in=file.getInputStream();
                EasyExcel.read(in, BookSubjectData.class,new BookSubjectExcelListener(bookSubjectService)).sheet().doRead();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
   //图书分类树形结构
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1.查询所有一级分类 select *from edu_subject where parent_id='0'改写成java语言
        QueryWrapper<EduBookSubject>wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduBookSubject>oneSubjectList=baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类  parentid != 0
        QueryWrapper<EduBookSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduBookSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) { //遍历oneSubjectList集合
            //得到oneSubjectList每个eduSubject对象
            EduBookSubject eduSubject = oneSubjectList.get(i);
            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());
            //eduSubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();
            //遍历二级分类list集合
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduBookSubject tSubject = twoSubjectList.get(m);
                //判断二级分类parentid和一级分类id是否一样
                if(tSubject.getParentId().equals(eduSubject.getId())) {
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);
        }
        return finalSubjectList;
    }


}
