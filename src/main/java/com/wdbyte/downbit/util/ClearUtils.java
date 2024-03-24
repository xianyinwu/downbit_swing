package com.wdbyte.downbit.util;

import java.io.IOException;

public class ClearUtils {
    public static void Clear() throws IOException, InterruptedException {
        // ...
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 清屏命令
        // ...
    }
}
