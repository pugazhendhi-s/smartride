package com.smartride.payment;

import com.smartride.exception.InsufficientBalanceException;
import com.smartride.util.LoggerUtil;

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
            LoggerUtil.debug("Added payment method: " + payment.getPaymentMethod());

            // Set first valid payment as preferred
            if (preferredPayment == null) {
                this.preferredPayment = payment;
            }
        }
        else {
            LoggerUtil.debug("Cannot add invalid payment method");
        }
    }

    /**
     * Set the preferred payment method
     */
    public void setPreferredPayment(Payment payment) {
        if (availablePaymentMethods.contains(payment)) {
            this.preferredPayment = payment;
            LoggerUtil.debug("Preferred payment set to: " + payment.getPaymentMethod());
        }
        else {
            LoggerUtil.debug("Preferred payment not available");
        }
    }

    /**
     * Process payment using preferred method, with automatic fallback
     * @return true if the transaction success(fallback), false otherwise
     */
    public boolean processPayment(double amount) throws InsufficientBalanceException{
        if (availablePaymentMethods.isEmpty()) {
            LoggerUtil.debug("No payment methods available");
            return false;
        }

        InsufficientBalanceException lastException = null;

        // try the preferred payment
        if (preferredPayment != null && preferredPayment.isAvailable()) {
            try {
                LoggerUtil.debug("\n--- Attempting payment with " + preferredPayment.getPaymentMethod() + " ---");
                boolean success = preferredPayment.processPayment(amount);
                if (success) {
                    LoggerUtil.info("Payment successful: " + preferredPayment.getPaymentMethod());
                    lastTransactionDetails = preferredPayment.getTransactionDetails();
                    return true;
                }
            } catch (InsufficientBalanceException e) {
                LoggerUtil.warning(preferredPayment.getPaymentMethod() + " failed: " + e.getMessage());
                lastException = e; // Keep track
            }
        }
        LoggerUtil.info("Trying alternative payment methods...");

        for (Payment payment : availablePaymentMethods) {
            if (payment != preferredPayment && payment.isAvailable()) {
                try {
                    LoggerUtil.debug("\n--- Attempting payment with " + payment.getPaymentMethod() + " ---");
                    boolean success = payment.processPayment(amount);
                    if (success) {
                        LoggerUtil.info("Payment successful: " + payment.getPaymentMethod());
                        lastTransactionDetails = payment.getTransactionDetails();
                        return true;
                    }
                } catch (InsufficientBalanceException e) {
                    LoggerUtil.warning(payment.getPaymentMethod() + " failed: " + e.getMessage());
                    lastException = e;
                }
            }
        }
        LoggerUtil.error("All payment methods failed for amount: Rs " + amount);
        if (lastException != null) {
            throw lastException;
        }
        else {
            throw new InsufficientBalanceException(
                    "Payment processing failed",
                    amount,
                    0.0,
                    "Unknown"
            );
        }
    }

    /**
     * Process payment with specific method
     */
    public boolean processPaymentWith(String paymentMethodName, double amount) throws InsufficientBalanceException{
        for (Payment payment : availablePaymentMethods) {
            if (payment.getPaymentMethod().equalsIgnoreCase(paymentMethodName)) {
                try{
                    LoggerUtil.debug("\n--- Processing payment with " + payment.getPaymentMethod() + " ---");
                    boolean success = payment.processPayment(amount);
                    if (success) {
                        lastTransactionDetails = payment.getTransactionDetails();
                    }
                    return success;
                } catch (InsufficientBalanceException e) {
                    LoggerUtil.warning(payment.getPaymentMethod() + " failed: " + e.getMessage());
                    throw new InsufficientBalanceException(e.getMessage());
                }
            }
        }
        LoggerUtil.debug("Payment method '" + paymentMethodName + "' not found");
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
        LoggerUtil.debug("\n=== Available Payment Methods ===");
        if (availablePaymentMethods.isEmpty()) {
            LoggerUtil.debug("No payment methods configured");
            return;
        }
        for (int i = 0; i < availablePaymentMethods.size(); i++) {
            Payment p = availablePaymentMethods.get(i);
            String preferred = (p == preferredPayment) ? " [PREFERRED]" : "";
            String toDisplay = "%d. %s - Available: %b%s".formatted(i + 1, p.getPaymentMethod(), p.isAvailable(), preferred);
            LoggerUtil.debug(toDisplay);
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
            LoggerUtil.debug("Removed payment method: " + payment.getPaymentMethod());

            // if removed preferred, set new preferred
            if (payment == preferredPayment && !availablePaymentMethods.isEmpty()) {
                this.preferredPayment = availablePaymentMethods.get(0);
                LoggerUtil.debug("New preferred payment: " + preferredPayment.getPaymentMethod());
            }
        }
    }
}
