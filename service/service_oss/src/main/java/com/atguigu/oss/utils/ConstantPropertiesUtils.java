package com.atguigu.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//当项目启动，spring接口，spring加载之后，执行一个接口
@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //读取配置文件中的内容
    //@Value属性注入
    @Value("${aliyun.oss.file.endpoint}")//注入站点
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")//注入id
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")//注入秘钥
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")//注入文件夹名字
    private String bucketName;

    //定义公开静态的常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
    END_POINT=endpoint;
    ACCESS_KEY_ID=keyId;
    ACCESS_KEY_SECRET=keySecret;
    BUCKET_NAME=bucketName;
    }
}
