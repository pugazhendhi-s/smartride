package com.smartride.model;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private final List<String> rideHistory;
    private double walletBalance;

    public Customer(String userID, String userName, String phoneNumber, String email) {
        super(userID, userName, phoneNumber, email);
        this.rideHistory = new ArrayList<>();
        this.walletBalance = 0.0;
    }

    @Override
    public String getUserType() {
        return "Customer";
    }

    public List<String> getRideHistory() {
        return rideHistory;
    }

    public void addToRideHistory(String rideID) {
        this.rideHistory.add(rideID);
    }

    public double getWalletBalance() {
        return walletBalance;
    }

    public void addMoney(double amount) {
        this.walletBalance += amount;
    }

    public boolean deductMoney(double amount) {
        if (walletBalance >= amount) {
            this.walletBalance -= amount;
            return true;
        }
        return false;
    }
}
