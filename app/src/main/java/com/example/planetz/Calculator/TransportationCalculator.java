package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;

public class TransportationCalculator {

    public static double calculate(CarbonFootprintData data) {
        double totalEmission = 0.0;

        // 计算车辆排放
        if (data.isUsingVehicle() && data.getVehicleType() != null) {
            double emissionFactor;
            switch (data.getVehicleType()) {
                case GASOLINE:
                    emissionFactor = 0.24;
                    break;
                case DIESEL:
                    emissionFactor = 0.27;
                    break;
                case HYBRID:
                    emissionFactor = 0.16;
                    break;
                case ELECTRIC:
                    emissionFactor = 0.05;
                    break;
                default:
                    emissionFactor = 0.0;
            }
            totalEmission += emissionFactor * data.getAnnualMileage();
        }

        // 计算公共交通排放
        String frequency = data.getPublicTransportFrequency();
        String timeSpent = data.getPublicTransportTime();
        if (frequency != null && timeSpent != null) {
            totalEmission += calculatePublicTransportEmission(frequency, timeSpent);
        }

        return totalEmission;
    }

    private static double calculatePublicTransportEmission(String frequency, String timeSpent) {
        double emission = 0.0;

        // 根据频率和时间计算
        switch (frequency) {
            case "Never":
                return 0.0;
            case "Occasionally (1-2 times/week)":
                emission = 246; // 示例数据
                break;
            case "Frequently (3-4 times/week)":
                emission = 573; // 示例数据
                break;
            case "Always (5+ times/week)":
                emission = 957; // 示例数据
                break;
        }

        // 时间的加权
        if (timeSpent.equals("Under 1 hour")) {
            emission *= 0.5;
        } else if (timeSpent.equals("1-3 hours")) {
            emission *= 1.0;
        } else if (timeSpent.equals("3-5 hours")) {
            emission *= 1.5;
        }

        return emission;
    }
}