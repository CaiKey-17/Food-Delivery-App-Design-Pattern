package com.example.Api.pattern.strategy;

public class DefaultPayment implements PaymentStrategy {
    @Override
    public String getPaymentMethod() {
        return "Mac dinh";
    }

    @Override
    public double calculateFee(double amount) {
        return 0;
    }
}
