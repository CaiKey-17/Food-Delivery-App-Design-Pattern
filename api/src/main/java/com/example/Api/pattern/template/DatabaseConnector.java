package com.example.Api.pattern.template;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseConnector {
    protected String url;
    protected String username;
    protected String password;

    private static DatabaseConnector instance;

    protected DatabaseConnector(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static synchronized DatabaseConnector getInstance(String dbType, String url, String username, String password) {
        if (instance == null) {
            switch (dbType.toLowerCase()) {
                case "mysql":
                    instance = new MySQLConnector(url, username, password);
                    break;
                case "postgresql":
                    instance = new PostgreSQLConnector(url, username, password);
                    break;
                default:
                    throw new IllegalArgumentException("Database không được hỗ trợ: " + dbType);
            }
        }

        return instance;
    }

    public final Connection connect() {
        loadDriver();
        return createConnection();
    }

    protected abstract void loadDriver();

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Kết nối database thất bại!", e);
        }
    }
}
