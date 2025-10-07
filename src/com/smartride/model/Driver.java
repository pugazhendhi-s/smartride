package com.smartride.model;

public class Driver extends User {
    private final String licenseNumber;
    private Vehicle assignedVehicle;
    private boolean isOnDuty;
    private double rating;
    private int totalRides;

    public Driver(String userID, String userName, String phoneNumber, String email, String licenseNumber) {
        super(userID, userName, phoneNumber, email);
        this.licenseNumber = licenseNumber;
        this.isOnDuty = false;
        this.rating = 5.0;
        this.totalRides = 0;
    }

    @Override
    public String getUserType() {
        return "Driver";
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }
    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public boolean isOnDuty() {
        return isOnDuty;
    }
    public void setIsOnDuty(boolean isOnDuty) {
        this.isOnDuty = isOnDuty;
    }

    public double getRating() {
        return rating;
    }
    public void updateRating(double newRating) {
        this.rating = (this.rating * this.totalRides + newRating) / (totalRides + 1);
    }

    public int getTotalRides() {
        return totalRides;
    }
    public void incrementRides() {
        this.totalRides++;
    }
}
