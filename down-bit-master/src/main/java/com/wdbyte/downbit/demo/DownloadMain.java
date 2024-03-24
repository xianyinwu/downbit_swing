package com.wdbyte.downbit.demo;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.name_to_code.download_trans;
import com.wdbyte.downbit.util.*;

/**
 * 多线程下载
 * 断点续传下载 demo
 */
public class DownloadMain {
    // 下载线程池
    public static ExecutorService executor = Executors.newFixedThreadPool(Content.DOWNLOAD_THREAD_NUM + 1);

    public static void Download(String[] args) throws Exception {
        String c = null;
        if (args == null || args.length == 0) {
            for (; ; ) {
                LogUtils.info("请输入下载地址"); // 输入下载地址
                Scanner scanner = new Scanner(System.in);
                c = scanner.next(); // 从控制台读取输入的下载地址
                if (c != null) {
                    break; // 如果成功读取到下载地址，则跳出循环
                }
            }
        } else {
            c = args[0]; // 如果有参数传入，则使用参数作为下载地址
        }

        final String url = c;
        long count = Content.PROTOCAL_SET.stream().filter(prefix -> url.startsWith(prefix)).count();
        if (count == 0) {
            LogUtils.info("阿里云存储"); // 如果下载地址不是支持的协议类型，则打印不支持的协议类型并返回
            String newFileName=c;
            String oldFileName= download_trans.parseJson_trans(c);
            c= DownLoadAliyun.toHttpUrl(c);
            LogUtils.info("要下载的链接是:{}", c); // 打印要下载的链接
            new FileUtils().download(MagnetUtils.toHttpUrl(ThunderUtils.toHttpUrl(c))); // 调用FileUtils的download方法进行下载
            File oldFile = new File(oldFileName.substring(9));
            File newFile = new File(newFileName);
            if (oldFile.renameTo(newFile)) {
                System.out.println("文件重命名成功！");
            } else {
                System.out.println("文件重命名失败！");
            }
            return;
        }
        LogUtils.info("要下载的链接是:{}", url); // 打印要下载的链接
        new FileUtils().download(MagnetUtils.toHttpUrl(ThunderUtils.toHttpUrl(url))); // 调用FileUtils的download方法进行下载
        return;
    }
}
