package com.example.Api.pattern.factory;

import com.example.Api.pattern.template.DatabaseConnector;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

public class CustomerHandler implements UserRoleHandler {
    private final DatabaseConnector dbConnector;
    Connection connection = null;


    public CustomerHandler() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database,username ,password
        );
    }
    @Override
    public void createSubTable(int userId) {
        try {
            Connection connection = dbConnector.connect();
            String storedProc = "{CALL createCustomer(?)}";
            CallableStatement callableStatement = connection.prepareCall(storedProc);
            callableStatement.setInt(1, userId);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
