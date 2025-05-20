package com.example.Api.pattern.strategy;

public class BankingPayment implements PaymentStrategy {
    @Override
    public String getPaymentMethod() {
        return "Chuyen khoan";
    }

    @Override
    public double calculateFee(double amount) {
        return amount * 0.01;
    }
}
