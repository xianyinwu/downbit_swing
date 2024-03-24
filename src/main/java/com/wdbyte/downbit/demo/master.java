package com.wdbyte.downbit.demo;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.picture.img_online;
import com.wdbyte.downbit.util.LogUtils;
import com.wdbyte.downbit.video.mp4_to_m3u8;
import com.wdbyte.downbit.util.online_until;
import com.wdbyte.downbit.words.TextEditorApp2;

import java.util.Scanner;

public class master {

    public static void main(String[] args) throws Exception {
        while (true){
            String c = null;
            if (args == null || args.length == 0) {
                for (; ; ) {
                    LogUtils.info(Content.msg);
                    LogUtils.info("请选择功能：");
                    Scanner scanner = new Scanner(System.in);
                    c = scanner.next(); // 从控制台读取输入的下载地址
                    if (c != null) {
                        break; // 如果成功读取到下载地址，则跳出循环
                    }
                }
            } else {
                c = args[0]; // 如果有参数传入，则使用参数作为下载地址
            }

            if (c.equalsIgnoreCase("1")) {
                LogUtils.info("正在查询阿里云文件目录：");
                GetBucketUtils.BucketUtils();

            } else if (c.equalsIgnoreCase("2")) {
               DownloadMain.Download(null);
            } else if (c.equalsIgnoreCase("3")) {
                DownloadMain.Download(null);
            } else if (c.equalsIgnoreCase("4")) {
                LogUtils.info("请选择需要删除的文件：");
                Scanner scanner = new Scanner(System.in);
                c = scanner.next();
                DeleteFileDemo.DeleteFile(c);
            } else if (c.equalsIgnoreCase("5")) {
                LogUtils.info("请选择需要上传的文件：");
                Scanner scanner = new Scanner(System.in);
                c = scanner.next();
                AliyunUpload.AliyunUp(c);
            }else if (c.equalsIgnoreCase("6")) {
                LogUtils.info("请选择需要拆分的视频名称：");
                Scanner scanner = new Scanner(System.in);
                c = scanner.next();
                mp4_to_m3u8.mp4_trans_m3u8(c);
            } else if (c.equalsIgnoreCase("7")) {
                LogUtils.info("请选择需要在线浏览的的视频名称或视频下载链接：");
                Scanner scanner = new Scanner(System.in);
                c = scanner.next();
                online_until.online_player(c);
            }else if (c.equalsIgnoreCase("8")) {
                LogUtils.info("请选择需要在线浏览的图片名称或下载链接：");
                Scanner scanner = new Scanner(System.in);
                c = scanner.next();
                img_online.online_img_player(c);
            }else if (c.equalsIgnoreCase("9")) {
                LogUtils.info("已打开文件编辑器");
                TextEditorApp2.TextEditorApp();
            }else{
                break;
            }
        }
    }

}
