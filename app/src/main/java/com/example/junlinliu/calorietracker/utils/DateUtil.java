package com.example.junlinliu.calorietracker.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static String dateToString(Date date){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String dateString = calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
        return dateString;
    }
}
