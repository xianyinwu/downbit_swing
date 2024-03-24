package com.wdbyte.downbit.words;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.wdbyte.downbit.demo.AliyunUpload;

public class TextEditorFrame<EditName> extends JFrame {
    File file=null;
    /**默认颜色为黑色*/
    Color color=Color.black;
    TextDAL dal=null;
    TextEditorFrame(TextDAL dal){
        this.dal=dal;
        //文本部分
        initTextPane();
        //菜单部分
        initMenu();
        //对话框部分
        initAboutDialog();
        //工具条部分
        initToolBar();
    }
    void initTextPane(){//将文本框放入又滚动对象，并加入到Frame中
        getContentPane().add(new JScrollPane(text));
        return;
    }
    JTextPane text=new JTextPane();
    /**文件选择框*/
    JFileChooser fileChooser=new JFileChooser();
    /**颜色选择对话框*/
    JColorChooser colorChooser=new JColorChooser();
    /**关于对话框*/
    JDialog about=new JDialog();
    /**菜单*/
    JMenuBar menuBar=new JMenuBar();
    /**一级菜单包括以下三个选项*/
    JMenu[] menus=new JMenu[]{new JMenu("文件"),new JMenu("编辑"),new JMenu("帮助")};
    /**设置菜单的子选项*/
    JMenuItem[][] menuItem =new JMenuItem[][]{{
            new JMenuItem("新建文件"),
            new JMenuItem("打开文件"),
            new JMenuItem("保存文件"),
            new JMenuItem("退出文件")},
            {new JMenuItem("复制文件"),
                    new JMenuItem("剪切文件"),
                    new JMenuItem("粘贴文件"),
                    new JMenuItem("颜色")},
            {new JMenuItem("关于")}
    };
    /**初始化菜单，将的三个一级菜单选项加入到menuBar中，并且给每一个子菜单添加监听事件*/
    void initMenu(){
        for(int i=0;i<menus.length;i++) {
            menuBar.add(menus[i]);
            for (int j = 0; j < menuItem[i].length; j++) {
                menus[i].add(menuItem[i][j]);
                menuItem[i][j].addActionListener(action);
            }
        }
        //将menuBar加入到面板中
        this.setJMenuBar(menuBar);
        return;
    }
    /**定义action监听事件*/
    ActionListener action=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem mi = (JMenuItem) e.getSource();
            String id = mi.getText();
            if (id.equals("新建文件")) {text.setText("");file = null;}
            else if (id.equals("打开文件")) {
                if (file != null) {fileChooser.setSelectedFile(file);}
                int returnVal = fileChooser.showOpenDialog(TextEditorFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    openFile();
                }}
            else if (id.equals("保存文件")) {
                if (file != null) {
                    fileChooser.setSelectedFile(file);
                }
                int returnVal = fileChooser.showOpenDialog(TextEditorFrame.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                    saveFile();
                }}
            else if (id.equals("退出文件")) {System.exit(0);}
            else if (id.equals("剪切文件")) {text.cut();}
            else if (id.equals("复制文件")) {text.copy();}
            else if (id.equals("粘贴文件")) {text.paste();}
            else if (id.equals("颜色")) {
                color = JColorChooser.showDialog(TextEditorFrame.this, "", color);
                text.setForeground(color);}
            else if (id.equals("关于")) {
                about.setSize(100, 50);
                about.setVisible(true);
            }
            return;
        }
    };
    /**定义保存文件的函数*/
    void saveFile(){
        String content=text.getText();
        dal.save(file,content);
        return;
    }
    /**定义打开文件的函数*/
    void openFile(){
        String content=dal.read(file);text.setText(content);
        return;
    }

    void initAboutDialog(){
        about.getContentPane().add(new JLabel("<html><body>简单的文本编辑器1.0<br>CSDN作者：偷掉月亮的阿硕<br>本片文章代码改编自Java程序设计唐大仕老师的代码，如果侵犯您的权益请联系<body></html>"));
        //更改窗口为模式对话框
        about.setModal(true);
        about.setSize(100,50);
        return;
    }
    /**定义工具栏，并且可以添加相应的图片*/
    JToolBar toolBar=new JToolBar();
    JButton[] buttons=new JButton[]{
            new JButton("",new ImageIcon("copy.jpg")),
            new JButton("",new ImageIcon("cut.jpg")),
            new JButton("",new ImageIcon("paste.jpg"))
    };
    /**定义工具按钮的相关功能，加入到组件中*/
    void initToolBar(){
        for (int i=0;i<buttons.length;i++){
            toolBar.add(buttons[i]);
        }
        buttons[0].setToolTipText("复制文件");
        buttons[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.copy();
            }
        });
        buttons[1].setToolTipText("剪切文件");
        buttons[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.cut();
            }
        });
        buttons[2].setToolTipText("粘贴文件");
        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.paste();
            }
        });
        this.getContentPane().add(toolBar,BorderLayout.NORTH);
        //当且仅当鼠标悬停在该按钮时，才会显示出该按钮的边框
        toolBar.setRollover(true);
        return;
    }
}

