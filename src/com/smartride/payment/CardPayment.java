package com.smartride.payment;

public class CardPayment implements Payment{
    private final String cardNumber;
    private final String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String cardType; // Debit or Credit
    private String lastTransactionID;
    private boolean isValid;

    public CardPayment(String cardNumber, String cardHolderName, String expiryDate, String cvv, String cardType) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardType = cardType;
        this.lastTransactionID = "";
        this.isValid = validateCard();
    }

    @Override
    public boolean processPayment(double amount) {
        if (!isValid) {
            System.out.println("Invalid card details. Payment failed.");
            return false;
        }
        // Simulate card processing
        System.out.println("Processing " + cardType + " card payment of Rs " + amount);

        // Simulate bank verification (always succeeds in our demo)
        lastTransactionID = "CARD_TXN_" + System.currentTimeMillis();
        System.out.println("Card payment successful!");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return cardType + " Card";
    }

    @Override
    public boolean isAvailable() {
        return isValid;
    }

    @Override
    public String getTransactionDetails() {
        return String.format("Transaction ID: '%s', Card: '%s', Type: '%s'", lastTransactionID, cardNumber, cardType);
    }

    // Card-specific methods
    private boolean validateCard() {
        // Simple validation - card number should have 16 digits.
        String cleanNumber = cardNumber.replaceAll("[^0-9]", "");
        if (cleanNumber.length() != 16) {
            return false;
        }

        // Check expiry date format
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }

        // cvv should be 3 digits
        return cvv.length() == 3;
    }

    private String maskCardNumber(String cardNumber) {
        String clean = cardNumber.replaceAll("[^0-9]", "");
        if (clean.length() == 16) {
            return cardNumber.substring(0, 4) + " **** **** " + cardNumber.substring(12);
        }
        return cardNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    @Override
    public String toString() {
        return "'%s' Card{holder='%s', number='%s'}".formatted(cardType, cardHolderName, cardNumber);
    }
}
