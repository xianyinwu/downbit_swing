package com.wdbyte.downbit.demo;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.name_to_code.deleteJson;
import com.wdbyte.downbit.name_to_code.download_trans;

//删除文件
public class DeleteFileDemo {
    public static void DeleteFile(String arg) throws Exception {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(Content.accessKeyId, Content.accessKeySecret);
        String objectName = download_trans.parseJson_trans(arg);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(Content.endpoint, credentialsProvider);

        deleteJson.json_del(arg,objectName);//删除json
        try {
            // 删除文件。
            ossClient.deleteObject(Content.bucketName, objectName);
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return;
    }
}
