package com.example.Api.pattern.factory;

public class UserRoleFactory {
    public UserRoleFactory() {}
    public UserRoleHandler getHandler(int role) {
        switch (role) {
            case 4:
                return new CustomerHandler();
            case 3:
                return new ShipperHandler();
            case 2:
                return new RestaurantHandler();
            case 1:
                return new AdminHandler();
            default:
                throw new IllegalArgumentException("Vai trò không hợp lệ.");
        }
    }
}

