package com.wdbyte.downbit.video;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class HtmlVideoUrlUpdater {
    public static void VideoUrlUpdater(String newVideoUrl,String videoUrlPlaceholder,String htmlPath) {
        // HTML文件路径
        String htmlFilePath = htmlPath;
        // 新的视频URL
        //String newVideoUrl = "https://xianyinwu.oss-cn-beijing.aliyuncs.com/20240322/1711112419205622.mp4";
        // 替换视频URL的标识符（可以是你HTML中的一个特定的占位符，例如：{{VIDEO_URL}}）
        //String videoUrlPlaceholder = "{{VIDEO_URL}}";

        try {
            // 读取HTML文件内容到字符串中
            String htmlContent = new String(Files.readAllBytes(Paths.get(htmlFilePath)), StandardCharsets.UTF_8);

            // 替换视频URL
            htmlContent = htmlContent.replace(videoUrlPlaceholder, newVideoUrl);

            // 将更新后的HTML内容写回文件
            Files.write(Paths.get(htmlFilePath), htmlContent.getBytes(StandardCharsets.UTF_8));

            System.out.println("视频URL更新成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void openHtmlFile(String filePath) {
        if (Desktop.isDesktopSupported()) {
            try {
                // 创建文件实例
                File htmlFile = new File(filePath);

                // 获取系统默认桌面实例
                Desktop desktop = Desktop.getDesktop();

                // 使用默认应用程序打开文件，通常是Web浏览器
                desktop.browse(htmlFile.toURI());

                System.out.println("HTML文件已在浏览器中打开。");
            } catch (IOException e) {
                System.err.println("无法打开HTML文件：" + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("文件路径错误：" + e.getMessage());
            }
        } else {
            System.err.println("Desktop功能不支持在当前平台上运行。");
        }
    }
}