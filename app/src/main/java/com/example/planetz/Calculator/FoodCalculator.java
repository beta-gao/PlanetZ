package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;

public class FoodCalculator {

    public double calculateFoodEmission(CarbonFootprintData data) {
        double dietEmission = calculateDietEmission(data.getDietType());

        if (data.getDietType().equalsIgnoreCase("Meat-based")) {
            dietEmission += calculateMeatConsumptionEmission(
                    data.getBeefFrequency(),
                    data.getPorkFrequency(),
                    data.getChickenFrequency(),
                    data.getFishFrequency()
            );
        }

        dietEmission += calculateFoodWasteEmission(data.getFoodWasteFrequency());

        return dietEmission/1000;
    }

    private double calculateDietEmission(String dietType) {
        switch (dietType) {
            case "Vegetarian":
                return 1000.0;
            case "Vegan":
                return 500.0;
            case "Pescatarian (fish/seafood)":
                return 1500.0;
            case "Meat-based (eat all types of animal products)":
                return 0.0;
            default:
                throw new IllegalArgumentException("Unknown diet type");
        }
    }


    private double calculateMeatConsumptionEmission(String beefFrequency, String porkFrequency, String chickenFrequency, String fishFrequency) {
        double beefEmission = getMeatEmission(beefFrequency, 2500, 1900, 1300, 0);
        double porkEmission = getMeatEmission(porkFrequency, 1450, 860, 450, 0);
        double chickenEmission = getMeatEmission(chickenFrequency, 950, 600, 200, 0);
        double fishEmission = getMeatEmission(fishFrequency, 800, 500, 150, 0);

        return beefEmission + porkEmission + chickenEmission + fishEmission;
    }


    private double getMeatEmission(String frequency, double dailyEmission, double frequentEmission, double occasionalEmission, double neverEmission) {
        switch (frequency) {
            case "Daily":
                return dailyEmission;
            case "Frequently":
                return frequentEmission;
            case "Occasionally":
                return occasionalEmission;
            case "Never":
                return neverEmission;
            default:
                throw new IllegalArgumentException("Unknown frequency type");
        }
    }


    private double calculateFoodWasteEmission(String foodWasteFrequency) {
        switch (foodWasteFrequency) {
            case "Never":
                return 0.0;
            case "Rarely":
                return 23.4;
            case "Occasionally":
                return 70.2;
            case "Frequently":
                return 140.4;
            default:
                throw new IllegalArgumentException("Unknown food waste frequency");
        }
    }


}
