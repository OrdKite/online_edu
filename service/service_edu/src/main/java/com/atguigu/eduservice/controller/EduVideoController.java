package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    //注入vodclient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节，删除对应阿里云中的视频，先删视频再删小节
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId=eduVideo.getVideoSourceId();
//        判断小节里面是否有视频
        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id做到远程调用实现删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.ok();
    }


    //根据小节id查询
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo eduVideo = videoService.getById(id);
        return R.ok().data("video",eduVideo);
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }


}

