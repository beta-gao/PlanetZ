package com.example.planetz.Calculator;

import android.util.Log;

import com.example.planetz.Calculator.HousingMethod.CondoApartmentCalculator;
import com.example.planetz.Calculator.HousingMethod.DetachedHouseCalculator;
import com.example.planetz.Calculator.HousingMethod.SemiDetachedHouseCalculator;
import com.example.planetz.Calculator.HousingMethod.TownhouseCalculator;
import com.example.planetz.model.CarbonFootprintData;

public class HousingCalculator {

    /**
     * 计算住房碳排放
     *
     * @param data CarbonFootprintData 实例
     * @return 碳排放（单位：kg）
     */
    public double calculateHousingEmission(CarbonFootprintData data) {
        double baseEmission = calculateBaseEmission(
                data.getHomeType(),
                data.getHouseholdSize(),
                data.getHomeSize(),
                data.getMonthlyElectricityBill(),
                data.getHomeHeatingType()
        );

        if (!data.getHomeHeatingType().equalsIgnoreCase(data.getWaterHeatingType())) {
            baseEmission += 0.233; // 固定增加 233 kg
        }

        baseEmission -= calculateRenewableEnergyReduction(data.getRenewableEnergyUse());

        return baseEmission;
    }

    /**
     * 计算基础碳排放
     *
     * @param homeType               住房类型
     * @param householdSize          家庭成员数量
     * @param homeSize               房屋面积
     * @param monthlyElectricityBill 月电费
     * @param heatingType            供暖能源类型
     * @return 碳排放（单位：kg）
     */
    private double calculateBaseEmission(String homeType, String householdSize, String homeSize, String monthlyElectricityBill, String heatingType) {
        int occupants = parseHouseholdSize(householdSize);
        int sizeCategory = parseHomeSize(homeSize);
        int billCategory = parseElectricityBill(monthlyElectricityBill);
        int heatingCategory = parseHeatingType(heatingType);

        switch (homeType) {
            case "Detached house":
                DetachedHouseCalculator detachedCalculator = new DetachedHouseCalculator();
                return detachedCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Semi-detached house":
                SemiDetachedHouseCalculator semiDetachedCalculator = new SemiDetachedHouseCalculator();
                return semiDetachedCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Townhouse":
            case "Other":
                TownhouseCalculator townhouseCalculator = new TownhouseCalculator();
                return townhouseCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Condo/Apartment":
                CondoApartmentCalculator condoCalculator = new CondoApartmentCalculator();
                return condoCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            default:
                throw new IllegalArgumentException("Unknown home type: " + homeType);
        }
    }


    /**
     * 解析家庭成员数量
     *
     * @param householdSize 家庭成员数量
     * @return 数值化的成员数量
     */
    private int parseHouseholdSize(String householdSize) {
        switch (householdSize) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3-4":
                return 4;
            case "5 or more":
                return 5;
            default:
                throw new IllegalArgumentException("Unknown household size");
        }
    }

    /**
     * 解析房屋面积类别
     *
     * @param homeSize 房屋面积
     * @return 面积类别
     */
    private int parseHomeSize(String homeSize) {
        switch (homeSize) {
            case "Under 1000 sq. ft.":
                return 1;
            case "1000-2000 sq. ft.":
                return 2;
            case "Over 2000 sq. ft.":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown home size");
        }
    }

    /**
     * 解析月电费类别
     *
     * @param monthlyElectricityBill 月电费
     * @return 电费类别
     */
    private int parseElectricityBill(String monthlyElectricityBill) {
        switch (monthlyElectricityBill) {
            case "Under $50":
                return 1;
            case "$50-$100":
                return 2;
            case "$100-$150":
                return 3;
            case "$150-$200":
                return 4;
            case "Over $200":
                return 5;
            default:
                Log.e("HousingCalculator", "Unknown electricity bill: " + monthlyElectricityBill);
                return 0; // 返回默认值，表示未知类别
        }
    }


    /**
     * 解析供暖能源类型
     *
     * @param heatingType 供暖能源类型
     * @return 能源类别
     */
    private int parseHeatingType(String heatingType) {
        switch (heatingType) {
            case "Natural Gas":
                return 1;
            case "Electricity":
                return 2;
            case "Oil":
                return 3;
            case "Propane":
                return 4;
            case "Wood":
            case "Other":
                return 5;
            default:
                throw new IllegalArgumentException("Unknown heating type");
        }
    }

    /**
     * 计算可再生能源减排
     *
     * @param renewableEnergyUse 可再生能源使用情况
     * @return 减排值（单位：kg）
     */
    private double calculateRenewableEnergyReduction(String renewableEnergyUse) {
        switch (renewableEnergyUse) {
            case "Yes, primarily (more than 50% of energy use)":
                return 6.0;
            case "Yes, partially (less than 50% of energy use)":
                return 4.0;
            case "No":
                return 0.0;
            default:
                throw new IllegalArgumentException("Unknown renewable energy use");
        }
    }
}