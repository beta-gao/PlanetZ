package com.example.planetz.DailyActivity;

public class ActivityLog {
    private String activityType;
    private double value;

    // Constructor
    public ActivityLog(String activityType, double value) {
        this.activityType = activityType;
        this.value = value;
    }

    // Getter for activity type
    public String getActivityType() {
        return activityType;
    }

    // Getter for value (emission)
    public double getValue() {
        return value;
    }
}