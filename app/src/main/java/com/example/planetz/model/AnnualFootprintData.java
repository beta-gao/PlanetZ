package com.example.planetz.model;

import java.util.HashMap;
import java.util.Map;

public class AnnualFootprintData {

    private static AnnualFootprintData instance;

    // Fields
    private String userId;
    private double transportation;
    private double housing;
    private double food;
    private double consumption;
    private double total;

    private AnnualFootprintData() {
        this.transportation = 0;
        this.housing = 0;
        this.food = 0;
        this.consumption = 0;
        this.total = 0;
    }

    public static synchronized AnnualFootprintData getInstance() {
        if (instance == null) {
            instance = new AnnualFootprintData();
        }
        return instance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTransportation() {
        return transportation;
    }

    public void setTransportation(double transportation) {
        this.transportation = transportation;
        updateTotal();
    }

    public double getHousing() {
        return housing;
    }

    public void setHousing(double housing) {
        this.housing = housing;
        updateTotal();
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
        updateTotal();
    }

    public double getConsumption() {
        return consumption;
    }

    public void setConsumption(double consumption) {
        this.consumption = consumption;
        updateTotal();
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private void updateTotal() {
        this.total = transportation + housing + food + consumption;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("transportation", transportation);
        data.put("housing", housing);
        data.put("food", food);
        data.put("consumption", consumption);
        data.put("total", total);
        return data;
    }

    @Override
    public String toString() {
        return "ConsumptionData{" +
                "userId='" + userId + '\'' +
                ", transportation=" + transportation +
                ", housing=" + housing +
                ", food=" + food +
                ", consumption=" + consumption +
                ", total=" + total +
                '}';
    }
}
