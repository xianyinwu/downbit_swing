package com.wdbyte.downbit.util;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MagnetUtils {
    private static String Magnet = "magnet:?xt=urn:btih:";

    public static boolean isMagnetLink(String url) {
        return url.startsWith(Magnet);
    }

    public static String toHttpUrl(String url) throws MalformedURLException {
        if (!isMagnetLink(url)) {
            return url;
        }

        LogUtils.info("当前链接是磁力链接，开始转换...");

        // 正则表达式
        String pattern = "magnet:\\?xt=urn:btih:([A-F0-9]+)&dn=([A-Za-z0-9.]+)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(url);

        String fileName=null;
        if (m.find()) {
            String hash = m.group(1);
            fileName = m.group(2);

            System.out.println("Hash: " + hash);
            System.out.println("File Name: " + fileName);
        } else {
            System.out.println("Invalid magnet link");
        }
        String fileUrl = "" + fileName;

        LogUtils.info("当前链接是磁力链接，转换结果:{}", fileUrl);
        try {
            URL url_ = new URL(fileUrl);
            URLConnection connection = url_.openConnection();

            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            FileOutputStream out = new FileOutputStream(".");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            in.close();

            System.out.println("File downloaded successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileUrl;
    }

}
