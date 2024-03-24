package com.wdbyte.downbit.name_to_code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class readJson {
    /**
     * 读取json文件数据
     * @param jsonPath json文件路径
     * @return 字符串
     */
    public static String read(String jsonPath) {
        File jsonFile = new File(jsonPath);
        try {
            FileReader fileReader = new FileReader(jsonFile);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                int ch = reader.read();
                if (ch != -1) {
                    sb.append((char) ch);
                } else {
                    break;
                }
            }
            fileReader.close();
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
