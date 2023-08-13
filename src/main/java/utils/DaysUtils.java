package utils;


import model.DateEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 戴志豪
 * @date 2021/5/15 16:34
 */
public class DaysUtils {


    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    // 初始化月份天数
    private static final Map<Integer, Integer> map = new HashMap<>();

    static {
        for (int i = 1; i <= 12; i++) {
            if (i <= 7) {
                if (i % 2 != 0) {
                    map.put(i, 31);
                } else if (i == 2) {
                    map.put(i, 28);
                } else {
                    map.put(i, 30);
                }
            } else {
                if (i % 2 == 0) {
                    map.put(i, 31);
                } else {
                    map.put(i, 30);
                }
            }
        }
    }


    /**
     * 根据月份提供该月的天数
     *
     * @param months
     * @return 对应月份的天数
     */
    public static int getMonthsDays(Integer months, Integer year) {

        if (months == 2) {
            if ((checkYear(year))) {
                return map.get(months) + 1;
            } else {
                return map.get(months);
            }
        }

        return map.get(months);
    }


    /**
     * 判断年的天数
     *
     * @param year
     * @return 对应年份的天数
     */
    public static int getYearDays(Integer year) {

        if ((checkYear(year))) {
            return 366;
        } else {
            return 365;
        }

    }

    /**
     * 判断是否闰年
     *
     * @param year 年
     * @return true 为 闰年    false 不是闰年
     */
    public static boolean checkYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    /**
     * 重载getBetweenDays方法，接受字符串形式日期
     * <p>
     * 格式注意 为  yyyy-MM-dd  eg: 2022-02-01
     *
     * @param newDate 起始时间
     * @param oldDate 结束时间
     * @return 间隔时间实体
     * @throws ParseException 解析异常，一般为格式异常导致
     */
    public static DateEntity getBetweenDays(String newDate, String oldDate) throws ParseException {
        if ("".equals(newDate.trim()) && "".equals(oldDate.trim())) {
            return new DateEntity(0, 0, 0, 0, 0, 0);
        }

        return getBetweenDays(Optional.of(newDate).orElse(DATE_FORMAT.format(new Date())),
                Optional.of(oldDate).orElse(DATE_FORMAT.format(new Date()))
        );

    }

