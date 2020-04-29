package com.csdl.smartplacenew.count;

import java.util.Date;

public class CountObjectInfo {


    // 总访问量合计
    protected int totalCount = 0;
    // 日访问量
    protected int dayCount = 0;
    // 周访问量
    protected int weekCount = 0;
    // 月访问量
    protected int monthCount = 0;
    // 年访问量
    protected int yearCount = 0;
    // 临时访问量
    protected int tempCount = 0;

    protected Date date = new Date();


    public int getDayCount() {
        return dayCount;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getWeekCount() {
        return weekCount;
    }

    public int getYearCount() {
        return yearCount;
    }

    public void setDayCount(int i) {
        dayCount = i;
    }

    public void setMonthCount(int i) {
        monthCount = i;
    }

    public void setTotalCount(int i) {
        totalCount = i;
    }

    public void setWeekCount(int i) {
        weekCount = i;
    }

    public void setYearCount(int i) {
        yearCount = i;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTempCount() {
        return tempCount;
    }

    public void setTempCount(int i) {
        tempCount = i;
    }


}
