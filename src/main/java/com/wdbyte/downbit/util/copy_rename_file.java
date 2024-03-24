package com.wdbyte.downbit.util;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class copy_rename_file {
    public static String copy_rename(String htmlFilePath) throws IOException {
        Date datex = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String newHtmlFilePath="E:/xunleidownload/down-bit-master/down-bit-master/src/main/java/com/wdbyte/downbit/video/"+format.format(datex)+".html";


        FileInputStream in = new FileInputStream(new File(htmlFilePath));
        FileOutputStream out = new FileOutputStream(new File(newHtmlFilePath));
        byte[] buff = new byte[512];
        int n = 0;
        System.out.println("复制文件：" + "\n" + "源路径：" + htmlFilePath + "\n" + "目标路径："
                + newHtmlFilePath);
        while ((n = in.read(buff)) != -1) {
            out.write(buff, 0, n);
        }
        out.flush();
        in.close();
        out.close();
        System.out.println("复制完成");

        return newHtmlFilePath;
    }
}
