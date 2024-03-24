package com.wdbyte.downbit.swing;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.util.*;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程下载
 * 断点续传下载 demo
 */
public class DownloadMain_swing {
    // 下载线程池
    public static ExecutorService executor = Executors.newFixedThreadPool(Content.DOWNLOAD_THREAD_NUM + 1);

    public static void Download(String c) throws Exception {
        final String url = c;
        long count = Content.PROTOCAL_SET.stream().filter(prefix -> url.startsWith(prefix)).count();
        if (count == 0) {
            LogUtils.info("阿里云存储"); // 如果下载地址不是支持的协议类型，则打印不支持的协议类型并返回
            String newFileName=c;
            String oldFileName= download_trans_swing.parseJson_trans(c);
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
