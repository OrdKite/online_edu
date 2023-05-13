package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws Exception {
//        String accessKeyId="LTAI5t7dHu9X4WNPKarAvPCy";
//
//        String accessKeySecret="8JmwHxbkXaIZ4vD4QsERpAboaTl5bu";
//
//        String title="6 - What If I Want to Move Faster - upload by sdk";
//
//        String fileName="E:/毕设/项目资料/1-阿里云上传测试视频/6 - What If I Want to Move Faster.mp4";
//
//        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
//        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
//        request.setPartSize(2 * 1024 * 1024L);
//        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
//        request.setTaskNum(1);
//
//        UploadVideoImpl uploader = new UploadVideoImpl();
//        UploadVideoResponse response = uploader.uploadVideo(request);
//
////        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//
//            } else {
//                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
//                System.out.print("VideoId=" + response.getVideoId() + "\n");
//                System.out.print("ErrorCode=" + response.getCode() + "\n");
//                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//            }
        getPlayAuth();

    }



    //得到播放地址
    public static void getPlayUrl()throws Exception{
        //根据视频id获取视频播放地址
        DefaultAcsClient client=InitObject.initVodClient("LTAI5t7dHu9X4WNPKarAvPCy","8JmwHxbkXaIZ4vD4QsERpAboaTl5bu");
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();

        request.setVideoId("88caf090e0bf71edbffd6723b78e0102");
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    //得到播放凭证
    public static void getPlayAuth()throws Exception{
        DefaultAcsClient client = InitObject.initVodClient("LTAI5t7dHu9X4WNPKarAvPCy", "8JmwHxbkXaIZ4vD4QsERpAboaTl5bu");
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();

        request.setVideoId("88caf090e0bf71edbffd6723b78e0102");

        response= client.getAcsResponse(request);

        System.out.println("playauth:"+response.getPlayAuth());

    }

}
