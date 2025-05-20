package com.example.Api.service;

import com.example.Api.model.Dish;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceDish {

    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;


    public ServiceDish() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    public Dish addDish(String name, Double price, int quantity, String describe, int id_restaurant, String id_group, String image) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "INSERT INTO dish(Name, Price, Quantity, Describe_dish, Id_fk, Name_fk,image,time_up) VALUES (?, ?, ?, ?, ?, ?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);
            statement.setString(4, describe);
            statement.setInt(5, id_restaurant);
            statement.setString(6, id_group);
            statement.setString(7, image);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(8, currentTime);
            result = statement.executeUpdate();
            if (result > 0) {
                return new Dish(name, price, quantity, describe, id_restaurant, id_group, image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Dish> listAllDishByRestaurant(int id_fk) {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish WHERE Id_fk = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_fk);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dish.setTime_up(resultSet.getTimestamp("time_up"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dishes;
    }

    public List<Dish> listAllDishByGroupDish(String name_fk) {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish WHERE Name_fk = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, name_fk);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public List<Dish> listAllDishByGroupDishSearch(String name_fk, String name) {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish WHERE Name_fk = ? And Name like ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, name_fk);
            statement.setString(2, "%" + name + "%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public List<Dish> listDish() {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public List<Dish> listAllDishLimit10() {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish ORDER BY time_up DESC LIMIT 10";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public List<Dish> searchDishByName(String name) {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish WHERE Name LIKE ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public Dish detail(int id) {
        Dish dish = null;
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM dish WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dish;
    }


    public int updateDish(int id, String name, Double price, int quantity, String describe, int id_restaurant, String id_group, String image) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE dish SET Name = ?, Price = ?, Quantity = ?, Describe_dish = ?, Id_fk = ?, Name_fk = ?, image = ? WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);
            statement.setString(4, describe);
            statement.setInt(5, id_restaurant);
            statement.setString(6, id_group);
            statement.setString(7, image);
            statement.setInt(8, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateDish1(int id, String name, Double price, int quantity, String describe, int id_restaurant, String id_group) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE dish SET Name = ?, Price = ?, Quantity = ?, Describe_dish = ?, Id_fk = ?, Name_fk = ? WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setDouble(2, price);
            statement.setInt(3, quantity);
            statement.setString(4, describe);
            statement.setInt(5, id_restaurant);
            statement.setString(6, id_group);
            statement.setInt(7, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getIdRestaurant(int id) {
        try {
            connection = dbConnector.connect();
            String query = "SELECT Id_fk FROM dish WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                return resultSet.getInt("Id_fk");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Dish> listDishGoiY() {
        List<Dish> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT d.*,r.Rating FROM dish d, restaurant r WHERE d.Id_fk = r.Id and r.Rating > 3 ORDER BY r.Rating DESC LIMIT 10";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("Id"));
                dish.setName(resultSet.getString("Name"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setDescribe_dish(resultSet.getString("Describe_dish"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk"));
                dish.setId_fk_group_dish(resultSet.getString("Name_fk"));
                dish.setImage(resultSet.getString("image"));
                dish.setRating(resultSet.getDouble("Rating"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

}
