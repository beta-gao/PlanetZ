package com.example.planetz.data;

import android.util.Log;

public class Consumption {
    private double energyUse;
    private double foodConsumption;
    private double shoppingAndConsumption;
    private double totalEmissions;
    private double transportation;

    private static final String TAG = "Consumption";

    public Consumption() {
        Log.d(TAG, "Consumption: Default constructor called");
    }

    public Consumption(double energyUse, double foodConsumption,
                       double shoppingAndConsumption, double transportation) {
        Log.d(TAG, "Consumption: Constructor with parameters called");
        setEnergyUse(energyUse);
        setFoodConsumption(foodConsumption);
        setShoppingAndConsumption(shoppingAndConsumption);
        setTransportation(transportation);
    }

    public Consumption getConsumption() {
        Log.d(TAG, "getConsumption: Returning this instance");
        return this;
    }

    public void setEnergyUse(double energyUse) {
        Log.d(TAG, "setEnergyUse: Setting energyUse to " + energyUse);
        if (energyUse < 0) {
            Log.e(TAG, "setEnergyUse: Invalid energyUse value " + energyUse);
            throw new IllegalArgumentException("Energy use must be greater than or equal to 0.");
        }
        this.energyUse = energyUse;
        updateTotalEmissions();
    }

    public void setFoodConsumption(double foodConsumption) {
        Log.d(TAG, "setFoodConsumption: Setting foodConsumption to " + foodConsumption);
        if (foodConsumption < 0) {
            Log.e(TAG, "setFoodConsumption: Invalid foodConsumption value " + foodConsumption);
            throw new IllegalArgumentException("Food Consumption must be greater than or equal to 0.");
        }
        this.foodConsumption = foodConsumption;
        updateTotalEmissions();
    }

    public void setShoppingAndConsumption(double shoppingAndConsumption) {
        Log.d(TAG, "setShoppingAndConsumption: Setting shoppingAndConsumption to " + shoppingAndConsumption);
        if (shoppingAndConsumption < 0) {
            Log.e(TAG, "setShoppingAndConsumption: Invalid shoppingAndConsumption value " + shoppingAndConsumption);
            throw new IllegalArgumentException("Shopping and consumption must be greater than or equal to 0.");
        }
        this.shoppingAndConsumption = shoppingAndConsumption;
        updateTotalEmissions();
    }

    public void setTransportation(double transportation) {
        Log.d(TAG, "setTransportation: Setting transportation to " + transportation);
        if (transportation < 0) {
            Log.e(TAG, "setTransportation: Invalid transportation value " + transportation);
            throw new IllegalArgumentException("Transportation must be greater than or equal to 0.");
        }
        this.transportation = transportation;
        updateTotalEmissions();
    }

    public void updateTotalEmissions() {
        this.totalEmissions = energyUse + foodConsumption + shoppingAndConsumption + transportation;
        Log.d(TAG, "updateTotalEmissions: Total emissions updated to " + totalEmissions);
    }

    public double getEnergyUse() {
        Log.d(TAG, "getEnergyUse: Returning energyUse " + energyUse);
        return energyUse;
    }

    public double getFoodConsumption() {
        Log.d(TAG, "getFoodConsumption: Returning foodConsumption " + foodConsumption);
        return foodConsumption;
    }

    public double getShoppingAndConsumption() {
        Log.d(TAG, "getShoppingAndConsumption: Returning shoppingAndConsumption " + shoppingAndConsumption);
        return shoppingAndConsumption;
    }

    public double getTotalEmissions() {
        Log.d(TAG, "getTotalEmissions: Returning totalEmissions " + totalEmissions);
        return totalEmissions;
    }

    public double getTransportation() {
        Log.d(TAG, "getTransportation: Returning transportation " + transportation);
        return transportation;
    }
}