    /**
     * 计算两个日期间的天数
     * 保证了总是大的减小的日期
     * <p>
     * 核心思想（2021-5-15   2019-3-25 举例）：
     * 总数为五部分组成：
     * 1.2020年 全年天数
     * <p>
     * 2.2021年 1-4 四个月份总天数
     * <p>
     * 3.2019年 4-12 九个月份总天数
     * <p>
     * 4.2021年 5 月多出的15天
     * <p>
     * 5.2019年 3 月剩余的6天
     *
     * @param newDate 总是被减的日期
     * @param oldDate 总是减的日期
     * @return DateEntity
     */
    public static DateEntity getBetweenDays(Date newDate, Date oldDate) {


        int yearSum = 0;
        int monthsSum = 0;
        int daysSum = 0;

        int totalSum = 0;
        int totalYear = 0;
        int totalMonths = 0;

        DateEntity dateEntity = new DateEntity();

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

        //下面区域代码块功能为保证总是大的日期减去小的日期

        //首先比较年份，如果年份相同继续比较月份
        if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[0]) == Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[0])) {

            //如果月份小于后者，则交换日期位置，大于不变
            if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[1]) < Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[1])) {
                Date temp = oldDate;
                oldDate = newDate;
                newDate = temp;
            }

            //如果月份等于后者，则比较日期
            if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[1]) == Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[1])) {

                //如果日期小于，则交换日期位置，大于不变
                if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[2]) < Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[2])) {
                    Date temp = oldDate;
                    oldDate = newDate;
                    newDate = temp;
                }

                //如果日期相等，至此日期完全相同，直接返回天数0
                if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[2]) == Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[2])) {
                    dateEntity.setTotalDays(0);
                    return dateEntity;
                }
            }

            //如期年份小于后者，则发生交换。
        } else if (Integer.parseInt(DATE_FORMAT.format(newDate).split("-")[0]) < Integer.parseInt(DATE_FORMAT.format(oldDate).split("-")[0])) {
            Date temp = oldDate;
            oldDate = newDate;
            newDate = temp;
        }


        //下面这部分用于计算

        // 日期处理
        String dateStr1 = DATE_FORMAT.format(oldDate);
        String[] split1 = dateStr1.split("-");
        int year1 = Integer.parseInt(split1[0]);
        int months1 = Integer.parseInt(split1[1]);
        int day1 = Integer.parseInt(split1[2]);


        String dateStr2 = DATE_FORMAT.format(newDate);
        String[] split2 = dateStr2.split("-");
        int year2 = Integer.parseInt(split2[0]);
        int months2 = Integer.parseInt(split2[1]);
        int day2 = Integer.parseInt(split2[2]);

       /*
        //判断日期格式是否符合规范
        if (day1 > DaysUtils.getMonthsDays(months1,year1) || day2 > DaysUtils.getMonthsDays(months2,year2)){
            System.out.println("日期参数不合理");
            throw new RuntimeException();
        }
        */

        //因为有了上面的日期处理，会保证year2永远大于year1，月，日都是如此，所以，年份除了不等于，只剩year2大于year1
        if (year1 != year2) {
            List<Integer> years = RangeUtils.range(year1, year2);

            //获取年份不同时，之间间隔的年份并计算日期
            for (Integer year : years) {
                totalYear += 1;
                totalMonths += 12;
                yearSum += 1;
                totalSum += DaysUtils.getYearDays(year);
            }

            //计算完间隔年的整年日期后（若2020-9 ~ 2021-6 这样的，上面将不会有间隔年，即这时sum=0） 再计算被减日期多出的月的总天数
            for (Integer mons : RangeUtils.range(0, months2)) {
                monthsSum += 1;
                totalMonths += 1;
                totalSum += DaysUtils.getMonthsDays(mons, year2);
            }

            //计算完间隔年的整年日期后（若2020-9 ~ 2021-6 这样的，上面将不会有间隔年，即这时sum=0） 再计算减日期剩余的月的总天数
            for (Integer mons2 : RangeUtils.range(months1, 13)) {
                monthsSum += 1;
                totalMonths += 1;
                totalSum += DaysUtils.getMonthsDays(mons2, year1);
            }

            //最后将间隔年和多出的月，以及剩余月的总天数加完后，再加上被减日期的多出天数，减日期该月的剩余天数，最后结果，即两日期间隔时间
            totalSum += day2 + (DaysUtils.getMonthsDays(months1, year1) - day1);
            daysSum = day2 + (DaysUtils.getMonthsDays(months1, year1) - day1);

            // 日期超出29天处理
            if (daysSum > 29) {
                monthsSum += 1;
                totalMonths += 1;
                daysSum -= 30;
            }


            //如果年份相等，则直接进行计算间隔月，加多出天数和剩余天数
        } else {

            List<Integer> mons = RangeUtils.range(months1, months2);
            for (Integer mon : mons) {
                monthsSum += 1;
                totalMonths += 1;
                totalSum += DaysUtils.getMonthsDays(mon, year1);
            }
            totalSum += day2 + (DaysUtils.getMonthsDays(months1, year1) - day1);
            daysSum = day2 + (DaysUtils.getMonthsDays(months1, year1) - day1);


            // 日期超出29天处理
            if (daysSum > 29) {
                monthsSum += 1;
                totalMonths += 1;
                daysSum -= 30;
            }

        }

        // 将处理好的日期存入日期模型
        dateEntity.setYear(yearSum);
        dateEntity.setMonths(monthsSum);
        dateEntity.setDays(daysSum);


        dateEntity.setTotalYear(totalYear);
        dateEntity.setTotalMonths(totalMonths);
        dateEntity.setTotalDays(totalSum);

        return dateEntity;

    }

    /**
     *
     *   根据当前日期往后推的天数，得到xx天后的日期
     *
     * @param date  传递的日期
     * @param days  推进的天数
     * @return  推进后的日期
     */
    public static String getAfterDaysDate(String date, int days) {
        int resultMonth = 0;
        int resultDays = 0;
        int totalMonth = 0;
        StringBuilder sb = new StringBuilder();

        String[] dateStr = date.split("-");
        int year = Integer.parseInt(dateStr[0]);
        int month = Integer.parseInt(dateStr[1]);
        int day = Integer.parseInt(dateStr[2]);



        // 如果传递的天数小于当前月剩余天数，则直接返回以下逻辑
        if (days <= (getMonthsDays(month, year) - day)) return buildDate(year,month,day+days);

        // 减去当月剩余天数后还剩下的天数
        int tempDay = days - (getMonthsDays(month, year) - day);
        resultDays = tempDay;

        for (int i = 1; i <= (tempDay / 30)+1; i++) {
            resultMonth++;
            int nextMonthDays = getNextMonthDays((month+i-1)%12, ((month+i-1) / 12 > 0 ? year + ((month+i-1) /12):year));
            if (resultDays <= nextMonthDays) break;
            resultDays -= nextMonthDays;
        }

        totalMonth = month + resultMonth;

       if (totalMonth / 12 != 0){
           year += totalMonth / 12;
       }

       sb.append(year)
               .append("-")
               .append(totalMonth % 12)
               .append("-")
               .append(resultDays);



        return sb.toString();
    }


    /**
     * 返回后一个月的天数
     *
     * @param month 月份
     * @param year  年份
     * @return 下一个月的天数
     */
    public static int getNextMonthDays(int month, int year) {
        int nextMonthDay;
        nextMonthDay = switch (month) {
            case 1 -> checkYear(year) ? 29 : 28;
            case 12 -> getMonthsDays(1, year + 1);
            default -> getMonthsDays(month + 1, year);
        };
        return nextMonthDay;
    }



    /**
     *
     *  根据年月日构造标准日期
     *
     * @param year   年份
     * @param month  月份
     * @param day    天
     * @return  标准日期
     */
    private static String buildDate(int year,int month,int day){
        return year +
                "-" +
                month +
                "-" +
                day;
    }

}
