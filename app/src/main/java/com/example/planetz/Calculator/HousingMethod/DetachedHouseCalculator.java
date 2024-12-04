package com.example.planetz.Calculator.HousingMethod;

import com.example.planetz.Calculator.HousingCalculator;

public class DetachedHouseCalculator {

    /**
     * 计算独立屋的碳排放
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
            {2400,200,2100,2870,2170,2440,400,5200,4400,2340,2610,1200,6100,5400,2510,2780,1700,7200,6400,2680,3100,2300,8200,7400,3000},
            {2600,250,2650,3470,2370,2640,500,5400,4600,2540,2810,1450,6250,5600,2710,2980,1900,7400,6600,2880,3100,2500,8400,7600,3200},
            {2700,380,3200,4370,2670,2940,550,5700,4900,2840,3110,1600,6700,5900,3010,3280,2050,7700,6900,3180,3600,2700,8700,7900,3500},
            {3000,450,3700,5270,2970,3240,600,6000,5200,3140,3410,1800,6950,6200,3310,3580,2200,8000,7200,3480,3900,3000,9000,8200,3800}
    };

    private static final double[][] MEDIUM_HOUSE_IMPACT = {
            {2800,300,3800,3770,3670,5900,600,5300,3940,3840,6500,1200,6200,7100,4010,7100,1800,7200,4280,4180,8300,2400,8200,4600,4500},
            {3000,380,4000,4470,4170,6100,800,5440,4640,4340,6700,1450,6400,7230,4510,7300,2000,7400,4980,4680,8500,2600,8400,5300,5000},
            {3380,500,4700,5670,4870,6400,1050,5800,5740,5040,7000,1600,6700,7400,5210,7600,2250,7700,5985,5380,8800,2800,8700,6350,5750},
            {3860,600,5350,6570,5670,6700,1200,6100,6740,5840,7300,1800,7000,7550,6010,7900,2400,8000,7080,6180,9100,3100,9000,7400,6500}
    };

    private static final double[][] LARGE_HOUSE_IMPACT = {
            {2800,320,5400,5570,4170,3000,600,10500,5740,4340,3200,1800,14000,5800,4510,3400,2700,17500,5852,4680,3600,3600,21000,6100,5000},
            {2880,450,6200,6170,4670,3200,900,11000,6340,4840,3400,2100,15500,6410,5010,3600,3100,18100,6560,5180,3800,3800,22000,6840,5500},
            {3000,520,7000,6970,5270,3500,1500,12500,7240,5640,3700,2300,16250,7300,5710,4100,3400,20000,7600,5980,4100,4000,23500,7890,6250},
            {3230,675,8900,7970,6170,3800,1800,14000,8140,6340,4000,2700,17500,8230,6510,4400,3600,21000,8300,6680,4400,4200,25000,8710,7000},
    };
}