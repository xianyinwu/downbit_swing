package com.wdbyte.downbit.words;

import java.io.File;

interface TextDAL {
    String read(File file);

    void save(File file, String text);
}
