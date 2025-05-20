package com.example.Api.pattern.factory;

import com.example.Api.pattern.template.DatabaseConnector;

import java.sql.*;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

public class RestaurantHandler implements UserRoleHandler {
    private final DatabaseConnector dbConnector;
    Connection connection = null;


    public RestaurantHandler() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database,username ,password
        );
    }
    @Override
    public void createSubTable(int userId) {
        try {
            connection = dbConnector.connect();
            String storedProc = "{CALL createRestaurant(?)}";
            CallableStatement callableStatement = connection.prepareCall(storedProc);
            callableStatement.setInt(1, userId);

            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

