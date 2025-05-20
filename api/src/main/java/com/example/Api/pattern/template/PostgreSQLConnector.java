package com.example.Api.pattern.template;

public class PostgreSQLConnector extends DatabaseConnector {
    public PostgreSQLConnector(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    protected void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy driver PostgreSQL!", e);
        }
    }
}