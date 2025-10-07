package com.smartride.payment;

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
    public boolean processPayment(double amount) {
        if (balance < amount) {
            System.out.println("Insufficient Wallet balance. Available: Rs " + balance);
            return false;
        }

        this.balance -= amount;
        lastTransactionID = "WALLET_TXN_" + System.currentTimeMillis();
        System.out.println("Wallet payment successful: Rs " + amount);
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
        System.out.println("Added Rs " + amount + " to wallet. New balance: Rs " + balance);
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
