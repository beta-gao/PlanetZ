package com.example.planetz.model;

/**
 * CarbonFootprintData class stores user input data needed to calculate carbon footprint.
 * This class is implemented as a Singleton to ensure a single shared instance throughout the application.
 */
public class CarbonFootprintData {


    private static CarbonFootprintData instance;


    private boolean isUsingVehicle;
    private VehicleType vehicleType;
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

    // Getters and Setters for all fields
    public boolean isUsingVehicle() {
        return isUsingVehicle;
    }

    public void setUsingVehicle(boolean isUsingVehicle) {
        this.isUsingVehicle = isUsingVehicle;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
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
        this.monthlyElectricityBill = monthlyElectricityBill;
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


    public void clear() {
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
}