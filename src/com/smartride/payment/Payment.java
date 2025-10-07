package com.smartride.payment;

public interface Payment {
    /**
     * Process a payment for the given amount
     * @param amount Amount to be paid
     * @return true if payment successful, false otherwise
     */
    boolean processPayment(double amount);

    /**
     * Get the payment method name
     * @return Name of the payment method (e.g., "Wallet", "UPI")
     */
    String getPaymentMethod();

    /**
     * Check if the payment method is available/valid
     * @return true if the payment can be user, false otherwise
     */
    boolean isAvailable();

    /**
     * Get transaction details after payment
     * @return Transaction ID or confirmation message
     */
    String getTransactionDetails();

}
