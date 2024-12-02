package com.example.planetz.Calculator.HousingMethod;

public class TownhouseCalculator {

    /**
     * 计算联排别墅的碳排放
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
            {1971,300,2400,1500,2000,2800,500,2800,2500,2800,3000,1000,3600,3000,3000,4000,1600,4500,3700,3330,8000,2000,5000,5800,3500},
            {2160,410,2523,1850,2250,2910,550,3100,2800,3000,3210,1250,3750,3500,3300,5500,1750,4600,4500,3500,9500,2100,5200,6800,3700},
            {2500,500,2600,2100,2500,3000,580,3250,3400,3300,3500,1320,3900,4100,3520,6200,1900,4800,5200,3720,10200,2300,5300,7200,4000},
            {2800,550,2720,2500,2600,3200,600,3500,3800,3400,3800,1420,4050,5000,3800,8000,2000,5100,5800,4000,12000,3000,5600,7800,4300}  // 5
    };

    private static final double[][] MEDIUM_HOUSE_IMPACT = {
            {2443,300,2590,3170,1400,4000,550,3800,5600,2400,4300,1200,5000,6000,4000,4800,1700,5350,3680,3800,9500,2500,5370,6000,4000},
            {2750,380,2620,3770,1560,5000,700,4320,5940,2600,5500,1350,5800,6200,4310,6300,1900,5500,4280,3800,10100,2780,5500,6600,4640},
            {3111,500,2730,4670,1900,6500,950,4800,6140,3300,6800,1520,6200,6420,4600,8500,2150,5720,5180,4220,11200,3000,5800,6800,5000},
            {3580,590,2800,5570,2200,7300,1100,5500,6340,3800,8340,1700,6100,6500,5100,9000,2400,5900,6080,4400,14000,3500,6200,7400,5430},
    };

    private static final double[][] LARGE_HOUSE_IMPACT = {
            {2822,300,2810,3340,3800,3600,1200,4300,5900,3500,5000,1800,5300,3510,4100,8000,2400,5440,3800,4200,9500,2800,5670,6200,4300},
            {3010,560,3000,3940,4070,3840,1380,4900,6330,3930,6200,2000,5690,4110,4500,8300,2500,5600,4500,4640,1010,3000,5800,6900,4700},
            {3300,890,3468,4840,5000,3900,1600,5320,6440,4360,7000,2200,6250,5010,4780,9000,2650,5800,5380,5000,10300,3800,6100,7500,5100},
            {3600,1000,3760,5740,5600,5100,1750,5800,6900,5000,8000,2300,6500,5910,5360,9500,2800,6000,6200,5400,11000,4300,6350,7850,5500},
    };
}
