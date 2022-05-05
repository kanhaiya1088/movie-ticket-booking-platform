package com.bookmyshow.bms.utils;

import com.bookmyshow.bms.exceptions.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String SIMPLE_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_HH_MM_SS_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static Date convertStringToDate(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new BusinessException("Unable to parse date : "+ date , e.getCause());
        }
    }

    public static String convertDateToString(Date date, String format)  {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static Date getStartOfADay(final Date date) {
        Instant inst = date.toInstant();
        LocalDate localDate = inst.atZone(ZoneId.systemDefault()).toLocalDate();
        Instant dayInst = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(dayInst);
    }

    public static Date getEndOfADay(final Date date) {
        Instant instEnd = date.toInstant();
        LocalDate localDateEnd = instEnd.atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDateTime dayInstEnd = localDateEnd.atTime(LocalTime.MAX);
        return Date.from(dayInstEnd.toInstant(OffsetDateTime.now().getOffset()));
    }

    public static boolean isAfternoonShow(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 12 && timeOfDay < 17) {
            return true;
        }
        return false;
    }
}
