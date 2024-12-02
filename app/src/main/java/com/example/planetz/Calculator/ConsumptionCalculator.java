package com.example.planetz.Calculator;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;
public class ConsumptionCalculator {


    public double calculateConsumptionEmission(CarbonFootprintData data) {

        String clothingFrequency = data.getClothingPurchaseFrequency();
        String ecoFriendlyProductUsage = data.getSecondHandOrEcoFriendlyProducts();
        String electronicDevicesPurchased = data.getElectronicDevicesPurchased();
        String recyclingFrequency = data.getRecyclingFrequency();

        int frequency = mapClothingFrequency(clothingFrequency);
        int ecoFriendlyUsage = mapEcoFriendlyProductUsage(ecoFriendlyProductUsage);
        int devicesCount = mapElectronicDevicesCount(electronicDevicesPurchased);
        int recyclingFreq = mapRecyclingFrequency(recyclingFrequency);

        double clothingEmission = calculateClothingEmission(frequency, ecoFriendlyUsage);
        double electronicDevicesEmission = calculateElectronicDevicesEmission(devicesCount);
        double recyclingReduction = calculateRecyclingReduction(frequency, recyclingFreq, devicesCount);

        return (clothingEmission + electronicDevicesEmission - recyclingReduction)/1000;
    }

    public static int mapClothingFrequency(String frequency) {
        switch (frequency) {
            case "Monthly":
                return 1;
            case "Quarterly":
                return 2;
            case "Annually":
                return 3;
            case "Rarely":
                return 4;
            default:
                throw new IllegalArgumentException("Unknown clothing frequency: " + frequency);
        }
    }

    public static int mapEcoFriendlyProductUsage(String usage) {
        switch (usage) {
            case "Yes, regularly":
                return 1;
            case "Yes, occasionally":
                return 2;
            case "No":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown eco-friendly product usage: " + usage);
        }
    }

    public static int mapElectronicDevicesCount(String count) {
        switch (count) {
            case "None":
                return 0;
            case "1":
                return 1;
            case "2":
                return 2;
            case "3 or more":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown electronic devices count: " + count);
        }
    }

    public static int mapRecyclingFrequency(String frequency) {
        switch (frequency) {
            case "Never":
                return 0;
            case "Occasionally":
                return 1;
            case "Frequently":
                return 2;
            case "Always":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown recycling frequency: " + frequency);
        }
    }
    /**
     * 计算新衣服购买的碳排放
     *
     * @param frequency 新衣服购买频率
     * @param ecoFriendlyProductUsage 是否购买二手或环保产品
     * @return 衣服相关的碳排放（单位：kg）
     */
    private double calculateClothingEmission(int frequency, int ecoFriendlyProductUsage) {
        double baseEmission;
        switch (frequency) {
            case 1: // Monthly
                baseEmission = 360;
                break;
            case 2: // Quarterly
                baseEmission = 120;
                break;
            case 3: // Annually
                baseEmission = 100;
                break;
            case 4: // Rarely
                baseEmission = 5;
                break;
            default:
                throw new IllegalArgumentException("Unknown clothing frequency: " + frequency);
        }

        switch (ecoFriendlyProductUsage) {
            case 1: // Yes, regularly
                return baseEmission * 0.5;
            case 2: // Yes, occasionally
                return baseEmission * 0.7;
            case 3: // No
                return baseEmission;
            default:
                throw new IllegalArgumentException("Unknown eco-friendly product usage: " + ecoFriendlyProductUsage);
        }
    }

    /**
     * 计算电子设备购买的碳排放
     * @param count 过去一年购买的电子设备数量（整数）
     * @return 电子设备相关的碳排放（单位：kg）
     */
    private double calculateElectronicDevicesEmission(int count) {
        switch (count) {
            case 0: // None
                return 0;
            case 1: // 1 device
                return 300;
            case 2: // 2 devices
                return 600;
            case 3: // 3 or more devices
                return 900;
            default: // Invalid count
                return 1200;
        }
    }

    /**
     * 计算回收对碳排放的减排
     * @param buyinghabit 购买的习惯
     * @param recyclingFrequency 回收频率（整数）
     * @return 回收的减排量（单位：kg）
     */
    private double calculateRecyclingReduction(int buyinghabit, int recyclingFrequency, int devicesamount) {
        if(recyclingFrequency == 0){
            return 0;
        }
        double reductionPerHabit = Habit_Recycle[buyinghabit-1][recyclingFrequency-1];
        if (devicesamount == 0) {
            return reductionPerHabit;
        }
        double reductionPerDevice = Device_Recycle[devicesamount][recyclingFrequency-1];

        return reductionPerDevice + reductionPerHabit;
    }
    private static final double[][] Habit_Recycle = {
            {54, 108, 180},
            {15, 30, 50},
            {15, 30, 50},
            {0.75,1.5,2.5}
    };

    private static final double[][] Device_Recycle = {
            {0, 0, 0},
            {45, 60, 90},
            {60,120,180},
            {120.240,360}
    };

}

