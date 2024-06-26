package com.wdbyte.downbit.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日志工具类
 */
public class LogUtils {

    public static boolean DEBUG = false;

    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    /**
     * 打印信息日志
     *
     * @param msg  日志消息
     * @param arg  格式化参数
     */
    public static void info(String msg, Object... arg) {
        print(msg, " -INFO- ", arg);
    }

    /**
     * 打印错误日志
     *
     * @param msg  日志消息
     * @param arg  格式化参数
     */
    public static void error(String msg, Object... arg) {
        print(msg, " -ERROR-", arg);
    }

    /**
     * 打印调试日志
     *
     * @param msg  日志消息
     * @param arg  格式化参数
     */
    public static void debug(String msg, Object... arg) {
        if (DEBUG) {
            print(msg, " -DEBUG-", arg);
        }
    }

    private static void print(String msg, String level, Object... arg) {
        if (arg != null && arg.length > 0) {
            msg = String.format(msg.replace("{}", "%s"), arg);
        }
        String thread = Thread.currentThread().getName();
        System.out.println(LocalDateTime.now().format(dateTimeFormatter) + " " + thread + level + msg);
    }
}
