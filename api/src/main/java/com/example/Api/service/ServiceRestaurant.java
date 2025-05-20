package com.example.Api.service;

import com.example.Api.inheritance.IVisitor;
import com.example.Api.model.Restaurant;
import com.example.Api.pattern.template.DatabaseConnector;
import com.example.Api.pattern.visitor.GetAllVisitor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceRestaurant {
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    private final IVisitor visitor;


    public ServiceRestaurant() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
        this.visitor = new GetAllVisitor(dbConnector);
    }

    public List<Restaurant> listAllRestaurant(int id_customer) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.*, n.image, n.Latitude, n.Longitude, (6371 * 2 * ATAN2(SQRT(SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2)), SQRT(1 - (SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2))))) AS distance FROM restaurant r JOIN user n ON r.Id = n.Id JOIN user c ON c.Id = ? WHERE (6371 * 2 * ATAN2(SQRT(SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2)), SQRT(1 - (SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2))))) < 10";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("Id"));
                restaurant.setName(resultSet.getString("Name"));
                restaurant.setBalance(resultSet.getDouble("Balance"));
                restaurant.setWorking(resultSet.getInt("Working"));
                restaurant.setRating(resultSet.getDouble("Rating"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setLatitude(resultSet.getDouble("Latitude"));
                restaurant.setLongitude(resultSet.getDouble("Longitude"));
                restaurant.setDistance(resultSet.getDouble("distance"));
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public List<Restaurant> listAllRestaurant1(int id_customer) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.*, n.image, n.Latitude, n.Longitude, (6371 * 2 * ATAN2(SQRT(SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2)), SQRT(1 - (SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2))))) AS distance FROM restaurant r JOIN user n ON r.Id = n.Id JOIN user c ON c.Id = ? ";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("Id"));
                restaurant.setName(resultSet.getString("Name"));
                restaurant.setBalance(resultSet.getDouble("Balance"));
                restaurant.setWorking(resultSet.getInt("Working"));
                restaurant.setRating(resultSet.getDouble("Rating"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setLatitude(resultSet.getDouble("Latitude"));
                restaurant.setLongitude(resultSet.getDouble("Longitude"));
                restaurant.setDistance(resultSet.getDouble("distance"));

                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public List<Restaurant> listAllRestaurantAdmin() {
        return visitor.visitRestaurant();
    }


    public List<Restaurant> listAllSearch(int id_customer, String name) {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.*, n.image, n.Latitude, n.Longitude, (6371 * 2 * ATAN2(SQRT(SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2)), SQRT(1 - (SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2))))) AS distance FROM restaurant r JOIN user n ON r.Id = n.Id JOIN user c ON c.Id = ? and r.Name LIKE ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            statement.setString(2, "%" + name + "%");

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setId(resultSet.getInt("Id"));
                restaurant.setName(resultSet.getString("Name"));
                restaurant.setBalance(resultSet.getDouble("Balance"));
                restaurant.setWorking(resultSet.getInt("Working"));
                restaurant.setRating(resultSet.getDouble("Rating"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setLatitude(resultSet.getDouble("Latitude"));
                restaurant.setLongitude(resultSet.getDouble("Longitude"));
                restaurant.setDistance(resultSet.getDouble("distance"));

                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public Restaurant detailOfRestaurant(int id_cus, int id_res) {
        Restaurant restaurant = new Restaurant();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.*, n.image, n.Latitude, n.Longitude, (6371 * 2 * ATAN2(SQRT(SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2)), SQRT(1 - (SIN(RADIANS(n.Latitude - c.Latitude) / 2) * SIN(RADIANS(n.Latitude - c.Latitude) / 2) + COS(RADIANS(c.Latitude)) * COS(RADIANS(n.Latitude)) * SIN(RADIANS(n.Longitude - c.Longitude) / 2) * SIN(RADIANS(n.Longitude - c.Longitude) / 2))))) AS distance FROM restaurant r JOIN user n ON r.Id = n.Id JOIN user c ON c.Id = ?  WHERE r.Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_cus);
            statement.setInt(2, id_res);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                restaurant.setId(resultSet.getInt("Id"));
                restaurant.setName(resultSet.getString("Name"));
                restaurant.setBalance(resultSet.getDouble("Balance"));
                restaurant.setWorking(resultSet.getInt("Working"));
                restaurant.setRating(resultSet.getDouble("Rating"));
                restaurant.setImage(resultSet.getString("image"));
                restaurant.setLatitude(resultSet.getDouble("Latitude"));
                restaurant.setLongitude(resultSet.getDouble("Longitude"));
                restaurant.setDistance(resultSet.getDouble("distance"));
            }
            return restaurant;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;

    }

}
