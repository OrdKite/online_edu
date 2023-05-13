package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduBookSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-05-09
 */
public interface EduBookSubjectService extends IService<EduBookSubject> {

    //添加图书分类
    //获取上传过来的文件，把文件内容读取过来
    void savebookSubject(MultipartFile file, EduBookSubjectService bookSubjectService);

    //图书分类列表（树形）
    List<OneSubject> getAllOneTwoSubject();
}
