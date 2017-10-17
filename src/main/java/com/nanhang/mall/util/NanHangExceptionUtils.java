package com.nanhang.mall.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 南航字符串工具类
 * @Author: ZhangQingrong
 * @Date : 2017/9/13 16:33
 */
public class NanHangExceptionUtils {

    /**
     * 将异常堆栈打印到一行
     * */
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
