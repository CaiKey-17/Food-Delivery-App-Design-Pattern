package com.example.Api.service;

import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.example.Api.model.VoucherRestaurant;
import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceVoucherRestaurant {

    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    PreparedStatement updateStatement = null;

    PreparedStatement preparedStatement = null;


    public ServiceVoucherRestaurant() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    public VoucherRestaurant addVoucher(String name, int quantity, Double price, LocalDateTime expiry, int id_fk_restaurant) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "INSERT INTO voucher_restaurant(Name,Quantity,Price,Expiry,Id_fk,Active) VALUES (?,?,?,?,?,0)";
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setInt(2, quantity);
            statement.setDouble(3, price);
            statement.setTimestamp(4, Timestamp.valueOf(expiry));
            statement.setInt(5, id_fk_restaurant);
            result = statement.executeUpdate();

            if (result > 0) {
                return new VoucherRestaurant(name, quantity, price, expiry, id_fk_restaurant, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int active(int id) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE voucher_restaurant SET Active = 1 WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, id);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<VoucherRestaurant> listVoucherRequest(int id_fk) {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_restaurant WHERE Id_fk = ? and Active = 0";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_fk);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));
                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }

    public List<VoucherRestaurant> listVoucherRequestAll() {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_restaurant WHERE Active = 0";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));
                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }


    public List<VoucherRestaurant> listVoucher(int id_fk) {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_restaurant WHERE Id_fk = ? and Active = 1";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_fk);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }

    public int deleteVoucher(int id) {
        try {
            connection = dbConnector.connect();
            String query = "DELETE FROM voucher_restaurant WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public List<VoucherRestaurant> listVoucherRestaurantOfCustomer(int id) {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.* from get_voucher_restaurant g, voucher_restaurant r WHERE g.Id_customer = ? and g.Id_voucher_restaurant = r.Id";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }

    public List<VoucherRestaurant> listVoucherRestaurantOfCustomerInCart(int id_cus, int id_res) {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT DISTINCT g.*,r.* from get_voucher_restaurant g, voucher_restaurant r, selling_order d WHERE d.Id_fk_customer = ? and g.Id_customer = d.Id_fk_customer and g.Id_voucher_restaurant = r.Id and r.Id_fk = ? and g.Quantity > 0";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_cus);
            statement.setInt(2, id_res);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }


    public List<VoucherRestaurant> listVoucherRestaurantAdd() {
        List<VoucherRestaurant> voucherRestaurants = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM voucher_restaurant WHERE Active = 1 AND Expiry > NOW() And Quantity > 0;";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VoucherRestaurant voucherRestaurant = new VoucherRestaurant();
                voucherRestaurant.setId(resultSet.getInt("Id"));
                voucherRestaurant.setName(resultSet.getString("Name"));
                voucherRestaurant.setPrice(resultSet.getDouble("Price"));
                voucherRestaurant.setQuantity(resultSet.getInt("Quantity"));
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDateTime = expiryTimestamp.toLocalDateTime();
                    voucherRestaurant.setExpiry(expiryDateTime);
                }

                voucherRestaurant.setId_restaurant(resultSet.getInt("Id_fk"));
                voucherRestaurant.setActive(resultSet.getInt("Active"));

                voucherRestaurants.add(voucherRestaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voucherRestaurants;
    }

    public int getVoucher(int id_cus, int id_voucher) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL GetVoucherRes(?,?)}";
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
            String checkQuery = "SELECT COUNT(*) FROM get_voucher_restaurant WHERE Id_customer = ? AND Id_voucher_restaurant = ?";
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
            String checkExpiryQuery = "SELECT Expiry FROM voucher_restaurant WHERE Id = ?";
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


    public boolean checkVoucherQuantity(int id_customer, int id_voucher) {
        try {
            connection = dbConnector.connect();
            String checkQuantityQuery = "SELECT Quantity FROM get_voucher_restaurant WHERE Id_customer = ? and Id_voucher_restaurant = ?";
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
            String checkExpiryQuery = "SELECT Expiry FROM voucher_restaurant WHERE Id = ?";
            preparedStatement = connection.prepareStatement(checkExpiryQuery);
            preparedStatement.setInt(1, id_voucher);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Timestamp expiryTimestamp = resultSet.getTimestamp("Expiry");
                if (expiryTimestamp != null) {
                    LocalDateTime expiryDate = expiryTimestamp.toLocalDateTime();
                    LocalDateTime currentDate = LocalDateTime.now();

                    // Nếu voucher đã hết hạn
                    if (!expiryDate.isAfter(currentDate)) {
                        return 1;
                    }
                }
            }

            // Kiểm tra số lượng còn lại
            String checkQuantityQuery = "SELECT Quantity FROM get_voucher_restaurant WHERE Id_customer = ? and Id_voucher_restaurant = ?";
            preparedStatement = connection.prepareStatement(checkQuantityQuery);
            preparedStatement.setInt(1, id_customer);
            preparedStatement.setInt(2, id_voucher);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int quantity = resultSet.getInt("Quantity");

                // Nếu số lượng voucher đã hết
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
