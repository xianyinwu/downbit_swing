package com.wdbyte.downbit.util;

import com.wdbyte.downbit.Content.Content;

import java.io.File;

public class ClearTempUtils {
    /**
     * 清理临时文件
     *
     * @param fileName
     * @return
     */
    public static boolean clearTemp(String fileName) {
        LogUtils.info("开始清理临时文件 {}{}0-{}", fileName, Content.FILE_TEMP_SUFFIX, (Content.DOWNLOAD_THREAD_NUM - 1));
        for (int i = 0; i < Content.DOWNLOAD_THREAD_NUM; i++) {
            File file = new File(fileName + Content.FILE_TEMP_SUFFIX + i);
            file.delete();
        }
        LogUtils.info("临时文件清理完毕 {}{}0-{}", fileName, Content.FILE_TEMP_SUFFIX, (Content.DOWNLOAD_THREAD_NUM - 1));
        return true;
    }
}
