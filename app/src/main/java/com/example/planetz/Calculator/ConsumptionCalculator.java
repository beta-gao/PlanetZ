package com.example.planetz.Calculator;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;
public class ConsumptionCalculator {

    /**
     * 计算消费相关的碳排放
     *
     * @param clothingFrequency 新衣服购买频率
     * @param ecoFriendlyProductUsage 是否购买二手或环保产品
     * @param electronicDevicesPurchased 过去一年购买的电子设备数量
     * @param recyclingFrequency 回收频率
     * @return 总碳排放（单位：kg）
     */
    public double calculateConsumptionEmission(String clothingFrequency, String ecoFriendlyProductUsage,
                                               int electronicDevicesPurchased, String recyclingFrequency) {
        double clothingEmission = calculateClothingEmission(clothingFrequency, ecoFriendlyProductUsage);
        double electronicDevicesEmission = calculateElectronicDevicesEmission(electronicDevicesPurchased);
        double recyclingReduction = calculateRecyclingReduction(electronicDevicesPurchased, recyclingFrequency);

        // 总排放 = 衣服 + 电子设备 - 回收减排
        return clothingEmission + electronicDevicesEmission - recyclingReduction;
    }

    /**
     * 计算新衣服购买的碳排放
     *
     * @param frequency 新衣服购买频率
     * @param ecoFriendlyProductUsage 是否购买二手或环保产品
     * @return 衣服相关的碳排放（单位：kg）
     */
    private double calculateClothingEmission(String frequency, String ecoFriendlyProductUsage) {
        double baseEmission;
        switch (frequency) {
            case "Monthly":
                baseEmission = 360;
                break;
            case "Quarterly":
                baseEmission = 120;
                break;
            case "Annually":
                baseEmission = 100;
                break;
            case "Rarely":
                baseEmission = 5;
                break;
            default:
                throw new IllegalArgumentException("Unknown clothing frequency: " + frequency);
        }

        // 如果用户购买二手或环保产品
        if ("Yes, regularly".equalsIgnoreCase(ecoFriendlyProductUsage)) {
            return baseEmission * 0.5; // 减少 50%
        } else if ("Yes, occasionally".equalsIgnoreCase(ecoFriendlyProductUsage)) {
            return baseEmission * 0.7; // 减少 30%
        } else if ("No".equalsIgnoreCase(ecoFriendlyProductUsage)) {
            return baseEmission;
        }

        throw new IllegalArgumentException("Unknown eco-friendly product usage: " + ecoFriendlyProductUsage);
    }

    /**
     * 计算电子设备购买的碳排放
     *
     * @param count 过去一年购买的电子设备数量
     * @return 电子设备相关的碳排放（单位：kg）
     */
    private double calculateElectronicDevicesEmission(int count) {
        switch (count) {
            case 0:
                return 0;
            case 1:
                return 300;
            case 2:
                return 600;
            case 3:
                return 900;
            case 4: // 4 或更多
            default:
                return 1200;
        }
    }

    /**
     * 计算回收对碳排放的减排
     *
     * @param electronicDevicesPurchased 购买的电子设备数量
     * @param recyclingFrequency 回收频率
     * @return 回收的减排量（单位：kg）
     */
    private double calculateRecyclingReduction(int electronicDevicesPurchased, String recyclingFrequency) {
        int reductionPerDevice;
        switch (recyclingFrequency) {
            case "Never":
                return 0;
            case "Occasionally":
                reductionPerDevice = 45;
                break;
            case "Frequently":
                reductionPerDevice = 60;
                break;
            case "Always":
                reductionPerDevice = 90;
                break;
            default:
                throw new IllegalArgumentException("Unknown recycling frequency: " + recyclingFrequency);
        }

        // 总减排 = 每个设备的减排 * 购买的设备数量
        return reductionPerDevice * electronicDevicesPurchased;
    }

}

