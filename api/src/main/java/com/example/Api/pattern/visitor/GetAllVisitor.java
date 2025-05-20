package com.example.Api.pattern.visitor;

import com.example.Api.inheritance.IVisitor;
import com.example.Api.model.Restaurant;
import com.example.Api.model.Shipper;
import com.example.Api.model.User;
import com.example.Api.pattern.template.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllVisitor implements IVisitor {
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;

    public GetAllVisitor(DatabaseConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public List<User> visitUser() {
        List<User> customers = new ArrayList<>();
        String query = "SELECT r.*, n.image, n.Address FROM customer r, user n WHERE r.id = n.Id";

        try {
            connection = dbConnector.connect();
            PreparedStatement statement = connection.prepareStatement(query);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getInt("Id"));
                    user.setImage(resultSet.getString("image"));
                    user.setAddress(resultSet.getString("Address"));
                    customers.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<Shipper> visitShipper() {
        List<Shipper> shippers = new ArrayList<>();
        String query = "SELECT r.*, n.image, n.FullName, n.Address FROM shiper r, user n WHERE r.id = n.Id AND status_request = 0 or status_request = 1";

        try {
            connection = dbConnector.connect();
            PreparedStatement statement = connection.prepareStatement(query);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Shipper shipper = new Shipper();
                    shipper.setId(resultSet.getInt("Id"));
                    shipper.setName(resultSet.getString("FullName"));
                    shipper.setImage(resultSet.getString("image"));
                    shipper.setAddress(resultSet.getString("Address"));
                    shipper.setStatus_request(resultSet.getInt("status_request"));
                    shippers.add(shipper);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippers;
    }

    @Override
    public List<Restaurant> visitRestaurant() {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.*, n.image,n.Address FROM restaurant r, user n where r.Id = n.Id;";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("Id"));
                restaurant.setName(resultSet.getString("Name"));
                restaurant.setBalance(resultSet.getDouble("Balance"));
                restaurant.setWorking(resultSet.getInt("Working"));
                restaurant.setRating(resultSet.getDouble("Rating"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setAddress(resultSet.getString("Address"));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }
}
