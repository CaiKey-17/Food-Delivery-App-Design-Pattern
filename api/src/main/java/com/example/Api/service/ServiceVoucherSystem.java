package com.example.Api.service;

import com.example.Api.model.VoucherSystem;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;


@Service
public class ServiceVoucherSystem {
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    PreparedStatement updateStatement = null;

    PreparedStatement preparedStatement = null;


    public ServiceVoucherSystem() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    public VoucherSystem addVoucher(String name, int quantity, Double price, LocalDateTime expiry, int id_fk) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "INSERT INTO voucher_system(Name,Quantity,Price,Expiry,Id_fk) VALUES (?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, quantity);
            statement.setDouble(3, price);
            statement.setTimestamp(4, Timestamp.valueOf(expiry));
            statement.setInt(5,id_fk );

            result = statement.executeUpdate();

            if (result > 0) {
                return new VoucherSystem(name,quantity,price,expiry,id_fk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<VoucherSystem> listVoucherSystemOfCustomer(int id) {
        List<VoucherSystem> voucherSystems = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.* from get_voucher_system g, voucher_system r WHERE g.Id_customer = ? and g.Id_voucher_system = r.Id";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VoucherSystem voucherSystem = new VoucherSystem();
                voucherSystem.setId(resultSet.getInt("Id"));
                voucherSystem.setName(resultSet.getString("Name"));
                voucherSystem.setPrice(resultSet.getDouble("Price"));
                voucherSystem.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherSystem.setExpiry(expiryDateTime);
                }

                voucherSystem.setId_system(resultSet.getInt("Id_fk"));
                voucherSystems.add(voucherSystem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherSystems;
    }

    public List<VoucherSystem> listVoucherSystemOfCustomer1(int id) {
        List<VoucherSystem> voucherSystems = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.* FROM get_voucher_system g JOIN voucher_system r ON g.Id_voucher_system = r.Id WHERE g.Id_customer = ? AND g.Quantity > 0 AND DATEDIFF(r.Expiry, NOW()) > 0";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherSystem voucherSystem = new VoucherSystem();
                voucherSystem.setId(resultSet.getInt("Id"));
                voucherSystem.setName(resultSet.getString("Name"));
                voucherSystem.setPrice(resultSet.getDouble("Price"));
                voucherSystem.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherSystem.setExpiry(expiryDateTime);
                }

                voucherSystem.setId_system(resultSet.getInt("Id_fk"));
                voucherSystems.add(voucherSystem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherSystems;
    }


    public List<VoucherSystem> listVoucherSystemAdd() {
        List<VoucherSystem> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_system WHERE Expiry > NOW() And Quantity > 0";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherSystem voucherRestaurant = new VoucherSystem();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_system(resultSet.getInt("Id_fk"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }


    public List<VoucherSystem> listVoucherSystemManager() {
        List<VoucherSystem> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_system";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherSystem voucherRestaurant = new VoucherSystem();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_system(resultSet.getInt("Id_fk"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }


    public int getVoucher(int id_cus,int id_voucher) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL GetVoucherSys(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id_cus);
            callableStatement.setInt(2, id_voucher);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkVoucherExists(int id_cus, int id_voucher) {
        try {
            connection = dbConnector.connect();
            String checkQuery = "SELECT COUNT(*) FROM get_voucher_system WHERE Id_customer = ? AND Id_voucher_system = ?";
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setInt(1, id_cus);
            preparedStatement.setInt(2, id_voucher);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean checkVoucherExpiry(int id_voucher) {
        try {
            connection = dbConnector.connect();
            String checkExpiryQuery = "SELECT Expiry FROM voucher_system WHERE Id = ?";
            preparedStatement = connection.prepareStatement(checkExpiryQuery);
            preparedStatement.setInt(1, id_voucher);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDate = expiryTimestamp.toLocalDateTime();
                    LocalDateTime currentDate = LocalDateTime.now();

                    return expiryDate.isAfter(currentDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkVoucherQuantity(int id_customer,int id_voucher) {
        try {
            connection = dbConnector.connect();
            String checkQuantityQuery = "SELECT Quantity FROM get_voucher_system WHERE Id_customer = ? and Id_voucher_system = ?";
            preparedStatement = connection.prepareStatement(checkQuantityQuery);
            preparedStatement.setInt(1, id_customer);
            preparedStatement.setInt(2, id_voucher);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("Quantity");
                return quantity > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int checkVoucherStatusAndQuantity(int id_customer, int id_voucher) {
        try {
            connection = dbConnector.connect();
            String checkExpiryQuery = "SELECT Expiry FROM voucher_system WHERE Id = ?";
            preparedStatement = connection.prepareStatement(checkExpiryQuery);
            preparedStatement.setInt(1, id_voucher);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDate = expiryTimestamp.toLocalDateTime();
                    LocalDateTime currentDate = LocalDateTime.now();

                    if (!expiryDate.isAfter(currentDate)) {
                        return 1;
                    }
                }
            }

            String checkQuantityQuery = "SELECT Quantity FROM get_voucher_system WHERE Id_customer = ? and Id_voucher_system = ?";
            preparedStatement = connection.prepareStatement(checkQuantityQuery);
            preparedStatement.setInt(1, id_customer);
            preparedStatement.setInt(2, id_voucher);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("Quantity");

                if (quantity <= 0) {
                    return 2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 3;
    }


}
