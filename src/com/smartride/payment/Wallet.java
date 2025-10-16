package com.smartride.payment;

import com.smartride.exception.InsufficientBalanceException;
import com.smartride.util.*;

public class Wallet implements Payment{
    private final String walletID;
    private double balance;
    private String lastTransactionID;

    public Wallet(String walletID, double initialBalance) {
        this.walletID = walletID;
        this.balance = initialBalance;
        this.lastTransactionID = "";
    }

    // Implementing Payment interface methods
    @Override
    public String getPaymentMethod() {
        return "Wallet";
    }

    @Override
    public boolean processPayment(double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException(
                    "Insufficient wallet balance",
                    amount,      // required
                    balance,     // available
                    "Wallet"     // payment method
            );
        }

        this.balance -= amount;
        lastTransactionID = IDGenerator.generateTransactionID(getPaymentMethod());
        LoggerUtil.info("Wallet payment successful: Rs " + amount);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return balance > 0;
    }

    @Override
    public String getTransactionDetails() {
        return "Transaction ID: '%s', Remaining Balance: Rs %.2f".formatted(lastTransactionID, balance);
    }

    // Wallet-specific methods
    public void addMoney(double amount) {
        this.balance += amount;
        LoggerUtil.info("Added Rs " + amount + " to wallet. New balance: Rs " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public String getWalletID() {
        return walletID;
    }

    @Override
    public String toString() {
        return "Wallet{id='%s', balance=Rs %.2f}".formatted(walletID, balance);
    }
}
