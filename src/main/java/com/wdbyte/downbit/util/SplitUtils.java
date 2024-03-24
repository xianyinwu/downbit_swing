package com.wdbyte.downbit.util;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.demo.DownloadMain;
import com.wdbyte.downbit.thread.DownloadThread;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

public class SplitUtils {
    /**
     * 将下载任务切分到多个线程
     *
     * @param url
     * @param futureList
     * @throws IOException
     */
    public static void splitDownload(String url, List<Future<Boolean>> futureList) throws IOException {
        long httpFileContentLength = HttpUtls.getHttpFileContentLength(url);//获得实际任务的长度大小
        // 任务切分
        long size = httpFileContentLength / Content.DOWNLOAD_THREAD_NUM;
        long lastSize = httpFileContentLength - (httpFileContentLength / Content.DOWNLOAD_THREAD_NUM * (Content.DOWNLOAD_THREAD_NUM - 1));

        for (int i = 0; i < Content.DOWNLOAD_THREAD_NUM; i++) {
            long start = i * size;
            Long downloadWindow = (i == Content.DOWNLOAD_THREAD_NUM - 1) ? lastSize : size;//最后一个大小需要特殊处理
            Long end = start + downloadWindow;
            if (start != 0) {
                start++;
            }
            DownloadThread downloadThread = new DownloadThread(url, start, end, i, httpFileContentLength);
            Future<Boolean> future = DownloadMain.executor.submit(downloadThread);
            futureList.add(future);
        }
    }
}
