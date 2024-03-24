package com.wdbyte.downbit.words;

import com.wdbyte.downbit.util.LogUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class File_to_String {
    public static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            LogUtils.error("fileToBase64 failed: ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LogUtils.error("fileToBase64 close in failed: ", e);
                }
            }
        }
        return base64;
    }
}
