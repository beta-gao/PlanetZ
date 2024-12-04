package com.example.planetz.DailyActivity;

public class ActivityLog {
    private String activityType;
    private double value;

    public ActivityLog(String activityType, double value) {
        this.activityType = activityType;
        this.value = value;
    }

    public String getActivityType() {
        return activityType;
    }

    public double getValue() {
        return value;
    }
}
