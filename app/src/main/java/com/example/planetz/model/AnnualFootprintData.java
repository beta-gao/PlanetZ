package com.example.planetz.model;

import java.util.HashMap;
import java.util.Map;

public class AnnualFootprintData {

    private static AnnualFootprintData instance; // 单例实例

    // Fields
    private String userId;         // 用户 ID
    private double transportation; // 交通
    private double housing;        // 住房
    private double food;           // 饮食
    private double consumption;    // 通用消费
    private double total;          // 总消费

    // 私有构造方法，确保只能通过 getInstance() 获取实例
    private AnnualFootprintData() {
        this.transportation = 0;
        this.housing = 0;
        this.food = 0;
        this.consumption = 0;
        this.total = 0;
    }

    // 获取单例实例的静态方法
    public static synchronized AnnualFootprintData getInstance() {
        if (instance == null) {
            instance = new AnnualFootprintData();
        }
        return instance;
    }

    // Getter 和 Setter 方法
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

    // 添加 setTotal 方法
    public void setTotal(double total) {
        this.total = total;
    }

    // 更新总消费
    private void updateTotal() {
        this.total = transportation + housing + food + consumption;
    }

    // 转换为 Map 以支持 Firestore 上传
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
