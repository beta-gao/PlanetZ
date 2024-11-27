package com.example.planetz.model;

public class ConsumptionData {

    // 各分类的消费字段
    private double transportation; // 交通
    private double housing;        // 住房
    private double food;           // 饮食
    private double consumption;    // 通用消费

    // 总消费字段
    private double total;

    // 构造方法
    public ConsumptionData(double transportation, double housing,
                           double food, double consumption) {
        this.transportation = transportation;
        this.housing = housing;
        this.food = food;
        this.consumption = consumption;
        this.total = calculateTotal();
    }

    // 默认构造方法
    public ConsumptionData() {
        this.transportation = 0;
        this.housing = 0;
        this.food = 0;
        this.consumption = 0;
        this.total = 0;
    }

    // 计算总消费
    private double calculateTotal() {
        return transportation + housing + food + consumption;
    }

    // 更新总消费（每当分类消费发生变化时自动更新）
    private void updateTotal() {
        this.total = calculateTotal();
    }

    // Getter 和 Setter 方法
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

    @Override
    public String toString() {
        return "ConsumptionData{" +
                "transportation=" + transportation +
                ", housing=" + housing +
                ", food=" + food +
                ", consumption=" + consumption +
                ", total=" + total +
                '}';
    }
}
