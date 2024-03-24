package com.wdbyte.downbit.swing;

import com.wdbyte.downbit.demo.AliyunUpload;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * logger是一个日志文件，记录相应的操作
 */
class FileTextDAL_swing implements TextDAL_swing {
    @Override
    public String read(File file) {
        logger.log(Level.INFO, "read", "reab..." + file.getPath());
        try {
            FileReader fr = new FileReader(file);
            int len = (int) file.length();
            char[] buffer = new char[len];
            fr.read(buffer, 0, len);
            fr.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, null, e);
        }
        return "";
    }

    @Override
    public void save(File file, String text) {
        logger.log(Level.INFO, "save", "save..." + file.getPath());
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(text);
            fw.close();
            AliyunUpload.AliyunUp(file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE, null, ex);
        }
        return;
    }

    Logger logger = Logger.getLogger(FileTextDAL_swing.class.getName());

    {
        try {
            FileHandler handler = new FileHandler("TextEditorApp2.log");
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException ex) {
        }
    }
}
