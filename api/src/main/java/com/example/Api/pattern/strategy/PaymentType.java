package com.example.Api.pattern.strategy;

public enum PaymentType {
    CASH(1, "Tien mat"),
    BANKING(2, "Chuyen khoan");

    private final int code;
    private final String description;

    PaymentType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentType fromCode(int code) {
        for (PaymentType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid payment type code: " + code);
    }
} 