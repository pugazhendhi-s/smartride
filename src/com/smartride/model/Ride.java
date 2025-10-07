package com.smartride.model;
import java.time.LocalDateTime;

public class Ride {
    private final String rideID;
    private final Customer customer;
    private Driver driver;
    private Vehicle vehicle;
    private final Location pickupLocation;
    private final Location dropLocation;
    private LocalDateTime requestTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private RideStatus status;
    private double fare;
    private double distance;

    // Enum of ride status
    public enum RideStatus {
        REQUESTED, ACCEPTED, IN_PROGRESS, COMPLETED, CANCELLED
    }

    public Ride(String rideID, Customer customer, Location pickupLocation, Location dropLocation) {
        this.rideID = rideID;
        this.customer = customer;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.requestTime = LocalDateTime.now();
        this.status = RideStatus.REQUESTED;
        this.distance = pickupLocation.distanceTo(dropLocation);
    }
    // Getters
    public String getRideID() {
        return rideID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Location getPickupLocation() {
        return pickupLocation;
    }

    public Location getDropLocation() {
        return dropLocation;
    }

    public RideStatus getStatus() {
        return status;
    }

    public double getFare() {
        return fare;
    }

    public double getDistance() {
        return distance;
    }

    // Business methods
    public void assignDriver(Driver driver) {
        this.driver = driver;
        this.vehicle = driver.getAssignedVehicle();
        this.status = RideStatus.ACCEPTED;
        calculateFare();
    }

    public void startRide() {
        this.status = RideStatus.IN_PROGRESS;
        this.startTime = LocalDateTime.now();
    }

    public void completeRide() {
        this.status = RideStatus.COMPLETED;
        this.endTime = LocalDateTime.now();
        driver.incrementRides();
        customer.addToRideHistory(rideID);
    }

    public void cancelRide() {
        this.status = RideStatus.CANCELLED;
    }

    private void calculateFare() {
        if (vehicle != null) {
            double baseFare = vehicle.getBaseFare();
            double ratePerKm = vehicle.getRatePerKm();
            this.fare = baseFare + (distance * ratePerKm);
        }
    }

    @Override
    public String toString() {
        return "Ride{id='%s', customer='%s', status='%s', fare='%f'}".formatted(rideID, customer.getUserName(), status, fare);
    }
}
