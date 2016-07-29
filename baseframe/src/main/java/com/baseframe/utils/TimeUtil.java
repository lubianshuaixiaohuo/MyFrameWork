/*
 * Copyright (C) 2013 KeSiCloud 
 * 
 * FileName: TimeUtil
 * Describe: It is a utility tool class for Time.
 * Author:   XiuMingHui
 */
package com.baseframe.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class TimeUtil
{
    public static final long OneDayMillisecond = 24 * 60 * 60 * 1000;

    public final static SimpleDateFormat SdfYMD = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat SdfYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // compare is or not same day
    public static boolean isSameDay(Date day1, Date day2) {
        if (day1 == null || day2 == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(day1);
        String ds2 = sdf.format(day2);
        logUtil.d ("TimeUtil", "day1: " + ds1 + ", day2: " + ds2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBetweenTwoDate(Date date, Date dateStart, Date dateEnd) {
        if ((date.after(dateStart) && date.before(dateEnd)) || date.equals(dateStart) || date.equals(dateEnd)) {
            return true;
        }
        return false;
    }

    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance ();
        Calendar cal2 = Calendar.getInstance ();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int intervalYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        // intervalYear==0, means same year

        if (intervalYear == 0 || (intervalYear == 1 && cal2.get(Calendar.MONTH) == 11)
                || (intervalYear == -1 && cal1.get(Calendar.MONTH) == 11)) {
            int week1 = cal1.get(Calendar.WEEK_OF_YEAR);
            int week2 = cal2.get(Calendar.WEEK_OF_YEAR);
            if (week1 == week2)
                return true;
        }
        return false;
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance ();
        Calendar cal2 = Calendar.getInstance ();
        cal1.setTime(date1);
        cal2.setTime(date2);
        int intervalYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        // intervalYear==0, means same year

        if (intervalYear == 0) {
            int month1 = cal1.get(Calendar.MONTH);
            int month2 = cal2.get(Calendar.MONTH);
            if (month1 == month2)
                return true;
        }
        return false;
    }

    public static int intervalDaysOf(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long days = diff / OneDayMillisecond;// calculate interval days
        return (int) days;
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance ();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    public static Date LastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance ();
        c.add(Calendar.MONTH, 1);
        c.set (Calendar.DAY_OF_MONTH, 1);
        c.add (Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }
    
    public static int getCurrentMinute() 
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        formatter.setTimeZone(TimeZone.getTimeZone ("GMT+0"));
        Date date = new Date(System.currentTimeMillis ());
        Calendar calendar = Calendar.getInstance ();
        calendar = Calendar.getInstance ();
        calendar.setTime (date);
        return calendar.get (Calendar.MINUTE);
    }

    public static int getCurrentHour()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        formatter.setTimeZone(TimeZone.getTimeZone ("GMT+0"));
        Date date = new Date(System.currentTimeMillis ());
        Calendar calendar = Calendar.getInstance ();
        calendar = Calendar.getInstance ();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentMinute(Date date)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月dd日-HH时mm分ss秒");
        formatter.setTimeZone(TimeZone.getTimeZone ("GMT+0"));
        Calendar calendar = Calendar.getInstance ();
        calendar = Calendar.getInstance ();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }


    public static Date getDayStartTime(Date day) {
        Date start = new Date(day.getTime());
        try {
            start = SdfYMD.parse(SdfYMD.format(start));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return start;
    }

    public static Date getDayEndTime(Date day) {
        Date end = new Date(day.getTime());
        try {
            end = SdfYMD.parse(SdfYMD.format(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return end;
    }

    public static Date getCurrentDayStartTime() {
        Date now = new Date();
        try {
            now = SdfYMD.parse(SdfYMD.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public static String getCurrentYMD() {
        Date now = new Date();
        return SdfYMD.format(now);
    }

    public static Date getCurrentDayEndTime() {
        Date now = new Date();
        try {
            now = SdfYMDHMS.parse(SdfYMD.format(now) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public static Date getCurrentTime() {
        Calendar c = Calendar.getInstance ();
        return c.getTime();
    }
    
    public static String getCurrentTimeYMDHMS(){
        Date date = new Date();
        return SdfYMDHMS.format(date);
    }

    public static Date getCurrentWeekStartTime(Date date) {
        Calendar c = Calendar.getInstance ();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(SdfYMDHMS.parse(SdfYMD.format(c.getTime()) + " 00:00:00"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    public static Date getCurrentWeekEndTime(Date date) {
        Calendar c = Calendar.getInstance ();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(SdfYMDHMS.parse(SdfYMD.format(c.getTime()) + " 23:59:59"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    public static Date getCurrentMonthStartTime(Date date) {
        Calendar c = Calendar.getInstance ();
        c.setTime(date);
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            now = SdfYMD.parse(SdfYMD.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public static Date getCurrentMonthEndTime(Date date) {
        Calendar c = Calendar.getInstance ();
        c.setTime(date);
        Date now = null;
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            now = SdfYMDHMS.parse(SdfYMD.format(c.getTime()) + " 23:59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public static int getMonthGap(Date startDate, Date endDate) {
        int monthday = 0;

        Calendar starCal = Calendar.getInstance ();
        starCal.setTime(startDate);
        int sYear = starCal.get(Calendar.YEAR);
        int sMonth = starCal.get(Calendar.MONTH);

        Calendar endCal = Calendar.getInstance ();
        endCal.setTime(endDate);
        int eYear = endCal.get(Calendar.YEAR);
        int eMonth = endCal.get(Calendar.MONTH);

        monthday = ((eYear - sYear) * 12 + (eMonth - sMonth));

        return monthday;
    }

    public static boolean isInValidDate(final Date cur, final Date start, final Date end) {
        Date preStart = new Date(start.getTime() - OneDayMillisecond);
        if (cur.after(end)) {
            return false;
        } else if (cur.before(preStart)) {
            return false;
        }
        return true;
    }

    public final static int GetCurrentYear() {
        Calendar aCalendar = Calendar.getInstance ();
        int year = aCalendar.get(Calendar.YEAR);
        return year;
    }

    public final static int GetCurrentMonth() {
        Calendar aCalendar = Calendar.getInstance ();
        int month = aCalendar.get(Calendar.MONTH) + 1;
        return month;
    }

    public final static int GetCurrentDay() {
        Calendar aCalendar = Calendar.getInstance ();
        int day = aCalendar.get(Calendar.DATE);
        return day;
    }
    
    public final static String GetCurrentDayBefore30()
    {
        long time = System.currentTimeMillis () + (1000L * 60 * 60 * 24 * -6);
        String pattern = "yyyy-MM-dd";
        Date date = new Date();
        if (time > 0) {
            date.setTime(time);
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
    
    public final static String GetCurrentDayBefore7()
    {
        long time = System.currentTimeMillis () + (1000L * 60 * 60 * 24 * -7);
        String pattern = "yyyy-MM-dd";
        Date date = new Date();
        if (time > 0) {
            date.setTime(time);
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date parseFormatString2Date(String formatStr) {
        Date date = null;
        try {
            date = SdfYMD.parse(formatStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseDate2FormatString(Date date) {
        String dateString = null;
        dateString = SdfYMD.format(date);
        return dateString;
    }

    public static String parseDate2FormatString(String format, Date date) {
        if (format == null || date == null)
            return "";
        String dateStr = null;
        SimpleDateFormat SDF = new SimpleDateFormat(format);
        dateStr = SDF.format(date);
        return dateStr;
    }
    
    private static final long HOURS_IN_ONE_DAY = 24;
    private static final long SECONDS_IN_ONE_DAY = 24*60*60;
    private static final long SECONDS_IN_ONE_HOUR = 60*60;
    private static final long SECONDS_IN_ONE_MINUTE = 60;
    
    public static long getHourFormseconds(final long aSeconds)
    {
        long hours = (aSeconds % SECONDS_IN_ONE_DAY)
                / SECONDS_IN_ONE_HOUR;
        return hours;
    }
    
    public static long getMinuteFormseconds(final long aSeconds)
    {
        long minutes = (aSeconds % SECONDS_IN_ONE_HOUR) / (SECONDS_IN_ONE_MINUTE);
        return minutes;
    }
    
    
    public static String getTimeFormseconds(final long aSeconds)
    {
        String aTime = null;
        long days = 0;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (aSeconds < 0){
            
        } else {
            days = aSeconds / SECONDS_IN_ONE_DAY;
            hours = (aSeconds % SECONDS_IN_ONE_DAY)
                    / SECONDS_IN_ONE_HOUR;
            minutes = (aSeconds % SECONDS_IN_ONE_HOUR) / (SECONDS_IN_ONE_MINUTE);
            seconds = aSeconds % (SECONDS_IN_ONE_MINUTE);            
        }
        
        aTime = String.format ("%1$02d:%2$02d:%3$02d", days * HOURS_IN_ONE_DAY + hours, minutes, seconds);
        
//        long hours = (aSeconds % (60 * 60 * 24)) / (60 * 60);  
//        long minutes = (aSeconds % (60 * 60)) / (60);  
//        long seconds = aSeconds - hours*60*60 - minutes*60;
//        String aTime = null;
//        try
//        {
//            String ahours = null;
//            String aminutes = null;
//            String aseconds = null;
//            if(hours<10)
//                ahours = "0"+Long.toString(hours);
//            else
//                ahours = Long.toString(hours);
//            if(minutes<10)
//                aminutes = "0"+Long.toString(minutes);
//            else
//                aminutes = Long.toString(minutes);
//            if(seconds<10)
//                aseconds = "0"+Long.toString(seconds);
//            else
//                aseconds = Long.toString(seconds);
//            
//            aTime = ahours+":"+aminutes+":"+aseconds;
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
        return aTime;
    }
    
    public static String getTimeFormSystem(final long aTimeMillis)
    {
        String time = null;
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone ("GMT+8"));
            Date date = new Date(aTimeMillis);
            time = formatter.format(date);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return time;
    }
    
    public static int GetCurrentHour()
    {
        Calendar aCalendar = Calendar.getInstance ();
        int day = aCalendar.get(Calendar.HOUR_OF_DAY);
        return day;
    }
    
    public static int GetCurrentMinute()
    {
        Calendar aCalendar = Calendar.getInstance ();
        int day = aCalendar.get(Calendar.MINUTE);
        return day;
    }

    public static int GetHourFormMills(final long aMill)
    {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTimeInMillis(aMill);
        return aCalendar.get(Calendar.HOUR_OF_DAY);
    }
    
    public static int GetMinuteFormMills(final long aMill)
    {
        Calendar aCalendar = Calendar.getInstance ();
        aCalendar.setTimeInMillis(aMill);
        return aCalendar.get(Calendar.MINUTE);
    }
    
    public static int GetDayFormMills(final long aMill)
    {
        Calendar aCalendar = Calendar.getInstance ();
        aCalendar.setTimeInMillis(aMill);
        return aCalendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public static boolean IsSameDayFromMills(final long atime1,final long atime2)
    {
        Calendar aCalendar = Calendar.getInstance ();
        aCalendar.setTimeInMillis(atime1);
        int year1 = aCalendar.get(Calendar.YEAR);
        int month1 = aCalendar.get(Calendar.MONTH);
        int day1 = aCalendar.get(Calendar.DAY_OF_MONTH);
        
        Calendar aCalendar2 = Calendar.getInstance ();
        aCalendar2.setTimeInMillis(atime2);
        int year2 = aCalendar2.get(Calendar.YEAR);
        int month2 = aCalendar2.get(Calendar.MONTH);
        int day2 = aCalendar2.get(Calendar.DAY_OF_MONTH);
        
        if(year1 == year2 && month1 == month2 && day1 == day2)
            return true;
        return false;
    }
    
    public static boolean IsLessThanGivenMinute(final long atime1,final long atime2,int aValue)
    {
        Calendar aCalendar = Calendar.getInstance ();
        aCalendar.setTimeInMillis(atime1);
        int minute1 = aCalendar.get(Calendar.MINUTE);
       
        Calendar aCalendar2 = Calendar.getInstance();
        aCalendar2.setTimeInMillis(atime2);
        int minute2 = aCalendar2.get(Calendar.MINUTE);
        
        if(Math.abs (minute1 - minute2) <aValue)
            return true;
        return false;
    }
    
    public static String GetYesterday()
    {
        Calendar aCalendar = Calendar.getInstance ();
        aCalendar.setTimeInMillis(System.currentTimeMillis ());
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        
        String time = formatter.format(aCalendar.getTime());
        return time;
    }

    // 通过生日获得年龄
    public static int getAgeOfBirthday(Date birthday)
    {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday))
        {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth)
        {
            if (monthNow == monthBirth)
            {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth)
                {
                    age--;
                }
            }
            else
            {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }

    public static String getConstellationOfBirthday(int mouth, int day)
    {
        String constellation = null;

        if ((mouth == 3 && day >= 21) || (mouth == 4 && day <= 19)) {
            constellation = "白羊座";
        } else if ((mouth == 4 && day >= 20) || (mouth == 5 && day <= 20)) {
            constellation = "金牛座";
        } else if ((mouth == 5 && day >= 21) || (mouth == 6 && day <= 21)) {
            constellation = "双子座";
        } else if ((mouth == 6 && day >= 22) || (mouth == 7 && day <= 22)) {
            constellation = "巨蟹座";
        } else if ((mouth == 7 && day >= 23) || (mouth == 8 && day <= 22)) {
            constellation = "狮子座";
        } else if ((mouth == 8 && day >= 23) || (mouth == 9 && day <= 22)) {
            constellation = "处女座";
        } else if ((mouth == 9 && day >= 23) || (mouth == 10 && day <= 23)) {
            constellation = "天秤座";
        } else if ((mouth == 10 && day >= 24) || (mouth == 11 && day <= 22)) {
            constellation = "天蝎座";
        } else if ((mouth == 11 && day >= 23) || (mouth == 12 && day <= 21)) {
            constellation = "射手座";
        } else if ((mouth == 12 && day >= 22) || (mouth == 1 && day <= 19)) {
            constellation = "摩羯座";
        } else if ((mouth == 1 && day >= 20) || (mouth == 2 && day <= 18)) {
            constellation = "水瓶座";
        } else {
            constellation = "双鱼座";
        }
        return constellation;
    }

    /**
     * 获得当前时间共经过的毫秒数
     * @return
     */
    public static long getCurMillisecond(){
        Calendar cl = Calendar.getInstance();
        return cl.getTime().getTime();
    }
}
