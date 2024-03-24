package com.wdbyte.downbit.util;

import com.wdbyte.downbit.name_to_code.download_trans;
import com.wdbyte.downbit.name_to_code.read_menu_json;

import java.io.IOException;

public class DownLoadAliyun {
    public static String toHttpUrl(String filename){
        try {
            filename= download_trans.parseJson_trans(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url="https://xianyinwu.oss-cn-beijing.aliyuncs.com/"+filename;
        return url;
    }

}
