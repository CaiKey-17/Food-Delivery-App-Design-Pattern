package com.example.Api.pattern.template;

public class MySQLConnector extends DatabaseConnector {
    public MySQLConnector(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    protected void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Không tìm thấy driver MySQL!", e);
        }
    }
}

