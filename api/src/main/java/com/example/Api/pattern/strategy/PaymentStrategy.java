package com.example.Api.pattern.strategy;

public interface PaymentStrategy {
    String getPaymentMethod();
    double calculateFee(double amount);
}
