package com.example.Api.pattern.strategy;

public class PaymentContext {
    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(double amount) {
        return strategy.calculateFee(amount);
    }
}
