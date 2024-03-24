package com.wdbyte.downbit.name_to_code;

import com.wdbyte.downbit.Content.Content;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class deleteJson {
    //先把两个json读出来（map）格式，然后删除指定名称，再重新写回
    public static void json_del(String name,String code) throws IOException {//写回map
        Map<String, String> Map_code_to_name=trans(Content.data_code_to_name,name);
        Map<String, String> Map_name_to_code=trans(Content.data_name_to_code,code);

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

            FileWriter fileWriter_code_to_name = new FileWriter(jsonFile_code_to_name.getAbsoluteFile(), false);//覆盖
            FileWriter fileWriter_name_to_code = new FileWriter(jsonFile_name_to_code.getAbsoluteFile(), false);

            BufferedWriter bw_code_to_name = new BufferedWriter(fileWriter_code_to_name);
            BufferedWriter bw_name_to_code = new BufferedWriter(fileWriter_name_to_code);

            bw_code_to_name.write(data_code_to_name);
            bw_name_to_code.write(data_name_to_code);

            bw_code_to_name.close();
            bw_name_to_code.close();
            return;
        } catch (IOException e) {
            return;
        }
    }

    public static Map trans(String filepath, String del) throws IOException {//通过文件路径返回map
        BufferedReader reader = null;
        String laststr = "";
        FileInputStream fileInputStream = new FileInputStream(filepath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GB2312");
        reader = new BufferedReader(inputStreamReader);
        String tempString = null;
        while ((tempString = reader.readLine()) != null) {
            laststr += tempString;
        }
        reader.close();

        laststr = laststr.replace("}{", ",");

        // Create a Map to store the key-value pairs
        Map<String, String> map = new HashMap<>();

        try {
            // Wrap the string in a JSON array
            JSONArray jsonArray = new JSONArray("[" + laststr + "]");

            // Iterate through the JSON objects
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Extract the key-value pairs and add them to the map
                for (Object key : jsonObject.keySet()) {
                    String value = jsonObject.getString((String) key);
                    if(!value.equalsIgnoreCase(del)){//删除指定内容
                        map.put((String) key, value);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
