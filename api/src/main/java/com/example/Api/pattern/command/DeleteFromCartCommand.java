package com.example.Api.pattern.command;

import com.example.Api.service.ServiceCart;

public class DeleteFromCartCommand implements ICommand {
    private int id;
    private ServiceCart serviceCart;

    public DeleteFromCartCommand(ServiceCart serviceCart, int id) {
        this.serviceCart = serviceCart;
        this.id = id;
    }

    @Override
    public int execute() {
        return serviceCart.deleteToCart(id);
    }
}

