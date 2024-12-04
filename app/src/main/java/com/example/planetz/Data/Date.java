package com.example.planetz.Data;

import android.util.Log;

public class Date {
    private int day;
    private int month;
    private int week;
    private int year;

    private static final String TAG = "Date";

    public Date() {
        Log.d(TAG, "Date: Default constructor called");
    }

    /**
     * Sets the date with all components (day, month, week, year).
     *
     * @param day represents the day of the month and should within 1-31.
     * @param month represents the month of the year and should within 1-12.
     * @param week represents the week of the year and should with 1-53.
     * @param year represents the year and should be positive integer.
     */
    public Date(int day, int month, int week, int year) {
        Log.d(TAG, "Date: Constructor with parameters called");
        if (day < 1 || day > 31) {
            Log.e(TAG, "Date: Invalid day " + day);
            throw new IllegalArgumentException("Day should be within 1 and 31.");
        }
        if (month < 1 || month > 12) {
            Log.e(TAG, "Date: Invalid month " + month);
            throw new IllegalArgumentException("Month should be within 1 and 12.");
        }
        if (week < 1 || week > 53) {
            Log.e(TAG, "Date: Invalid week " + week);
            throw new IllegalArgumentException("Week should be within 1 and 53.");
        }
        if (year < 0) {
            Log.e(TAG, "Date: Invalid year " + year);
            throw new IllegalArgumentException("Year should be a positive number.");
        }
        this.day = day;
        this.month = month;
        this.week = week;
        this.year = year;
    }

    public Date getDate() {
        Log.d(TAG, "getDate: Returning this instance");
        return this;
    }

    // Getters
    public int getDay() {
        Log.d(TAG, "getDay: Returning day " + day);
        return day;
    }

    public int getMonth() {
        Log.d(TAG, "getMonth: Returning month " + month);
        return month;
    }

    public int getWeek() {
        Log.d(TAG, "getWeek: Returning week " + week);
        return week;
    }

    public int getYear() {
        Log.d(TAG, "getYear: Returning year " + year);
        return year;
    }
}
