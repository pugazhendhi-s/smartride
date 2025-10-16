package com.smartride.payment;

import com.smartride.util.*;

public class UpiPayment implements Payment {
    private final String upiID;
    private final String userName;
    private String lastTransactionID;
    private boolean isVerified;

    public UpiPayment(String upiID, String userName) {
        this.upiID = upiID;
        this.userName = userName;
        this.lastTransactionID = "";
        this.isVerified = validateUpiID();
    }

    @Override
    public boolean processPayment(double amount) {
        if (!isVerified) {
            LoggerUtil.debug("Invalid UPI ID. Payment failed.");
            return false;
        }
        LoggerUtil.debug("Sending payment request to UPI ID: " + upiID);
        LoggerUtil.debug("Amount: Rs " + amount);

        lastTransactionID = IDGenerator.generateTransactionID(getPaymentMethod());
        LoggerUtil.info("UPI payment successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "UPI";
    }

    @Override
    public boolean isAvailable() {
        return isVerified;
    }

    @Override
    public String getTransactionDetails() {
        return "Transaction ID: '%s', UPI ID: '%s', User: '%s'".formatted(lastTransactionID, upiID, userName);
    }

    // UPI specific methods
    private boolean validateUpiID() {
        if (upiID == null || upiID.isEmpty()) {
            return false;
        }
        String[] parts = upiID.split("@");
        if (parts.length != 2) {
            return false;
        }
        return !parts[0].isEmpty() && !parts[1].isEmpty();
    }

    public String getUpiID() {
        return upiID;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isVerified() {
        return isVerified;
    }

    @Override
    public String toString() {
        return "UPI{id='%s', user='%s', verified=%b}".formatted(upiID, userName, isVerified);
    }
}
