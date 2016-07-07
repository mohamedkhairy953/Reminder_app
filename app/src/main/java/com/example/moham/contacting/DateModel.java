package com.example.moham.contacting;

import java.io.Serializable;

/**
 * Created by moham on 6/17/2016.
 */
public class DateModel implements Serializable {
    private String year;
    private String month;
    private String day;
    String[] split;

    public DateModel() {
    }

    public DateModel(String RemindMeString) {
        this.split = RemindMeString.split("/");
        this.setDay(split[0]);
        this.setMonth(split[1]);
        this.setYear(split[2]);
    }

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }
}
