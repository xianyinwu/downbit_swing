package com.wdbyte.downbit.swing;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.util.*;
import com.wdbyte.downbit.video.HtmlVideoUrlUpdater;

public class online_until_swing {
    public static String name_trans(String video_name) throws Exception {//转化为下载链接地址
        final String url = video_name;
        String url_ret=null;
        long count = Content.PROTOCAL_SET.stream().filter(prefix -> url.startsWith(prefix)).count();
        if (count == 0) {
            LogUtils.info("阿里云存储"); // 如果下载地址不是支持的协议类型，则打印不支持的协议类型并返回
            video_name= DownLoadAliyun.toHttpUrl(video_name);
            LogUtils.info("要在线浏览的链接是:{}", video_name); // 打印要下载的链接
            url_ret= MagnetUtils.toHttpUrl(ThunderUtils.toHttpUrl(video_name)); // 调用FileUtils的download方法进行下载
            return url_ret;
        }
        LogUtils.info("要在线浏览的链接是:{}", url); // 打印要下载的链接
        url_ret=MagnetUtils.toHttpUrl(ThunderUtils.toHttpUrl(url)); // 调用FileUtils的download方法进行下载
        return url_ret;
    }

    public static void online_player(String url) throws Exception {
        String newVideoUrl= online_until_swing.name_trans(url);//将阿里云的文件名转化为下载链接
        String videoUrlPlaceholder="{{VIDEO_URL}}";
        String newHtmlFilePath= copy_rename_file.copy_rename(Content.video_htmlFilePath);
        HtmlVideoUrlUpdater.VideoUrlUpdater(newVideoUrl,videoUrlPlaceholder,newHtmlFilePath);
        HtmlVideoUrlUpdater.openHtmlFile(newHtmlFilePath);
        //HtmlVideoUrlUpdater.VideoUrlUpdater(videoUrlPlaceholder,newVideoUrl);
        return;
    }

}
