package com.wdbyte.downbit.swing;

import javax.swing.*;

public class TextEditorApp2_swing {
    public static void TextEditorApp() {
        //分发线程，保证不会发生死锁
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TextDAL_swing dal = new FileTextDAL_swing();
                TextEditorFrame_swing f = new TextEditorFrame_swing(dal);
                f.setTitle("简单的编辑器");
                f.setSize(800, 600);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
        return;
    }
}
