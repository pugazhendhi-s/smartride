package com.smartride.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class PaymentManager {
    private final List<Payment> availablePaymentMethods;
    private Payment preferredPayment;
    private String lastTransactionDetails;

    public PaymentManager() {
        this.availablePaymentMethods = new ArrayList<>();
        this.lastTransactionDetails = "";
    }

    /**
     * Add a payment method to available options
     */
    public void addPaymentMethod(Payment payment) {
        if (payment != null && payment.isAvailable()) {
            this.availablePaymentMethods.add(payment);
            System.out.println("Added payment method: " + payment.getPaymentMethod());

            // Set first valid payment as preferred
            if (preferredPayment == null) {
                this.preferredPayment = payment;
            }
        }
        else {
            System.out.println("Cannot add invalid payment method");
        }
    }

    /**
     * Set the preferred payment method
     */
    public void setPreferredPayment(Payment payment) {
        if (availablePaymentMethods.contains(payment)) {
            this.preferredPayment = payment;
            System.out.println("Preferred payment set to: " + payment.getPaymentMethod());
        }
        else {
            System.out.println("Preferred payment not available");
        }
    }

    /**
     * Process payment using preferred method, with automatic fallback
     * @return true if the transaction success(fallback), false otherwise
     */
    public boolean processPayment(double amount) {
        if (availablePaymentMethods.isEmpty()) {
            System.out.println("No payment methods available");
            return false;
        }

        // try the preferred payment
        if (preferredPayment != null && preferredPayment.isAvailable()) {
            System.out.println("\n--- Attempting payment with " + preferredPayment.getPaymentMethod() + " ---");
            boolean success = preferredPayment.processPayment(amount);
            if (success) {
                lastTransactionDetails = preferredPayment.getTransactionDetails();
                return true;
            }
        }

        // if preferred fails, try other payment methods
        for (Payment payment : availablePaymentMethods) {
            if (payment != preferredPayment && payment.isAvailable()) {
                System.out.println("\n--- Attempting payment with " + payment.getPaymentMethod() + " ---");
                boolean success = payment.processPayment(amount);
                if (success) {
                    lastTransactionDetails = payment.getTransactionDetails();
                    return true;
                }
            }
        }
        System.out.println("\nAll payment methods failed");
        return false;
    }

    /**
     * Process payment with specific method
     */
    public boolean processPaymentWith(String paymentMethodName, double amount) {
        for (Payment payment : availablePaymentMethods) {
            if (payment.getPaymentMethod().equalsIgnoreCase(paymentMethodName)) {
                System.out.println("\n--- Processing payment with " + payment.getPaymentMethod() + " ---");
                boolean success = payment.processPayment(amount);
                if (success) {
                    lastTransactionDetails = payment.getTransactionDetails();
                }
                return success;
            }
        }
        System.out.println("Payment method '" + paymentMethodName + "' not found");
        return false;
    }

    /**
     * Get all available payment methods
     */
    public List<Payment> getAvailablePaymentMethods() {
        return new ArrayList<>(availablePaymentMethods);
    }

    /**
     * Display all payment methods
     */
    public void displayPaymentMethods() {
        System.out.println("\n=== Available Payment Methods ===");
        if (availablePaymentMethods.isEmpty()) {
            System.out.println("No payment methods configured");
            return;
        }
        for (int i = 0; i < availablePaymentMethods.size(); i++) {
            Payment p = availablePaymentMethods.get(i);
            String preferred = (p == preferredPayment) ? " [PREFERRED]" : "";
            String toDisplay = "%d. %s - Available: %b%s".formatted(i + 1, p.getPaymentMethod(), p.isAvailable(), preferred);
            System.out.println(toDisplay);
        }
    }

    /**
     * Get the lastTransactionDetails
     */
    public String getLastTransactionDetails() {
        return lastTransactionDetails;
    }

    /**
     * Remove a payment method
     */
    public void removePaymentMethod(Payment payment) {
        if (availablePaymentMethods.remove(payment)) {
            System.out.println("Removed payment method: " + payment.getPaymentMethod());

            // if removed preferred, set new preferred
            if (payment == preferredPayment && !availablePaymentMethods.isEmpty()) {
                this.preferredPayment = availablePaymentMethods.get(0);
                System.out.println("New preferred payment: " + preferredPayment.getPaymentMethod());
            }
        }
    }
}
