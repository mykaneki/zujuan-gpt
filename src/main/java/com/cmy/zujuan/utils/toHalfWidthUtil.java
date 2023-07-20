package com.cmy.zujuan.utils;

public class toHalfWidthUtil {
    /**
     * 全角转半角
     *
     * @param input 包含全角符号的字符串
     * @return 半角字符串
     */
    public static String toHalfWidth(String input) {
        if (input == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(input.length());
        for (char c : input.toCharArray()) {
            if (c >= 0xFF01 && c <= 0xFF5E) {
                sb.append((char) (c - 0xFEE0));
            } else if (c == 0x3000) {
                sb.append((char) 0x0020);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
