package com.paulocurado.esportsmanager.model;

import com.badlogic.gdx.Gdx;

/**
 * Created by phcur on 20/12/2016.
 */

public class GameSchedule {
    private int day;
    private int week;
    private int month;
    private int year;
    public static int MIN_DAY = 7;
    public static int MIN_WEEK = 4;
    public static int MIN_MONTH = 12;
    public static float TIME_PASSING_SECONDS = 0.2f;
    private float realTime = 0;

    private boolean timePass = false;


    public GameSchedule() {
        day = 1;
        week = 1;
        month = 1;
        year = 2015;
    }

    public GameSchedule(int day, int week, int month, int year) {
        this.day = day;
        this.week = week;
        this.month = month;
        this.year = year;
    }

    public void passTime() {
        if(timePass) {
            realTime = realTime + Gdx.graphics.getDeltaTime();
            if(realTime >= TIME_PASSING_SECONDS) {
                realTime = 0;
                addDay(1);
            }
        }
    }

    public void addDay(int days) {
        if(day + days > MIN_DAY) {
            int multiplier = (day + days) / MIN_DAY;
            addWeek(multiplier);
            day = day + days - multiplier * MIN_DAY;
        }
        else {
            day += days;
        }

    }
    public void addWeek(int weeks) {
        if(week + weeks > MIN_WEEK) {
            int multiplier = (week + weeks) / MIN_WEEK;
            addMonth(multiplier);
            week = week + weeks - multiplier * MIN_WEEK;
        }
        else {
            week += weeks;
        }
    }

    public void addMonth(int months) {
        if(month + months > MIN_MONTH) {
            int multiplier = (month + months) / MIN_MONTH;
            year = year + multiplier;
            month = month + months - multiplier * MIN_MONTH;
        }
        else {
            month += months;
        }
    }
    public void addYear(int years) {
        year += years;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isTimePass() {
        return timePass;
    }

    public void setTimePass(boolean timePass) {
        this.timePass = timePass;
    }
}
