package com.wdbyte.downbit.util;

import com.wdbyte.downbit.demo.DownloadMain;
import com.wdbyte.downbit.thread.LogThread;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class FileUtils {//文件操作工具类

    public static long getFileContentLength(String name) {//获取文件内容长度
        File file = new File(name);
        return file.exists() && file.isFile() ? file.length() : 0;
    }

    public static void download(String url) throws Exception {//下载文件

        String fileName = HttpUtls.getHttpFileName(url);
        long localFileSize = getFileContentLength(fileName);
        // 获取网络文件具体大小
        long httpFileContentLength = HttpUtls.getHttpFileContentLength(url);

        if (localFileSize >= httpFileContentLength) {
            LogUtils.info("{}已经下载完毕，无需重新下载", fileName);
            return;
        }

        //Future表示一个可能还没有完成的异步任务的结果，针对这个结果可以添加Callback以便在任务执行成功或失败后作出相应的操作
        //这行代码创建了一个List对象，该列表可以存储Future<Boolean>类型的元素。
        // Future代表一个可能还没有完成的计算任务，Boolean表示任务的返回值类型，表示任务是否完成成功。
        // 通过List<Future<Boolean>>，我们可以将多个Future<Boolean>对象存储到列表中，并对它们进行管理和处理。
        //在这段代码中，futureList被用作存储多个下载线程的Future对象。
        // 这些线程的执行结果（即是否下载成功）将作为Boolean类型的返回值存储在Future<Boolean>对象中。
        // 通过将这些Future<Boolean>对象添加到futureList中，我们可以轻松地追踪和处理多个下载线程的执行结果。
        List<Future<Boolean>> futureList = new ArrayList<>();

        LogUtils.info("开始下载文件 {}", fileName);

        LogUtils.info("开始下载时间 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
        long startTime = System.currentTimeMillis();

        // 任务切分
        SplitUtils.splitDownload(url, futureList);

        LogThread logThread = new LogThread(httpFileContentLength);
        Future<Boolean> future = DownloadMain.executor.submit(logThread);
        futureList.add(future);

        // 开始下载
        for (Future<Boolean> booleanFuture : futureList) {
            booleanFuture.get();
        }

        LogUtils.info("文件下载完毕 {}，本次下载耗时：{}", fileName, (System.currentTimeMillis() - startTime) / 1000 + "s");
        LogUtils.info("结束下载时间 {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));

        // 文件合并
        boolean merge = MergeUtils.merge(fileName);
        if (merge) {
            // 清理分段文件
            ClearTempUtils.clearTemp(fileName);
        }

        LogUtils.info("本次文件下载结束");
        return;
    }
}
