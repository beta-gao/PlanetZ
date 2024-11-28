package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;
import static com.example.planetz.model.VehicleType.*;

public class TransportationCalculator {

    public double calculateVehicleEmission(CarbonFootprintData data) {
        if (!data.isUsingVehicle()) {
            return 0.0;
        }

        // 将字符串转换为枚举
        VehicleType vehicleType;
        try {
            vehicleType = VehicleType.valueOf(data.getVehicleType().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Invalid vehicle type: " + data.getVehicleType());
        }

        double emissionFactor;
        switch (vehicleType) {
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
                throw new IllegalArgumentException("Unknown vehicle type");
        }

        int annualMileage = data.getAnnualMileage();
        return emissionFactor * annualMileage;
    }


    public double calculatePublicTransportEmission(CarbonFootprintData data) {
        String frequency = data.getPublicTransportFrequency();
        String timeSpent = data.getPublicTransportTime();

        int emissionFactor;
        if (frequency.contains("Never")) {
            frequency = "Never";
        } else if (frequency.contains("Occasionally")) {
            frequency = "Occasionally";
        } else if (frequency.contains("Frequently")) {
            frequency = "Frequently";
        } else if (frequency.contains("Always")) {
            frequency = "Always";
        }

        switch (frequency) {
            case "Never":
                return 0.0;
            case "Occasionally":
                emissionFactor = timeSpent.equals("Under 1 hour") ? 246 :
                        timeSpent.equals("1-3 hours") ? 819 :
                                timeSpent.equals("3-5 hours") ? 1638 :
                                        timeSpent.equals("5-10 hours") ? 3071 :
                                                4095;
                break;
            case "Frequently":
            case "Always":
                emissionFactor = timeSpent.equals("Under 1 hour") ? 573 :
                        timeSpent.equals("1-3 hours") ? 1911 :
                                timeSpent.equals("3-5 hours") ? 3822 :
                                        timeSpent.equals("5-10 hours") ? 7166 :
                                                9555;
                break;
            default:
                emissionFactor = 0; // 提供默认值
                break;
        }


        return emissionFactor;
    }

    public double calculateFlightEmission(CarbonFootprintData data) {
        String shortHaul = data.getShortHaulFlights();
        String longHaul = data.getLongHaulFlights();

        double shortHaulEmission = shortHaul.equals("None") ? 0 :
                shortHaul.equals("1-2 flights") ? 225 :
                        shortHaul.equals("3-5 flights") ? 600 :
                                shortHaul.equals("6-10 flights") ? 1200 :
                                        1800;

        double longHaulEmission = longHaul.equals("None") ? 0 :
                longHaul.equals("1-2 flights") ? 825 :
                        longHaul.equals("3-5 flights") ? 2200 :
                                longHaul.equals("6-10 flights") ? 4400 :
                                        6600;

        return shortHaulEmission + longHaulEmission;
    }


    public double calculateTransportationEmission(CarbonFootprintData data) {
        double vehicleEmission = calculateVehicleEmission(data);
        double publicTransportEmission = calculatePublicTransportEmission(data);
        double flightEmission = calculateFlightEmission(data);

        return (vehicleEmission + publicTransportEmission + flightEmission)/1000;
    }

}
