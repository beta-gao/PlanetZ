package com.example.planetz.Calculator;

import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;

public class HousingCalculator {

    /**
     * 计算住房碳排放
     *
     * @param data CarbonFootprintData 实例
     * @return 碳排放（单位：kg）
     */
    public double calculateHousingEmission(CarbonFootprintData data) {
        double baseEmission = calculateBaseEmission(
                data.getHomeType(),
                data.getHouseholdSize(),
                data.getHomeSize(),
                data.getMonthlyElectricityBill(),
                data.getHomeHeatingType()
        );

        if (!data.getHomeHeatingType().equalsIgnoreCase(data.getWaterHeatingType())) {
            baseEmission += 233; // 固定增加 233 kg
        }

        baseEmission -= calculateRenewableEnergyReduction(data.getRenewableEnergyUse());

        return baseEmission;
    }

    /**
     * 计算基础碳排放
     *
     * @param homeType               住房类型
     * @param householdSize          家庭成员数量
     * @param homeSize               房屋面积
     * @param monthlyElectricityBill 月电费
     * @param heatingType            供暖能源类型
     * @return 碳排放（单位：kg）
     */
    private double calculateBaseEmission(String homeType, String householdSize, String homeSize, String monthlyElectricityBill, String heatingType) {
        int occupants = parseHouseholdSize(householdSize);
        int sizeCategory = parseHomeSize(homeSize);
        int billCategory = parseElectricityBill(monthlyElectricityBill);
        int heatingCategory = parseHeatingType(heatingType);

        switch (homeType) {
            case "Detached house":
                DetachedHouseCalculator detachedCalculator = new DetachedHouseCalculator();
                return detachedCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Semi-detached house":
                SemiDetachedHouseCalculator semiDetachedCalculator = new SemiDetachedHouseCalculator();
                return semiDetachedCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Townhouse":
            case "Other":
                TownhouseCalculator townhouseCalculator = new TownhouseCalculator();
                return townhouseCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            case "Condo/Apartment":
                CondoApartmentCalculator condoCalculator = new CondoApartmentCalculator();
                return condoCalculator.calculateEmission(homeSize, occupants, billCategory, heatingCategory);
            default:
                throw new IllegalArgumentException("Unknown home type: " + homeType);
        }
    }


