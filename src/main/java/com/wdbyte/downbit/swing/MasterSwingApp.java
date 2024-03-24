package com.wdbyte.downbit.swing;

import com.aliyuncs.exceptions.ClientException;
import com.wdbyte.downbit.util.LogUtils;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;

public class MasterSwingApp {

    private static JTextArea terminalTextArea;
    // 主方法
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    // 创建并显示GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("DownBit Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }

    // 放置组件
    private static void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // 添加按钮和对应的事件处理程序
        addButton(panel, "查询阿里云文件目录", 30, 20, e -> {
            LogUtils.info("正在查询阿里云文件目录：");
            try {
                GetBucketUtils_swing.BucketUtils();
                LogUtils.info("阿里云文件目录查询成功！");
            } catch (ClientException ex) {
                throw new RuntimeException(ex);
            }
        });

        addButton(panel, "下载阿里云文件", 30, 60, e -> {
            String filename = JOptionPane.showInputDialog("请输入要下载的文件名：");
            if (filename != null && !filename.trim().isEmpty()) {
                try {
                    DownloadMain_swing.Download(filename);
                    LogUtils.info(filename+"文件下载成功！");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton(panel, "下载其他资源", 30, 100, e -> {
            String filename = JOptionPane.showInputDialog("请输入要下载的文件链接：");
            if (filename != null && !filename.trim().isEmpty()) {
                try {
                    DownloadMain_swing.Download(filename);
                    LogUtils.info(filename+"文件下载成功！");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton(panel, "删除指定阿里云文件", 30, 140, e -> {
            String filename = JOptionPane.showInputDialog("请输入要删除的文件名：");
            if (filename != null && !filename.trim().isEmpty()) {
                LogUtils.info("选择的文件名称：" + filename);
                try {
                    DeleteFileDemo_swing.DeleteFile(filename);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton(panel, "上传文件到阿里云盘", 30, 180, e -> {
            String filename = JOptionPane.showInputDialog("请输入要上传的文件名：");
            if (filename != null && !filename.trim().isEmpty()) {
                LogUtils.info("选择的文件名称：" + filename);
                AliyunUpload_swing.AliyunUp(filename);
            }
        });

        addButton(panel, "拆分视频", 30, 220, e -> {
            String videoName = JOptionPane.showInputDialog("请输入要拆分的视频名称：");
            if (videoName != null && !videoName.trim().isEmpty()) {
                LogUtils.info("选择的视频名称：" + videoName);
                mp4_to_m3u8_swing.mp4_trans_m3u8(videoName);
            }
        });

        addButton(panel, "在线浏览视频", 30, 260, e -> {
            String videoLink = JOptionPane.showInputDialog("请输入要浏览的视频链接或名称：");
            if (videoLink != null && !videoLink.trim().isEmpty()) {
                LogUtils.info("选择的视频链接或名称：" + videoLink);
                try {
                    online_until_swing.online_player(videoLink);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton(panel, "在线浏览图片", 30, 300, e -> {
            String imageLink = JOptionPane.showInputDialog("请输入要浏览的图片链接或名称：");
            if (imageLink != null && !imageLink.trim().isEmpty()) {
                LogUtils.info("选择的图片链接或名称：" + imageLink);
                try {
                    img_online_swing.online_img_player(imageLink);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        addButton(panel, "打开文件编辑器", 30, 340, e -> {
            LogUtils.info("已打开文件编辑器");
            TextEditorApp2_swing.TextEditorApp();
        });

        addButton(panel, "退出", 30, 380, e -> {
            System.exit(0);
        });

        Font font = new Font("宋体", Font.PLAIN, 12); // 根据需要调整字体大小和样式
        terminalTextArea = new JTextArea();
        terminalTextArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(terminalTextArea);
        scrollPane.setBounds(220, 20, 320, 340);
        DefaultCaret caret = (DefaultCaret) terminalTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        panel.add(scrollPane);

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                terminalTextArea.append(new String(new byte[] {(byte) b}, "UTF-8"));
            }
        };
        System.setOut(new PrintStream(outputStream));
        System.setErr(new PrintStream(outputStream));

        System.out.println("Welcome to DownBit Application!");
    }

    // 辅助方法，用于添加按钮
    private static JButton addButton(JPanel panel, String buttonText, int x, int y, ActionListener actionListener) {
        JButton button = new JButton(buttonText);
        button.setBounds(x, y, 150, 25);
        button.addActionListener(actionListener);
        panel.add(button);
        return button;
    }
}