package com.wdbyte.downbit.swing;

import com.wdbyte.downbit.Content.Content;
import com.wdbyte.downbit.util.copy_rename_file;
import com.wdbyte.downbit.util.online_until;
import com.wdbyte.downbit.video.HtmlVideoUrlUpdater;

public class img_online_swing {

    public static void online_img_player(String url) throws Exception {
        String newVideoUrl= online_until.name_trans(url);//将阿里云的文件名转化为下载链接
        String videoUrlPlaceholder="{{VIDEO_URL}}";
        String newHtmlFilePath= copy_rename_file.copy_rename(Content.img_htmlFilePath);
        HtmlVideoUrlUpdater.VideoUrlUpdater(newVideoUrl,videoUrlPlaceholder,newHtmlFilePath);
        HtmlVideoUrlUpdater.openHtmlFile(newHtmlFilePath);
        //HtmlVideoUrlUpdater.VideoUrlUpdater(videoUrlPlaceholder,newVideoUrl);
        return;
    }

}
