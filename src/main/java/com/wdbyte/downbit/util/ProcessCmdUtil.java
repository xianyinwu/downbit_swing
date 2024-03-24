package com.wdbyte.downbit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ProcessCmdUtil {

    public static String processCmd(List<String> command) {
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            Process p = builder.start();
            int i = doWaitFor(p);
            p.destroy();
            return "成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "失败";
        }
    }
    /**
     * 监听ffmpeg运行过程
     * @param p
     * @return
     */
    public static  int doWaitFor(Process p) {
        InputStream in = null;
        InputStream err = null;
        int exitValue = -1; // returned to caller when p is finished
        try {
            System.out.println("comeing");
            in = p.getInputStream();
            err = p.getErrorStream();
            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while (in.available() > 0) {
                        System.out.print((char) in.read());
                    }
                    while (err.available() > 0) {
                        System.out.print((char) err.read());
                    }

                    exitValue = p.exitValue();
                    finished = true;

                } catch (IllegalThreadStateException e) {
                    Thread.currentThread().sleep(500);
                }
            }
        } catch (Exception e) {
            System.err.println("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (err != null) {
                try {
                    err.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }
}
