package com.zjy.simpleweather.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 极速蜗牛 on 2016/12/23 0023.
 */

public class Pipei {
    public static String getPipei(String str) {
        Pattern p = Pattern.compile("-?[0-9]*");
        Matcher m = p.matcher(str);
        String number = "";
        if (m.find()) {
            number = m.group();
        }
        return number;
    }
}
