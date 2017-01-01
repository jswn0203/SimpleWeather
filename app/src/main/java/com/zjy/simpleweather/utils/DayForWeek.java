package com.zjy.simpleweather.utils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by 极速蜗牛 on 2016/12/31 0031.
 */

public class DayForWeek {
    /***
     *
     * @param time 传入字符格式 2016-12-29
     * @return
     */
    public static int GetDayToWeek(String time){
        int  dayWeeek = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(format.parse(time));

            if (calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
                dayWeeek = 7;
            }else {
                dayWeeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayWeeek;
    }
}
