package com.example.planetz.Calculator.HousingMethod;

import com.example.planetz.Calculator.HousingCalculator;
public class SemiDetachedHouseCalculator {

    /**
     * 计算半独立屋的碳排放
     * @param homeSize 房屋面积
     * @param occupants 家庭成员数量
     * @param billCategory 月电费类别
     * @param heatingType 供暖能源类型
     * @return 碳排放值（单位：kg）
     */
    public double calculateEmission(String homeSize, int occupants, int billCategory, int heatingType) {
        double baseEmission = 0.0;

        // 根据房屋面积分类
        switch (homeSize) {
            case "Under 1000 sq. ft.":
                baseEmission = calculateForSmallHouse(occupants, billCategory, heatingType)/1000;
                break;
            case "1000-2000 sq. ft.":
                baseEmission = calculateForMediumHouse(occupants, billCategory, heatingType)/1000;
                break;
            case "Over 2000 sq. ft.":
                baseEmission = calculateForLargeHouse(occupants, billCategory, heatingType)/1000;
                break;
            default:
                throw new IllegalArgumentException("Unknown home size: " + homeSize);
        }

        return baseEmission;
    }

    private double calculateForSmallHouse(int occupants, int billCategory, int heatingType) {
        return SMALL_HOUSE_IMPACT[occupants-1][heatingType*billCategory-1];
    }

    private double calculateForMediumHouse(int occupants, int billCategory, int heatingType) {
        return MEDIUM_HOUSE_IMPACT[occupants-1][heatingType*billCategory-1];
    }

    private double calculateForLargeHouse(int occupants, int billCategory, int heatingType) {
        return LARGE_HOUSE_IMPACT[occupants-1][heatingType*billCategory-1];
    }

    // Impact matrices based on the provided image data
    private static final double[][] SMALL_HOUSE_IMPACT = {
            {2160,300,2500,2200,2100,2400,410,2800,2600,3000,2600,1210,3000,2800,3200,2800,1700,3200,3000,3400,3000,2200,3400,3200,3600},//1 occupant
            {2349,410,2592,2300,2450,2600,500,3000,2800,3200.2800,1450,3200,3000,3400,3000,1900,3400,3200,3600,3200,2500,3600,3400,3800},//2 occupants
            {2732,500,2680,2450,2700,2900,560,3300,3100,3500,3100,1620,3500,3300,3700,3300,2000,3700,3500,3900,3500,2600,3900,3700,4100}, // 3-4
            {3199,580,2750,2600,3000,3200,600,3600,3400,3800,3400,1820,3800,3600,4000,3600,2200,4000,3800,4200,3800,3000,4200,4000,4400}  // 5
    };

    private static final double[][] MEDIUM_HOUSE_IMPACT = {
            {2443,300,3400,4000,1500,4000,600,4000,5000,2500,4500,1200,6100,7000,4100,5000,1800,8000,9000,5500,6000,2400,10550,12000,7220},
            {2727,410,3499,4300,1800,5200,800,4600,6200,2700,6000,1550,6900,8000,4300,6500,2000,8800,10200,6000,7800,2500,10900,13200,8000},
            {3151,550,3599,4700,2100,6800,1050,5100,7300,3500,7800,1700,7300,9100,4850,8800,2250,9200,11000,6800,9800,2800,11200,14100,8600},
            {3578,605,3700,4900,2500,7500,1200,6000,8000,4000,8500,1800,8500,10000,5500,10000,2400,10500,12000,7100,11000,3200,12000,15000,9100},
    };

    private static final double[][] LARGE_HOUSE_IMPACT = {
            {2821,300,3820,4370,3970,7500,1200,5500,4540,4140,10000,1800,8500,4710,4310,12500,2400,11000,4880,4480,15000,3000,14000,5200,4800},
            {3010,560,4000,4870,4470,8800,1400,6000,5040,4640,12000,2000,9200,5210,4810,14200,2600,12000,5380,4980,16800,3500,14800,5700,5300},
            {3261,890,4307,5670,5270,10800,1650,7200,5840,5340,14000,2300,10200,6010,5610,16000,2820,13500,6180,5780,18200,4000,15500,6500,6150},
            {3578,1000,4400,6370,5970,12500,1800,8500,6540,6140,15000,2400,11000,6710,6310,17500,3000,14000,6880,6480,19000,4500,16000,7200,6800},
    };

}
