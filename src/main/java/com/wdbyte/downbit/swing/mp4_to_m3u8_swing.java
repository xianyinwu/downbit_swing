package com.wdbyte.downbit.swing;

import com.wdbyte.downbit.util.ProcessCmdUtil;
import java.util.ArrayList;
import java.util.List;

public class mp4_to_m3u8_swing {
    public static void mp4_trans_m3u8(String name) {
        name="E:/xunleidownload/down-bit-master/down-bit-master/src/main/java/com/wdbyte/downbit/video/view/"+name;
        String input = name+".mp4";
        String output = name+".m3u8";
        String ts=name+"%05d.ts";
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(input);
        command.add("-hls_time");
        command.add("10");
        command.add("-hls_list_size");
        command.add("0");
        command.add("-hls_segment_filename");
        command.add(ts);
        command.add(output);
        ProcessCmdUtil.processCmd(command);
    }
}
