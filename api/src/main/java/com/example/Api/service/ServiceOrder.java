package com.example.Api.service;


import com.example.Api.model.OrderDetail;
import com.example.Api.model.SellingOrder;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceOrder {

    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;


    public ServiceOrder() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    public List<SellingOrder> getAllOrderOfRestaurant(int id_restaurant) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process = 'Dang gui den nha hang' and Id_fk_restaurant = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_restaurant);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));
                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));
                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));
                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public List<SellingOrder> getAllOrderOfRestaurantHis(int id_restaurant) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process IN('Xong','Da huy','Da danh gia') and Id_fk_restaurant = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_restaurant);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));

                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));

                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }



    public List<SellingOrder> getAllOrderAcceptOfRestaurant(int id_restaurant) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process = 'Dang lam mon' and Id_fk_restaurant = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_restaurant);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));

                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));

                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));
                dish.setLatitude(resultSet.getDouble("latitude"));
                dish.setLongitude(resultSet.getDouble("longitude"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }



    public List<OrderDetail> listDetailOrder(String order_id) {
        List<OrderDetail> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT c.*,ds.Name,ds.image from selling_order d, order_detail c, dish ds WHERE d.Id = ? and d.Id = c.Id_fk_donhang and c.Id_fk_dish = ds.Id";
            statement = connection.prepareStatement(query);
            statement.setString(1, order_id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDetail dish = new OrderDetail();
                dish.setId(resultSet.getInt("Id"));
                dish.setPrice(resultSet.getDouble("Price"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setId_fk_donhang(resultSet.getString("Id_fk_donhang"));
                dish.setId_fk_dish(resultSet.getInt("Id_fk_dish"));
                dish.setImage(resultSet.getString("image"));
                dish.setName(resultSet.getString("Name"));
                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public List<SellingOrder> getAllOrderOfCustomer(int id_customer) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process IN('Dang gui den nha hang','Dang lam mon','Dang tim tai xe','Tai xe da nhan don') and Id_fk_customer = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));

                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));

                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));
                dish.setLatitude(resultSet.getDouble("latitude"));
                dish.setLongitude(resultSet.getDouble("longitude"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }


    public List<SellingOrder> getAllOrderOfCustomerDelivering(int id_customer) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process IN('Dang giao') and Id_fk_customer = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));

                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));

                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));
                dish.setLatitude(resultSet.getDouble("latitude"));
                dish.setLongitude(resultSet.getDouble("longitude"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public List<SellingOrder> getAllOrderOfCustomerHistory(int id_customer) {
        List<SellingOrder> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * from selling_order WHERE Process IN('Xong','Da huy','Da danh gia') and Id_fk_customer = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                SellingOrder dish = new SellingOrder();
                dish.setId(resultSet.getString("Id"));
                dish.setQuantity(resultSet.getInt("Quantity"));
                dish.setTotalFish(resultSet.getDouble("Total_dish"));
                dish.setDelivery_fee(resultSet.getDouble("Delivery_fee"));
                dish.setVoucherS(resultSet.getDouble("VoucherS"));
                dish.setVoucherR(resultSet.getDouble("VoucherR"));
                dish.setTotal(resultSet.getDouble("Total"));

                dish.setNote(resultSet.getString("Note"));
                dish.setProcess(resultSet.getString("Process"));
                dish.setSend_order(resultSet.getString("Send_order"));
                dish.setReceive_order(resultSet.getString("Receive_order"));

                dish.setId_fk_customer(resultSet.getInt("Id_fk_customer"));
                dish.setId_fk_shiper(resultSet.getInt("Id_fk_shiper"));
                dish.setId_fk_restaurant(resultSet.getInt("Id_fk_restaurant"));

                dish.setId_voucher_system(resultSet.getInt("Id_voucher_system"));
                dish.setId_voucher_restaurant(resultSet.getInt("Id_voucher_restaurant"));
                dish.setLatitude(resultSet.getDouble("latitude"));
                dish.setLongitude(resultSet.getDouble("longitude"));

                dishes.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public int ratingCus(String orderId,int id_fk_restaurant,double ratingRes,int id_fk_shiper,double ratingShiper) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL CustomerRating(?,?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, orderId);
            callableStatement.setInt(2, id_fk_restaurant);
            callableStatement.setDouble(3, ratingRes);
            callableStatement.setInt(4, id_fk_shiper);
            callableStatement.setDouble(5, ratingShiper);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
