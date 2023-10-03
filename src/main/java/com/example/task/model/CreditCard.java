package com.example.task.model;

import java.time.format.DateTimeFormatter;

public class CreditCard {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    public String getCardNumber() {
        return cardNumber;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public String getCvv() {
        return cvv;
    }
}