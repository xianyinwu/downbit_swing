package com.wdbyte.downbit.demo;


import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.name_to_code.WriteJson;
import com.wdbyte.downbit.util.HttpUtls;

import java.io.File;
import java.io.Writer;
import java.net.URL;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
public class AliyunUpload {

    public static void AliyunUp(String arg) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileName = arg;

        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 生成上传文件名
        String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;
        String objectName = sdf.format(new Date()) + "/" + finalFileName;

        int indexOf = arg.lastIndexOf("\\");
        String name= arg.substring(indexOf + 1);
        if(WriteJson.write(objectName,name,true)=="error"){
            System.out.println(name+"上传文件重复！");
            return;
        }


        File file = new File(fileName);

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentDisposition("attachment;");
        OSSClient ossClient = new OSSClient(Content.endpoint, Content.accessKeyId, Content.accessKeySecret);

        ossClient.putObject(Content.bucketName, objectName, file, meta);
        // 设置URL过期时间为1小时。
        Date expiration = new Date(System.currentTimeMillis() + 3600 * 100000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(Content.bucketName, objectName, expiration);
        ossClient.shutdown();
        System.out.println(url.toString());

        System.out.println(name+"上传成功！");
        return;
    }
}