    /**
     * 解析家庭成员数量
     *
     * @param householdSize 家庭成员数量
     * @return 数值化的成员数量
     */
    private int parseHouseholdSize(String householdSize) {
        switch (householdSize) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3-4":
                return 4;
            case "5 or more":
                return 5;
            default:
                throw new IllegalArgumentException("Unknown household size");
        }
    }

    /**
     * 解析房屋面积类别
     *
     * @param homeSize 房屋面积
     * @return 面积类别
     */
    private int parseHomeSize(String homeSize) {
        switch (homeSize) {
            case "Under 1000 sq. ft.":
                return 1;
            case "1000-2000 sq. ft.":
                return 2;
            case "Over 2000 sq. ft.":
                return 3;
            default:
                throw new IllegalArgumentException("Unknown home size");
        }
    }

    /**
     * 解析月电费类别
     *
     * @param monthlyElectricityBill 月电费
     * @return 电费类别
     */
    private int parseElectricityBill(String monthlyElectricityBill) {
        switch (monthlyElectricityBill) {
            case "Under $50":
                return 1;
            case "$50-$100":
                return 2;
            case "$100-$150":
                return 3;
            case "$150-$200":
                return 4;
            case "Over $200":
                return 5;
            default:
                throw new IllegalArgumentException("Unknown electricity bill");
        }
    }

    /**
     * 解析供暖能源类型
     *
     * @param heatingType 供暖能源类型
     * @return 能源类别
     */
    private int parseHeatingType(String heatingType) {
        switch (heatingType) {
            case "Natural Gas":
                return 1;
            case "Electricity":
                return 2;
            case "Oil":
                return 3;
            case "Propane":
                return 4;
            case "Wood":
                return 5;
            case "Other":
                return 6;
            default:
                throw new IllegalArgumentException("Unknown heating type");
        }
    }

    /**
     * 计算可再生能源减排
     *
     * @param renewableEnergyUse 可再生能源使用情况
     * @return 减排值（单位：kg）
     */
    private double calculateRenewableEnergyReduction(String renewableEnergyUse) {
        switch (renewableEnergyUse) {
            case "Yes, primarily":
                return 6000.0;
            case "Yes, partially":
                return 4000.0;
            case "No":
                return 0.0;
            default:
                throw new IllegalArgumentException("Unknown renewable energy use");
        }
    }

    public class DetachedHouseCalculator {

        /**
         * 计算独立屋的碳排放
         *
         * @param homeSize     房屋面积
         * @param occupants    家庭成员数量
         * @param billCategory 月电费类别
         * @param heatingType  供暖能源类型
         * @return 碳排放值（单位：kg）
         */
        public double calculateEmission(String homeSize, int occupants, int billCategory, int heatingType) {
            double baseEmission = 0.0;

            // 根据房屋面积分类
            switch (homeSize) {
                case "Under 1000 sq. ft.":
                    baseEmission = calculateForSmallHouse(occupants, billCategory, heatingType);
                    break;
                case "1000-2000 sq. ft.":
                    baseEmission = calculateForMediumHouse(occupants, billCategory, heatingType);
                    break;
                case "Over 2000 sq. ft.":
                    baseEmission = calculateForLargeHouse(occupants, billCategory, heatingType);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown home size: " + homeSize);
            }

            return baseEmission;
        }

        private double calculateForSmallHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2400;
                    break;
                case 2:
                    emission = 2600;
                    break;
                case 3:
                case 4:
                    emission = 2700;
                    break;
                case 5:
                    emission = 3000;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for small house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactSmallHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForMediumHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2800;
                    break;
                case 2:
                    emission = 3000;
                    break;
                case 3:
                case 4:
                    emission = 3380;
                    break;
                case 5:
                    emission = 3860;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for medium house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactMediumHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForLargeHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2800;
                    break;
                case 2:
                    emission = 2880;
                    break;
                case 3:
                case 4:
                    emission = 3000;
                    break;
                case 5:
                    emission = 3230;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for large house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactLargeHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateElectricityImpactSmallHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 200; // Natural Gas
                    case 2:
                        return 2100; // Electricity
                    case 3:
                        return 2870; // Oil
                    case 4:
                        return 2170; // Propane
                    case 5:
                        return 2440; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2440;
                    case 2:
                        return 400;
                    case 3:
                        return 5200;
                    case 4:
                        return 4400;
                    case 5:
                        return 2340;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for small house");
        }

        private double calculateElectricityImpactMediumHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 300; // Natural Gas
                    case 2:
                        return 3800; // Electricity
                    case 3:
                        return 3770; // Oil
                    case 4:
                        return 3670; // Propane
                    case 5:
                        return 5900; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 4470;
                    case 2:
                        return 800;
                    case 3:
                        return 5200;
                    case 4:
                        return 5800;
                    case 5:
                        return 4000;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for medium house");
        }

        private double calculateElectricityImpactLargeHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 320; // Natural Gas
                    case 2:
                        return 5400; // Electricity
                    case 3:
                        return 5570; // Oil
                    case 4:
                        return 4170; // Propane
                    case 5:
                        return 3000; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 5800;
                    case 2:
                        return 1200;
                    case 3:
                        return 11000;
                    case 4:
                        return 6340;
                    case 5:
                        return 4340;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for large house");
        }
    }
    public class SemiDetachedHouseCalculator {

        /**
         * 计算半独立屋的碳排放
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
                    baseEmission = calculateForSmallHouse(occupants, billCategory, heatingType);
                    break;
                case "1000-2000 sq. ft.":
                    baseEmission = calculateForMediumHouse(occupants, billCategory, heatingType);
                    break;
                case "Over 2000 sq. ft.":
                    baseEmission = calculateForLargeHouse(occupants, billCategory, heatingType);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown home size: " + homeSize);
            }

            return baseEmission;
        }

        private double calculateForSmallHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2160;
                    break;
                case 2:
                    emission = 2349;
                    break;
                case 3:
                case 4:
                    emission = 2732;
                    break;
                case 5:
                    emission = 3199;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for small house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactSmallHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForMediumHouse(int occupants, int billCategory, int heatingType) {
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2443;
                    break;
                case 2:
                    emission = 2727;
                    break;
                case 3:
                case 4:
                    emission = 3151;
                    break;
                case 5:
                    emission = 3578;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for medium house: " + occupants);
            }

            emission += calculateElectricityImpactMediumHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForLargeHouse(int occupants, int billCategory, int heatingType) {
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2821;
                    break;
                case 2:
                    emission = 3010;
                    break;
                case 3:
                case 4:
                    emission = 3261;
                    break;
                case 5:
                    emission = 3578;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for large house: " + occupants);
            }

            emission += calculateElectricityImpactLargeHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateElectricityImpactSmallHouse(int billCategory, int heatingType) {
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 300; // Natural Gas
                    case 2:
                        return 2500; // Electricity
                    case 3:
                        return 2200; // Oil
                    case 4:
                        return 2100; // Propane
                    case 5:
                        return 2800; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2600;
                    case 2:
                        return 410;
                    case 3:
                        return 2592;
                    case 4:
                        return 2349;
                    case 5:
                        return 2450;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for small house");
        }

        private double calculateElectricityImpactMediumHouse(int billCategory, int heatingType) {
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 400; // Natural Gas
                    case 2:
                        return 3400; // Electricity
                    case 3:
                        return 3000; // Oil
                    case 4:
                        return 2500; // Propane
                    case 5:
                        return 2000; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 4500;
                    case 2:
                        return 6200;
                    case 3:
                        return 7000;
                    case 4:
                        return 8000;
                    case 5:
                        return 10500;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for medium house");
        }

        private double calculateElectricityImpactLargeHouse(int billCategory, int heatingType) {
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 320; // Natural Gas
                    case 2:
                        return 4370; // Electricity
                    case 3:
                        return 3970; // Oil
                    case 4:
                        return 3000; // Propane
                    case 5:
                        return 4000; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 7500;
                    case 2:
                        return 10000;
                    case 3:
                        return 15000;
                    case 4:
                        return 17500;
                    case 5:
                        return 20000;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for large house");
        }

    }
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
                    baseEmission = calculateForSmallHouse(occupants, billCategory, heatingType);
                    break;
                case "1000-2000 sq. ft.":
                    baseEmission = calculateForMediumHouse(occupants, billCategory, heatingType);
                    break;
                case "Over 2000 sq. ft.":
                    baseEmission = calculateForLargeHouse(occupants, billCategory, heatingType);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown home size: " + homeSize);
            }

            return baseEmission;
        }

        private double calculateForSmallHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 1971;
                    break;
                case 2:
                    emission = 2160;
                    break;
                case 3:
                case 4:
                    emission = 2500;
                    break;
                case 5:
                    emission = 2800;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for small house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactSmallHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForMediumHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2443;
                    break;
                case 2:
                    emission = 2750;
                    break;
                case 3:
                case 4:
                    emission = 3111;
                    break;
                case 5:
                    emission = 3580;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for medium house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactMediumHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateForLargeHouse(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2822;
                    break;
                case 2:
                    emission = 3010;
                    break;
                case 3:
                case 4:
                    emission = 3300;
                    break;
                case 5:
                    emission = 3600;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for large house: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactLargeHouse(billCategory, heatingType);

            return emission;
        }

        private double calculateElectricityImpactSmallHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 300; // Natural Gas
                    case 2:
                        return 2500; // Electricity
                    case 3:
                        return 2200; // Oil
                    case 4:
                        return 1500; // Propane
                    case 5:
                        return 2000; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2800;
                    case 2:
                        return 550;
                    case 3:
                        return 2592;
                    case 4:
                        return 2450;
                    case 5:
                        return 2250;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for small house");
        }

        private double calculateElectricityImpactMediumHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 400; // Natural Gas
                    case 2:
                        return 3400; // Electricity
                    case 3:
                        return 3170; // Oil
                    case 4:
                        return 2400; // Propane
                    case 5:
                        return 2500; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 4500;
                    case 2:
                        return 6200;
                    case 3:
                        return 7000;
                    case 4:
                        return 8000;
                    case 5:
                        return 10000;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for medium house");
        }

        private double calculateElectricityImpactLargeHouse(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 500; // Natural Gas
                    case 2:
                        return 4000; // Electricity
                    case 3:
                        return 3400; // Oil
                    case 4:
                        return 3000; // Propane
                    case 5:
                        return 2800; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 9500;
                    case 2:
                        return 12000;
                    case 3:
                        return 15000;
                    case 4:
                        return 17500;
                    case 5:
                        return 20000;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large house");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for large house");
        }
    }
    public class CondoApartmentCalculator {

        /**
         * 计算公寓的碳排放
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
                    baseEmission = calculateForSmallCondo(occupants, billCategory, heatingType);
                    break;
                case "1000-2000 sq. ft.":
                    baseEmission = calculateForMediumCondo(occupants, billCategory, heatingType);
                    break;
                case "Over 2000 sq. ft.":
                    baseEmission = calculateForLargeCondo(occupants, billCategory, heatingType);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown home size: " + homeSize);
            }

            return baseEmission;
        }

        private double calculateForSmallCondo(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 1680;
                    break;
                case 2:
                    emission = 1870;
                    break;
                case 3:
                case 4:
                    emission = 2170;
                    break;
                case 5:
                    emission = 2440;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for small condo: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactSmallCondo(billCategory, heatingType);

            return emission;
        }

        private double calculateForMediumCondo(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2060;
                    break;
                case 2:
                    emission = 2260;
                    break;
                case 3:
                case 4:
                    emission = 2540;
                    break;
                case 5:
                    emission = 3010;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for medium condo: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactMediumCondo(billCategory, heatingType);

            return emission;
        }

        private double calculateForLargeCondo(int occupants, int billCategory, int heatingType) {
            // 基础排放值，按家庭成员数量分类
            double emission;
            switch (occupants) {
                case 1:
                    emission = 2440;
                    break;
                case 2:
                    emission = 2727;
                    break;
                case 3:
                case 4:
                    emission = 3010;
                    break;
                case 5:
                    emission = 3570;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid number of occupants for large condo: " + occupants);
            }

            // 电费影响
            emission += calculateElectricityImpactLargeCondo(billCategory, heatingType);

            return emission;
        }

        private double calculateElectricityImpactSmallCondo(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 200; // Natural Gas
                    case 2:
                        return 0; // Electricity
                    case 3:
                        return 1320; // Oil
                    case 4:
                        return 1600; // Propane
                    case 5:
                        return 1800; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small condo");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2240;
                    case 2:
                        return 500;
                    case 3:
                        return 0;
                    case 4:
                        return 2450;
                    case 5:
                        return 2200;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for small condo");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for small condo");
        }

        private double calculateElectricityImpactMediumCondo(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 300; // Natural Gas
                    case 2:
                        return 0; // Electricity
                    case 3:
                        return 1500; // Oil
                    case 4:
                        return 2200; // Propane
                    case 5:
                        return 2500; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium condo");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2500;
                    case 2:
                        return 550;
                    case 3:
                        return 0;
                    case 4:
                        return 3000;
                    case 5:
                        return 2700;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for medium condo");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for medium condo");
        }

        private double calculateElectricityImpactLargeCondo(int billCategory, int heatingType) {
            // 按电费类别和供暖类型调整碳排放
            if (billCategory == 1) {
                switch (heatingType) {
                    case 1:
                        return 350; // Natural Gas
                    case 2:
                        return 0; // Electricity
                    case 3:
                        return 1800; // Oil
                    case 4:
                        return 2300; // Propane
                    case 5:
                        return 2600; // Wood
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large condo");
                }
            } else if (billCategory == 2) {
                switch (heatingType) {
                    case 1:
                        return 2700;
                    case 2:
                        return 610;
                    case 3:
                        return 0;
                    case 4:
                        return 3300;
                    case 5:
                        return 3200;
                    default:
                        throw new IllegalArgumentException("Unknown heating type for large condo");
                }
            }
            throw new IllegalArgumentException("Unknown bill category for large condo");
        }
    }
}