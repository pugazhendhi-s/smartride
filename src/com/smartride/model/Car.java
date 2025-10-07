package com.smartride.model;

public class Car extends Vehicle {
    private final boolean hasAC;
    private final String carType;

    public Car(String vehicleID, String licensePlate, String model, double ratePerKm, boolean hasAC, String carType) {
        super(vehicleID, licensePlate, model, ratePerKm);
        this.hasAC = hasAC;
        this.carType = carType;
    }

    public boolean hasAC() {
        return hasAC;
    }

    public String getCarType() {
        return carType;
    }

    @Override
    public double getBaseFare() {
        return hasAC ? 170 : 100;
    }

    @Override
    public int getCapacity() {
        return 4;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }

}
