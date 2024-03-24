package com.wdbyte.downbit.swing;

import java.io.File;

interface TextDAL_swing {
    String read(File file);

    void save(File file, String text);
}
