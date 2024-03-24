package com.wdbyte.downbit.thread;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.util.FileUtils;
import com.wdbyte.downbit.util.HttpUtls;
import com.wdbyte.downbit.util.LogUtils;

//多线程下载工具类
public class DownloadThread implements Callable<Boolean> {

    private String url;//下载链接

    private long startPos;//下载开始位置

    private Long endPos;//要下载的文件区块大小

    private Integer part;//标识多线程下载切分的第几部分

    private Long contentLenth;//文件总大小

    public DownloadThread(String url, long startPos, Long endPos, Integer part, Long contentLenth) {
        this.url = url;
        this.startPos = startPos;
        this.endPos = endPos;
        this.part = part;
        this.contentLenth = contentLenth;
    }

    @Override
    public Boolean call() throws Exception {
        if (url == null || url.trim() == "") {
            throw new RuntimeException("下载路径不正确");
        }

        // 文件名
        String httpFileName = HttpUtls.getHttpFileName(url);
        if (part != null) {
            httpFileName = httpFileName + Content.FILE_TEMP_SUFFIX + part;
        }

        // 本地文件大小
        Long localFileContentLength = FileUtils.getFileContentLength(httpFileName);
        LogThread.LOCAL_FINISH_SIZE.addAndGet(localFileContentLength);
        if (localFileContentLength >= endPos - startPos) {
            LogUtils.info("{} 已经下载完毕，无需重复下载", httpFileName);
            LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
            return true;
        }else if(localFileContentLength!=0){
            LogUtils.info("{} 开始断点续传", httpFileName);
        }

        if (endPos.equals(contentLenth)) {
            endPos = null;
        }

        HttpURLConnection httpUrlConnection = HttpUtls.getHttpUrlConnection(url, startPos + localFileContentLength, endPos);//localFileContentLength用于实现断点续传
        // 获得输入流
        try (InputStream input = httpUrlConnection.getInputStream(); BufferedInputStream bis = new BufferedInputStream(input);
             RandomAccessFile oSavedFile = new RandomAccessFile(httpFileName, "rw")) {
            //oSavedFile.seek(localFileContentLength) 是一个文件操作方法，它用于将文件指针移动到指定的位置。
            //oSavedFile 是一个 RandomAccessFile 对象，它表示一个随机访问文件。seek() 方法用于将文件指针移动到指定的位置，以便在文件中进行读取或写入操作。
            //在这段代码中，oSavedFile.seek(localFileContentLength) 是将文件指针移动到 localFileContentLength 的位置。
            // 这意味着接下来的读取或写入操作将从该位置开始。
            // 这通常用于断点续传的情况，其中 localFileContentLength 表示已经下载的文件内容长度，我们希望在这个位置继续下载文件的剩余部分。
            // 通过将文件指针移动到该位置，我们可以继续在文件中写入剩余的内容，而不会覆盖已经下载的部分。
            oSavedFile.seek(localFileContentLength);
            byte[] buffer = new byte[Content.BYTE_SIZE];
            int len = -1;
            // 读到文件末尾则返回-1
            while ((len = bis.read(buffer)) != -1) {
                oSavedFile.write(buffer, 0, len);
                LogThread.DOWNLOAD_SIZE.addAndGet(len);
            }
        } catch (FileNotFoundException e) {
            LogUtils.error("ERROR! 要下载的文件路径不存在 {} ", url);
            return false;
        } catch (Exception e) {
            LogUtils.error("下载出现异常");
            e.printStackTrace();
            return false;
        } finally {
            httpUrlConnection.disconnect();
            LogThread.DOWNLOAD_FINISH_THREAD.addAndGet(1);
        }
        return true;
    }

}
