package com.wdbyte.downbit.words;

import javax.swing.*;

public class TextEditorApp2 {
    public static void TextEditorApp() {
        //分发线程，保证不会发生死锁
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextDAL dal = new FileTextDAL();
                TextEditorFrame f = new TextEditorFrame(dal);
                f.setTitle("简单的编辑器");
                f.setSize(800, 600);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
        return;
    }
}
