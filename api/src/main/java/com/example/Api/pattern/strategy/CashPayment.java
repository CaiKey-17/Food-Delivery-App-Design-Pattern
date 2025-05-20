package com.example.Api.pattern.strategy;

public class CashPayment implements PaymentStrategy {
    @Override
    public String getPaymentMethod() {
        return "Tien mat";
    }

    @Override
    public double calculateFee(double amount) {
        return 0;
    }
}
