package com.example.planetz.Calculator.HousingMethod;

public class CondoApartmentCalculator {

    /**
     * 计算公寓的碳排放
     *
     * @param homeSize     房屋面积
     * @param occupants    家庭成员数量
     * @param billCategory 月电费类别
     * @param heatingType  供暖能源类型
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
            {1680,200,0,1320,1600,2240,500,0,2100,1800,2800,900,0,3000,2800,3700,1400,0,3300,3000,5000,1900,0,5700,3500},
            {1870,250,0,1550,1850,2430,550,0,2400,2000,3000,1100,0,3300,3000,4100,1600,0,3700,3100,7200,2100,0,6000,3600},
            {2170,380,0,1800,2000,2719,580,0,2800,2300,3200,1200,0,3700,3300,4600,1700,0,4400,3500,8000,2200,0,6600,3900},
            {2440,450,0,2000,2100,2997,600,0,3200,2400,3500,1300,0,4300,3500,5000,1900,0,5200,3900,10000,2600,0,7000,4200}
    };

    private static final double[][] MEDIUM_HOUSE_IMPACT = {
            {2060,300,0,1500,1800,2500,550,0,3000,2200,3100,1250,0,4100,3200,4000,1900,0,4550,3100,5350,2300,0,6000,3900},
            {2260,400,0,1700,2200,2880,670,0,3400,2500,3300,1450,0,4600,3500,4700,2300,0,4950,3300,7550,2500,0,6300,4200},
            {2540,500,0,2100,2500,3110,780,0,3800,2900,3500,1750,0,5000,3600,5200,2700,0,5350,3700,8200,2700,0,7000,4500},
            {3010,620,0,2300,2700,3320,900,0,4200,3300,3900,2100,0,5400,4000,5900,3000,0,5650,4100,10500,3100,0,7400,4700}
    };

    private static final double[][] LARGE_HOUSE_IMPACT = {
            {2440,350,0,1800,2300,2700,610,0,3650,2600,3670,1500,0,4500,3500,4250,2150,0,5000,3530,6000,2600,0,6500,4100},
            {2727,570,0,2100,2600,3100,690,0,4050,2900,3870,1700,0,5000,3700,5050,2550,0,5300,3730,7800,3100,0,6800,4400},
            {3010,900,0,2450,2900,3300,820,0,4650,3300,4100,2000,0,5400,4200,5400,2850,0,5600,4200,8500,3600,0,7400,4900},
            {3577,980,0,2600,3300,3600,980,0,5150,3600,4300,2350,0,5700,4600,6200,3150,0,6000,4630,11100,4000,0,7800,5100}
    };
}
