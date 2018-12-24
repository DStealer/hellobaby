package com.dstealer.hellobaby.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具连接工具类
 * Created by LiShiwu on 02/18/2017.
 */
public class DateUtil {
    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            return new SimpleDateFormat(pattern).format(date);
        } else {
            return null;
        }
    }
}
