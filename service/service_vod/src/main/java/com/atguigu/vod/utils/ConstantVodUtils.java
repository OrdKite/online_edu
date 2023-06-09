package com.atguigu.vod.utils;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;

    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    //定义公开静态的常量
    public static String ACCESS_KEY_SECRET;
    public static String ACCESS_KEY_ID;
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=keyid;
        ACCESS_KEY_SECRET=keysecret;
    }
}
