import model.DateEntity;
import utils.DaysUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * @Author 戴志豪
 * @date 2021/5/15 16:45
 */
public class DateTest {

    public static void main(String[] args) throws ParseException {


//        DateEntity dateEntity = DaysUtils.getBetweenDays("2019-9-2","2021-3-29");
//
//        System.out.println("间隔时间：\t"+dateEntity.getYear()+"\t年\t"+dateEntity.getMonths()+"\t月\t"+dateEntity.getDays()+"\t日\t");
//
//        System.out.println();
//        System.out.println("==================================");
//        System.out.println("间隔总年数："+dateEntity.getTotalYear());
//        System.out.println("间隔总月数："+dateEntity.getTotalMonths());
//        System.out.println("间隔总天数："+dateEntity.getTotalDays());
//        System.out.println("==================================");

        System.out.println(DaysUtils.checkYear(2024));
         System.out.println(DaysUtils.getAfterDaysDate("2023-8-8", 206));

    }
}
