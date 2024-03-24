package com.wdbyte.downbit.name_to_code;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class download_trans {//用于从阿里云下载文件时文件名转换
    public static String parseJson_trans(String Key) throws IOException {//用来实现功能1&4的转换
        String filepath="./data_name_to_code.json";
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
                    String value = jsonObject.getString((String)key);
                    map.put((String)key, value);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String trans= (String) map.get(Key);
        return trans;
    }
}
