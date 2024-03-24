package com.wdbyte.downbit.name_to_code;

import com.wdbyte.downbit.Content.Content;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WriteJson {
    public static String write(String url,String name, boolean flag) {
        String cmp=readJson.read(Content.data_code_to_name);
        boolean status = cmp.contains(name);
        if(status) {
            return "error";
        }
        // Map数据转化为Json，再转换为String
        Map<String, String> Map_code_to_name= new HashMap<>();
        Map_code_to_name.put(url,name);
        Map<String, String> Map_name_to_code= new HashMap<>();
        Map_name_to_code.put(name,url);

        String data_code_to_name = new JSONObject(Map_code_to_name).toString();
        String data_name_to_code = new JSONObject(Map_name_to_code).toString();

        File jsonFile_code_to_name = new File(Content.data_code_to_name);
        File jsonFile_name_to_code = new File(Content.data_name_to_code);
        try {
            // 文件不存在就创建文件
            if (!jsonFile_code_to_name.exists()) {
                jsonFile_code_to_name.createNewFile();
            }
            if (!jsonFile_name_to_code.exists()) {
                jsonFile_name_to_code.createNewFile();
            }

            FileWriter fileWriter_code_to_name = new FileWriter(jsonFile_code_to_name.getAbsoluteFile(), flag);
            FileWriter fileWriter_name_to_code = new FileWriter(jsonFile_name_to_code.getAbsoluteFile(), flag);

            BufferedWriter bw_code_to_name = new BufferedWriter(fileWriter_code_to_name);
            BufferedWriter bw_name_to_code = new BufferedWriter(fileWriter_name_to_code);

            bw_code_to_name.write(data_code_to_name);
            bw_name_to_code.write(data_name_to_code);

            bw_code_to_name.close();
            bw_name_to_code.close();
            return "success";
        } catch (IOException e) {
            return "error";
        }
    }
}
