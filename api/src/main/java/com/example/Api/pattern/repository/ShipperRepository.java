package com.example.Api.pattern.repository;

import com.example.Api.pattern.repository.IShipperRepository;
import com.example.Api.model.Shipper;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Repository
public class ShipperRepository implements IShipperRepository {

    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;


    public ShipperRepository() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    @Override
    public int acceptShiper(int id) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE shiper SET status_request = 1 WHERE Id = ?";
            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Shipper> listAllShiperRequest() {
        return getShipersByStatus(0);
    }

    @Override
    public List<Shipper> listAllShiper() {
        return getShipersByStatus(1);
    }

    private List<Shipper> getShipersByStatus(int status) {
        List<Shipper> shippers = new ArrayList<>();
        String query = "SELECT r.*, n.image, n.FullName, n.Address FROM shiper r, user n WHERE r.id = n.Id AND status_request = ?";

        try {
            connection = dbConnector.connect();
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, status);
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
}

