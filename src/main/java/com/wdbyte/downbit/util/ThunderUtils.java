package com.wdbyte.downbit.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

/**
 * 迅雷链接转换工具
 */
public class ThunderUtils {

    private static String THUNDER = "thunder://";

    /**
     * 判断是否是迅雷链接
     *
     * @param url 链接
     * @return 如果是迅雷链接返回true，否则返回false
     */
    public static boolean isThunderLink(String url) {
        return url.startsWith(THUNDER);
    }

    /**
     * 转换成 HTTP URL
     *
     * @param url 链接
     * @return 转换后的HTTP URL
     */
    public static String toHttpUrl(String url) {
        if (!isThunderLink(url)) {
            return url;
        }
        LogUtils.info("当前链接是迅雷链接，开始转换...");
        url = url.replaceFirst(THUNDER, "");
        try {
            // base 64 转换
            url = new String(Base64.getDecoder().decode(url.getBytes()), "UTF-8");
            // url 解码
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 去头去尾
        if (url.startsWith("AA")) {
            url = url.substring(2);
        }
        if (url.endsWith("ZZ")) {
            url = url.substring(0, url.length() - 2);
        }
        LogUtils.info("当前链接是迅雷链接，转换结果:{}", url);
        return url;
    }
}
