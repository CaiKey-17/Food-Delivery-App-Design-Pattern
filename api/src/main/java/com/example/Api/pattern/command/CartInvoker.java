package com.example.Api.pattern.command;

import java.util.ArrayList;
import java.util.List;

public class CartInvoker {
    private List<ICommand> commandHistory = new ArrayList<>();

    public int executeCommand(ICommand command) {
        commandHistory.add(command);
        return command.execute();
    }
}

