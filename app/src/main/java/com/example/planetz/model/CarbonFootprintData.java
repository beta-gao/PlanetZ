package com.example.planetz.model;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CarbonFootprintData class stores user input data needed to calculate carbon footprint.
 * This class is implemented as a Singleton to ensure a single shared instance throughout the application.
 */
public class CarbonFootprintData {

    private static CarbonFootprintData instance;

    // Fields
    private String userId;
    private boolean isUsingVehicle;
    private String vehicleType; // Changed to String for Firestore compatibility
    private int annualMileage;
    private String dietType;
    private String publicTransportFrequency;
    private String publicTransportTime;
    private String shortHaulFlights;
    private String longHaulFlights;
    private String beefFrequency;
    private String porkFrequency;
    private String chickenFrequency;
    private String fishFrequency;
    private String foodWasteFrequency;
    private String homeType;
    private String householdSize;
    private String homeSize;
    private String homeHeatingType;
    private String monthlyElectricityBill;
    private String waterHeatingType;
    private String renewableEnergyUse;
    private String clothingPurchaseFrequency;
    private String secondHandOrEcoFriendlyProducts;
    private String electronicDevicesPurchased;
    private String recyclingFrequency;

    private CarbonFootprintData() {}

    /**
     * Provides the single instance of CarbonFootprintData.
     * @return the singleton instance of CarbonFootprintData.
     */
    public static CarbonFootprintData getInstance() {
        if (instance == null) {
            instance = new CarbonFootprintData();
        }
        return instance;
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isUsingVehicle() {
        return isUsingVehicle;
    }

    public void setUsingVehicle(boolean usingVehicle) {
        isUsingVehicle = usingVehicle;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getAnnualMileage() {
        return annualMileage;
    }

    public void setAnnualMileage(int annualMileage) {
        this.annualMileage = annualMileage;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getPublicTransportFrequency() {
        return publicTransportFrequency;
    }

    public void setPublicTransportFrequency(String publicTransportFrequency) {
        this.publicTransportFrequency = publicTransportFrequency;
    }

    public String getPublicTransportTime() {
        return publicTransportTime;
    }

    public void setPublicTransportTime(String publicTransportTime) {
        this.publicTransportTime = publicTransportTime;
    }

    public String getShortHaulFlights() {
        return shortHaulFlights;
    }

    public void setShortHaulFlights(String shortHaulFlights) {
        this.shortHaulFlights = shortHaulFlights;
    }

    public String getLongHaulFlights() {
        return longHaulFlights;
    }

    public void setLongHaulFlights(String longHaulFlights) {
        this.longHaulFlights = longHaulFlights;
    }

    public String getBeefFrequency() {
        return beefFrequency;
    }

    public void setBeefFrequency(String beefFrequency) {
        this.beefFrequency = beefFrequency;
    }

    public String getPorkFrequency() {
        return porkFrequency;
    }

    public void setPorkFrequency(String porkFrequency) {
        this.porkFrequency = porkFrequency;
    }

    public String getChickenFrequency() {
        return chickenFrequency;
    }

    public void setChickenFrequency(String chickenFrequency) {
        this.chickenFrequency = chickenFrequency;
    }

    public String getFishFrequency() {
        return fishFrequency;
    }

    public void setFishFrequency(String fishFrequency) {
        this.fishFrequency = fishFrequency;
    }

    public String getFoodWasteFrequency() {
        return foodWasteFrequency;
    }

    public void setFoodWasteFrequency(String foodWasteFrequency) {
        this.foodWasteFrequency = foodWasteFrequency;
    }

    public String getHomeType() {
        return homeType;
    }

    public void setHomeType(String homeType) {
        this.homeType = homeType;
    }

    public String getHouseholdSize() {
        return householdSize;
    }

    public void setHouseholdSize(String householdSize) {
        this.householdSize = householdSize;
    }

    public String getHomeSize() {
        return homeSize;
    }

    public void setHomeSize(String homeSize) {
        this.homeSize = homeSize;
    }

    public String getHomeHeatingType() {
        return homeHeatingType;
    }

    public void setHomeHeatingType(String homeHeatingType) {
        this.homeHeatingType = homeHeatingType;
    }

    public String getMonthlyElectricityBill() {
        return monthlyElectricityBill;
    }

    public void setMonthlyElectricityBill(String monthlyElectricityBill) {
        List<String> validBills = Arrays.asList("Under $50", "$50-$100", "$100-$150", "$150-$200", "Over $200");
        if (validBills.contains(monthlyElectricityBill)) {
            this.monthlyElectricityBill = monthlyElectricityBill;
        } else {
            Log.w("CarbonFootprintData", "Invalid electricity bill: " + monthlyElectricityBill);
            this.monthlyElectricityBill = "$50-$100"; // 默认值
        }
    }


    public String getWaterHeatingType() {
        return waterHeatingType;
    }

    public void setWaterHeatingType(String waterHeatingType) {
        this.waterHeatingType = waterHeatingType;
    }

    public String getRenewableEnergyUse() {
        return renewableEnergyUse;
    }

    public void setRenewableEnergyUse(String renewableEnergyUse) {
        this.renewableEnergyUse = renewableEnergyUse;
    }

    public String getClothingPurchaseFrequency() {
        return clothingPurchaseFrequency;
    }

    public void setClothingPurchaseFrequency(String clothingPurchaseFrequency) {
        this.clothingPurchaseFrequency = clothingPurchaseFrequency;
    }

    public String getSecondHandOrEcoFriendlyProducts() {
        return secondHandOrEcoFriendlyProducts;
    }

    public void setSecondHandOrEcoFriendlyProducts(String secondHandOrEcoFriendlyProducts) {
        this.secondHandOrEcoFriendlyProducts = secondHandOrEcoFriendlyProducts;
    }

    public String getElectronicDevicesPurchased() {
        return electronicDevicesPurchased;
    }

    public void setElectronicDevicesPurchased(String electronicDevicesPurchased) {
        this.electronicDevicesPurchased = electronicDevicesPurchased;
    }

    public String getRecyclingFrequency() {
        return recyclingFrequency;
    }

    public void setRecyclingFrequency(String recyclingFrequency) {
        this.recyclingFrequency = recyclingFrequency;
    }

    // Clear method
    public void clear() {
        userId = null;
        isUsingVehicle = false;
        vehicleType = null;
        annualMileage = 0;
        dietType = null;
        publicTransportFrequency = null;
        publicTransportTime = null;
        shortHaulFlights = null;
        longHaulFlights = null;
        beefFrequency = null;
        porkFrequency = null;
        chickenFrequency = null;
        fishFrequency = null;
        foodWasteFrequency = null;
        homeType = null;
        householdSize = null;
        homeSize = null;
        homeHeatingType = null;
        monthlyElectricityBill = null;
        waterHeatingType = null;
        renewableEnergyUse = null;
        clothingPurchaseFrequency = null;
        secondHandOrEcoFriendlyProducts = null;
        electronicDevicesPurchased = null;
        recyclingFrequency = null;
    }

    // Convert to Map for Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("isUsingVehicle", isUsingVehicle);
        map.put("vehicleType", vehicleType);
        map.put("annualMileage", annualMileage);
        map.put("dietType", dietType);
        map.put("publicTransportFrequency", publicTransportFrequency);
        map.put("publicTransportTime", publicTransportTime);
        map.put("shortHaulFlights", shortHaulFlights);
        map.put("longHaulFlights", longHaulFlights);
        map.put("beefFrequency", beefFrequency);
        map.put("porkFrequency", porkFrequency);
        map.put("chickenFrequency", chickenFrequency);
        map.put("fishFrequency", fishFrequency);
        map.put("foodWasteFrequency", foodWasteFrequency);
        map.put("homeType", homeType);
        map.put("householdSize", householdSize);
        map.put("homeSize", homeSize);
        map.put("homeHeatingType", homeHeatingType);
        map.put("monthlyElectricityBill", monthlyElectricityBill);
        map.put("waterHeatingType", waterHeatingType);
        map.put("renewableEnergyUse", renewableEnergyUse);
        map.put("clothingPurchaseFrequency", clothingPurchaseFrequency);
        map.put("secondHandOrEcoFriendlyProducts", secondHandOrEcoFriendlyProducts);
        map.put("electronicDevicesPurchased", electronicDevicesPurchased);
        map.put("recyclingFrequency", recyclingFrequency);
        return map;
    }

    // Save to Firestore
    public void saveToFirestore(FirebaseFirestore db, String userId, FirestoreSaveCallback callback) {
        this.userId = userId;

        db.collection("carbonFootprints")
                .document(userId)
                .set(toMap())
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(callback::onFailure);
    }

    public interface FirestoreSaveCallback {
        void onSuccess();
        void onFailure(Exception e);
    }
}