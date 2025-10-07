package com.smartride.model;

public class Auto extends Vehicle {
    private final boolean hasMeter;

    public Auto(String vehicleID, String licensePlate, String model, double ratePerKm, boolean hasMeter) {
        super(vehicleID, licensePlate, model, ratePerKm);
        this.hasMeter = hasMeter;
    }

    public boolean hasMeter() {
        return hasMeter;
    }

    @Override
    public double getBaseFare() {
        return 75;
    }

    @Override
    public int getCapacity() {
        return 3;
    }

    @Override
    public String getVehicleType() {
        return "Auto";
    }
}
