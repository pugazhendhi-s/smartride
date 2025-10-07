package com.smartride.model;

public class Bike extends Vehicle {
    private final boolean hasHelmet;

    public Bike(String vehicleID, String licensePlate, String model, double ratePerKm, boolean hasHelmet) {
        super(vehicleID, licensePlate, model, ratePerKm);
        this.hasHelmet = hasHelmet;
    }

    public boolean hasHelmet() {
        return hasHelmet;
    }

    @Override
    public double getBaseFare() {
        return 50;
    }

    @Override
    public int getCapacity() {
        return 1;
    }

    @Override
    public String getVehicleType() {
        return "Bike";
    }
}
