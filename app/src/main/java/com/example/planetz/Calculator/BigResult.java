package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;
import com.example.planetz.Calculator.HousingCalculator;
import com.example.planetz.Calculator.FoodCalculator;
import com.example.planetz.Calculator.TransportationCalculator;
import com.example.planetz.Calculator.ConsumptionCalculator;
public class BigResult {

    private TransportationCalculator transportationCalculator = new TransportationCalculator();
    private HousingCalculator housingCalculator = new HousingCalculator();
    private ConsumptionCalculator consumptionCalculator = new ConsumptionCalculator();
    private FoodCalculator foodCalculator = new FoodCalculator();

    /**
     * 计算总碳足迹
     *
     * @param transportationData 交通相关数据
     * @param housingData        住房相关数据
     * @param consumptionData    消费相关数据
     * @param foodData           饮食相关数据
     * @return 总碳足迹（单位：吨 CO2e）
     */
    public double calculateTotalCarbonFootprint(CarbonFootprintData transportationData,
                                                CarbonFootprintData housingData,
                                                CarbonFootprintData consumptionData,
                                                CarbonFootprintData foodData) {
        // 交通排放
        double transportationEmission = transportationCalculator.calculateTransportationEmission(transportationData);

        // 住房排放
        double housingEmission = housingCalculator.calculateHousingEmission(housingData);

        // 消费排放
        double consumptionEmission = consumptionCalculator.calculateConsumptionEmission(consumptionData);

        double foodEmission = foodCalculator.calculateFoodEmission(foodData);

        return transportationEmission + housingEmission + consumptionEmission + foodEmission;
    }
}