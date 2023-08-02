package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 戴志豪
 * @date 2021/5/16 18:44
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateEntity {

    private int year;
    private int months;
    private int days;
    private int totalYear;
    private int totalMonths;
    private int totalDays;

//    public int getTotalYear() {
//        return totalYear;
//    }
//
//    public void setTotalYear(int totalYear) {
//        this.totalYear = totalYear;
//    }
//
//    public int getTotalMonths() {
//        return totalMonths;
//    }
//
//    public void setTotalMonths(int totalMonths) {
//        this.totalMonths = totalMonths;
//    }
//
//    public int getYear() {
//        return year;
//    }
//
//    public void setYear(int year) {
//        this.year = year;
//    }
//
//    public int getMonths() {
//        return months;
//    }
//
//    public void setMonths(int months) {
//        this.months = months;
//    }
//
//    public int getDays() {
//        return days;
//    }
//
//    public void setDays(int days) {
//        this.days = days;
//    }
//
//    public int getTotalDays() {
//        return totalDays;
//    }
//
//    public void setTotalDays(int totalDays) {
//        this.totalDays = totalDays;
//    }
}
