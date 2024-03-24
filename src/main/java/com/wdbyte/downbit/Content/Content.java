package com.wdbyte.downbit.Content;

import java.util.HashSet;

public class Content {

    public static double MB_SIZE=1024d*1024d;
    // 临时文件后缀
    public static String FILE_TEMP_SUFFIX = ".temp";

    // 下载线程数量
    public static int DOWNLOAD_THREAD_NUM = 5;
    /**
     * 每次读取的数据块大小
     */
    public static int BYTE_SIZE = 1024 * 100;

    // 支持的 URL 协议
    public static HashSet<String> PROTOCAL_SET = new HashSet();

    static {
        PROTOCAL_SET.add("thunder://");
        PROTOCAL_SET.add("http://");
        PROTOCAL_SET.add("https://");
        PROTOCAL_SET.add("magnet");
    }

    public static String endpoint = "";
    public static String accessKeyId = "";
    public static String accessKeySecret = "";
    public static String bucketName = "";

    public static String msg="功能：1.查询阿里云文件目录；2.下载阿里云文件；3.下载其他资源；4.删除指定阿里云文件；5.上传文件到阿里云盘；6.拆分视频；7.在线浏览的视频；8.在线浏览图片";

    public static String data_code_to_name="data_code_to_name.json";
    public static String data_name_to_code="data_name_to_code.json";



    public static String img_htmlFilePath = "E:/xunleidownload/down-bit-master/down-bit-master/src/main/java/com/wdbyte/downbit/picture/img.html";

    public static String video_htmlFilePath = "E:/xunleidownload/down-bit-master/down-bit-master/src/main/java/com/wdbyte/downbit/video/video_player.html";
}
