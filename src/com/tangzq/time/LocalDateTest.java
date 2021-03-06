package com.tangzq.time;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;

/**
 * Author tangzq.
 */
public class LocalDateTest {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args){
        LocalDate from=LocalDate.parse("2016-06-12",formatter);
        LocalDate to=LocalDate.parse("2016-05-30",formatter);
        //取得指定日期范围之间有多少天
        //方法1
        long daysBetween = DAYS.between(from, to);
        System.out.println(daysBetween);

        //方法2
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now();
        long days = Period.between(startDate, endDate).getDays();
        System.out.println(days);
        //方法3
        long days2 = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println(days2);

        //取得指定日期范围之间有多少周
        long weeks = WEEKS.between(from,to);
        System.out.println(weeks);

        //取得指定日期范围之间有多少月
        long months=MONTHS.between(from,to);
        System.out.println(months);

        //取得指定日期属于某周的第几天
        System.out.println("周的第几天："+from.getDayOfWeek().getValue());
        //取得指定日期属于某月的第几天
        System.out.println("月的第几天："+from.getDayOfMonth());

        //取得指定日期所在某一周的星期一和星期天的日期
        LocalDate now = LocalDate.now();
        LocalDate first = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate last = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(first+","+last);

        //取得指定的两个日期范围内周一所在的日期
        LocalDate froma=LocalDate.parse("2016-05-01",formatter);
        LocalDate tob=LocalDate.parse("2016-06-30",formatter);
        LocalDate tempDate=froma;
        while(tempDate.isBefore(tob)){
            /**
             LocalDate mondayDate = tempDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
             System.out.println("每周一的日期："+mondayDate);
             System.out.println(tempDate.getDayOfWeek().name());
             System.out.println(DayOfWeek.MONDAY.name());
             **/
            if(tempDate.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())){
                System.out.println("每周一的日期："+tempDate);
            }
            tempDate=tempDate.plusDays(1);
        }
        while (!tempDate.isAfter(tob)){
            /**
             LocalDate mondayDate = tempDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
             System.out.println("每周一的日期："+mondayDate);
             **/
            if(tempDate.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())){
                System.out.println("每周一的日期："+tempDate);
            }
            tempDate=tempDate.plusDays(1);
            tempDate=tempDate.plusDays(1);
        }

        System.out.println(getMondayDate(froma,tob));

        //取得指定日期范围内月的日期
        LocalDate start=LocalDate.parse("2016-01-12",formatter);
        LocalDate end=LocalDate.parse("2016-06-30",formatter);
        LocalDate tempDate2=start;
        Map<String,List<LocalDate>> map=new HashMap();
        while(tempDate2.isBefore(end)){
//            LocalDate firstDayOfMonth = initial.withDayOfMonth(1);
//            LocalDate lastDayOfMonth = initial.withDayOfMonth(initial.lengthOfMonth());
            System.out.println(tempDate2.getMonth().name());
            LocalDate firstDayOfMonth = tempDate2.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = tempDate2.with(TemporalAdjusters.lastDayOfMonth());

            System.out.println(firstDayOfMonth+","+lastDayOfMonth);
            tempDate2=tempDate2.plusDays(1);
        }

        //取得指定日期范围内月的第一天的日期
        LocalDate start2=LocalDate.parse("2016-05-12",formatter);
        LocalDate end2=LocalDate.parse("2016-06-30",formatter);

        System.out.println(groupDateByMonths(start2,end2));
        LocalDate date=LocalDate.parse("2016-05-12",formatter);
        System.out.println(isBetween(date,start,end));
        LocalDate date2=LocalDate.parse("2016-08-12",formatter);
        System.out.println(isBetween(date2,start,end));
        LocalDate date3=LocalDate.parse("2016-04-12",formatter);
        System.out.println(isBetween(date3,start,end));
        LocalDate date4=LocalDate.parse("2016-06-30",formatter);
        System.out.println(isBetween(date4,start,end));
    }


    /**
     * 判断日期是否在指定范围内
     * @param date
     * @param dateStart
     * @param dateEnd
     * @return
     */
    public static boolean isBetween(LocalDate date, LocalDate dateStart, LocalDate dateEnd) {
        if (date != null && dateStart != null && dateEnd != null) {
            if ((date.isAfter(dateStart) && date.isBefore(dateEnd))||date.isEqual(dateStart)||date.isEqual(dateEnd)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    /**
     * 取得有效的月份第一天数据
     * @param from
     * @param to
     * @return
     */
    private static List<LocalDate> groupDateByMonths(LocalDate from,LocalDate to){
        LocalDate tempDate=from;
        List<LocalDate> firstDayOfMonthList=new ArrayList<LocalDate>();
        while(tempDate.isBefore(to)){
            LocalDate firstDayOfThisMonth= tempDate.withDayOfMonth(1);
            if(tempDate.toString().equals(firstDayOfThisMonth.toString())){
                firstDayOfMonthList.add(tempDate);
            }
            tempDate=tempDate.plusDays(1);
        }
        while (!tempDate.isAfter(to)){
            LocalDate firstDayOfThisMonth= tempDate.withDayOfMonth(1);
            if(tempDate.toString().equals(firstDayOfThisMonth.toString())){
                firstDayOfMonthList.add(tempDate);
            }
            tempDate=tempDate.plusDays(1);
        }
        return firstDayOfMonthList;
    }

    /**
     * 取得指定的两个日期范围内周一所在的日期封装方法
     * @param from
     * @param to
     * @return
     */
    private static List<LocalDate> getMondayDate(LocalDate from, LocalDate to){
        List<LocalDate> mondayDateList=new ArrayList();
        LocalDate tempDate=from;
        while(tempDate.isBefore(to)){
            if(tempDate.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())){
                mondayDateList.add(tempDate);
            }
            tempDate=tempDate.plusDays(1);
        }
        while (!tempDate.isAfter(to)){
            if(tempDate.getDayOfWeek().name().equals(DayOfWeek.MONDAY.name())){
                mondayDateList.add(tempDate);
            }
            tempDate=tempDate.plusDays(1);
            tempDate=tempDate.plusDays(1);
        }
        return mondayDateList;
    }
}
