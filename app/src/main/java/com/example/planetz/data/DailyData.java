package com.example.planetz.data;

import android.util.Log;

public class DailyData {
    private Consumption consumption;
    private Date date;

    private static final String TAG = "DailyData";

    public DailyData() {
        Log.d(TAG, "DailyData: Default constructor called");
    }

    public DailyData(Consumption consumption, Date date) {
        Log.d(TAG, "DailyData: Constructor with parameters called");
        this.consumption = consumption;
        this.date = date;
    }

    public DailyData getDailyData() {
        Log.d(TAG, "getDailyData: Returning this instance");
        return this;
    }

    // method to set and get consumption
    public void setConsumption(Consumption consumption) {
        Log.d(TAG, "setConsumption: Setting consumption");
        this.consumption = consumption;
    }

    public Consumption getConsumption() {
        Log.d(TAG, "getConsumption: Returning consumption");
        return consumption;
    }

    // method to set and get date
    public void setDate(Date date) {
        Log.d(TAG, "setDate: Setting date");
        this.date = date;
    }

    public Date getDate() {
        Log.d(TAG, "getDate: Returning date");
        return date;
    }

    public int getYear() {
        int year = date != null ? date.getYear() : 0;
        Log.d(TAG, "getYear: Returning year " + year);
        return year;
    }

    public int getMonth() {
        int month = date != null ? date.getMonth() : 0;
        Log.d(TAG, "getMonth: Returning month " + month);
        return month;
    }

    public int getWeek() {
        int week = date != null ? date.getWeek() : 0;
        Log.d(TAG, "getWeek: Returning week " + week);
        return week;
    }

    public int getDay() {
        int day = date != null ? date.getDay() : 0;
        Log.d(TAG, "getDay: Returning day " + day);
        return day;
    }
}
