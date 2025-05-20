package com.example.Api.service;

import com.example.Api.model.GroupDish;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.example.Api.global.Connection.*;

@Service
public class ServiceGroupDish {
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;


    public ServiceGroupDish() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database,username ,password
        );
    }

    public int addGroupDish(String name, String image) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "INSERT INTO group_dish(Name,image) VALUES (?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, image);
            result = statement.executeUpdate();
            if (result > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public List<GroupDish> getAllGroupDishes() {
        List<GroupDish> groupDishList = new ArrayList<>();
        try {
            connection =dbConnector.connect();
            String query = "SELECT Name,image FROM group_dish";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String image = resultSet.getString("image");
                GroupDish groupDish = new GroupDish(name,image);
                groupDishList.add(groupDish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return groupDishList;
    }
}
