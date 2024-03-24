package com.wdbyte.downbit.demo;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.auth.*;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.name_to_code.read_menu_json;

import java.io.IOException;
import java.util.List;

public class GetBucketUtils {

    //实现了读取目录的功能
    public static void BucketUtils() throws com.aliyuncs.exceptions.ClientException {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(Content.accessKeyId, Content.accessKeySecret);
        // 设置每页列举100个文件。
        int maxKeys = 100;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(Content.endpoint, credentialsProvider);

        try {
            String nextMarker = null;
            ObjectListing objectListing;

            do {
                objectListing = ossClient.listObjects(new ListObjectsRequest(Content.bucketName).withMarker(nextMarker).withMaxKeys(maxKeys));

                List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    String trans= read_menu_json.parseJson_trans(s.getKey());
                    System.out.println("\t" + trans);//s.getKey()为String
                }
                nextMarker = objectListing.getNextMarker();

            } while (objectListing.isTruncated());
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return;
    }
}
