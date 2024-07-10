package com.app.lms.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeUtil {

    private TimeUtil(){}

    private static final String TIME_ZONE = "Asia/Kolkata";

    private static final String YYMMDD = "yyMMdd";

    private static final String DD = "dd";

    public static Integer getNSlot(){
        DateFormat df = new SimpleDateFormat(YYMMDD);
        df.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Calendar cal = Calendar.getInstance();
        return Integer.parseInt(df.format(cal.getTime()));
    }

    public static Integer getNSlot(int days){
        DateFormat df = new SimpleDateFormat(YYMMDD);
        df.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        return Integer.parseInt(df.format(cal.getTime()));
    }

    public static Integer getNSlot(int days, int months){
        DateFormat df = new SimpleDateFormat(YYMMDD);
        df.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        cal.add(Calendar.MONTH, months);
        return Integer.parseInt(df.format(cal.getTime()));
    }
    public static Integer getNSlot(int days, int months, int years){
        DateFormat df = new SimpleDateFormat(YYMMDD);
        df.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        cal.add(Calendar.MONTH, months);
        cal.add(Calendar.YEAR, years);
        return Integer.parseInt(df.format(cal.getTime()));
    }

    public static Long getCurrEpoch() {
        return Instant.now().getEpochSecond();
    }

    public static Integer getDaysDiff(Integer startSlot,Integer endSlot){
        String sSlot = startSlot.toString();
        String eSlot = endSlot.toString();
        LocalDate date1 = LocalDate.of(Integer.parseInt("20"+sSlot.substring(0,2)),Integer.parseInt(sSlot.substring(2,4)),Integer.parseInt(sSlot.substring(4,6)));
        LocalDate date2 = LocalDate.of(Integer.parseInt("20"+eSlot.substring(0,2)),Integer.parseInt(eSlot.substring(2,4)),Integer.parseInt(eSlot.substring(4,6)));
        return Long.valueOf(ChronoUnit.DAYS.between(date1, date2)).intValue();
    }

    public static Integer getMonthsDiff(Integer startSlot,Integer endSlot){
        String sSlot = startSlot.toString();
        String eSlot = endSlot.toString();
        LocalDate date1 = LocalDate.of(Integer.parseInt("20"+sSlot.substring(0,2)),Integer.parseInt(sSlot.substring(2,4)),Integer.parseInt(sSlot.substring(4,6)));
        LocalDate date2 = LocalDate.of(Integer.parseInt("20"+eSlot.substring(0,2)),Integer.parseInt(eSlot.substring(2,4)),Integer.parseInt(eSlot.substring(4,6)));
        return Long.valueOf(ChronoUnit.MONTHS.between(date1, date2)).intValue();
    }

    public static Integer getWeeksDiff(Integer startSlot,Integer endSlot){
        String sSlot = startSlot.toString();
        String eSlot = endSlot.toString();
        LocalDate date1 = LocalDate.of(Integer.parseInt("20"+sSlot.substring(0,2)),Integer.parseInt(sSlot.substring(2,4)),Integer.parseInt(sSlot.substring(4,6)));
        LocalDate date2 = LocalDate.of(Integer.parseInt("20"+eSlot.substring(0,2)),Integer.parseInt(eSlot.substring(2,4)),Integer.parseInt(eSlot.substring(4,6)));
        return Long.valueOf(ChronoUnit.WEEKS.between(date1, date2)).intValue();
    }

    public static Integer getDayOfMonth(){
        DateFormat df = new SimpleDateFormat(DD);
        df.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        Calendar cal = Calendar.getInstance();
        return Integer.parseInt(df.format(cal.getTime()));
    }

    public static Integer getNSlotByDayOfMonth(Integer day){
        LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), day);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYMMDD);
        return Integer.parseInt(date.format(formatter));
    }

    public static Integer addMonthToNSlot(Integer nSlot,Integer months){
        String nSlotString = nSlot.toString();
        LocalDate date = LocalDate.of(Integer.parseInt("20"+nSlotString.substring(0,2)),Integer.parseInt(nSlotString.substring(2,4)),Integer.parseInt(nSlotString.substring(4,6)));
        LocalDate result = date.plusMonths(months);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYMMDD);
        return Integer.parseInt(result.format(formatter));
    }

    public static Integer getDateByNSlot(Integer nSlot) {
        return Integer.parseInt(nSlot.toString().substring(4,6));
    }

    public static Integer getDayOfWeek(Integer nSlotId) {
        String nSlotString = nSlotId.toString();
        LocalDate date = LocalDate.of(Integer.parseInt("20"+nSlotString.substring(0,2)),Integer.parseInt(nSlotString.substring(2,4)),Integer.parseInt(nSlotString.substring(4,6)));
        return date.getDayOfWeek().getValue();
    }


    public static Integer addWeekToNSlot(Integer nSlot, Integer noOfWeeks) {
        String nSlotString = nSlot.toString();
        LocalDate date = LocalDate.of(Integer.parseInt("20"+nSlotString.substring(0,2)),Integer.parseInt(nSlotString.substring(2,4)),Integer.parseInt(nSlotString.substring(4,6)));
        LocalDate result = date.plusWeeks(noOfWeeks);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYMMDD);
        return Integer.parseInt(result.format(formatter));
    }

    public static String formatNSlot(Integer nSlot,String format) {
        String nSlotString = nSlot.toString();
        LocalDate date = LocalDate.of(Integer.parseInt("20"+nSlotString.substring(0,2)),Integer.parseInt(nSlotString.substring(2,4)),Integer.parseInt(nSlotString.substring(4,6)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
}
