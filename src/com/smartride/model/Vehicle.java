package com.smartride.model;

abstract public class Vehicle {
    protected String vehicleID;
    protected String licensePlate;
    protected String model;
    protected Location currentLocation;
    protected boolean isAvailable;
    private final double ratePerKm;

    public Vehicle(String vehicleID, String licensePlate, String model, double ratePerKm) {
        this.vehicleID = vehicleID;
        this.licensePlate = licensePlate;
        this.model = model;
        this.isAvailable = true;
        this.ratePerKm = ratePerKm;
    }

    // Concrete methods (all vehicle have this)
    public String getVehicleID() {
        return vehicleID;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location location) {
        this.currentLocation = location;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public double getRatePerKm() {
        return ratePerKm;
    }

    // Abstract methods (each vehicle implements differently)
    public abstract double getBaseFare();
    public abstract int getCapacity();
    public abstract String getVehicleType();

    @Override
    public String toString() {
        return "'%s' {id='%s', plate='%s', available='%s'}".formatted(getVehicleType(), vehicleID, licensePlate, isAvailable);
    }
}
