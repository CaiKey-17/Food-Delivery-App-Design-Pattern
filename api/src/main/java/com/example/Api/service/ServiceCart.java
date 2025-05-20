package com.example.Api.service;
import com.example.Api.model.OrderDetail;
import com.example.Api.model.Restaurant;
import com.example.Api.pattern.strategy.*;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceCart {
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    CallableStatement callableStatement = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    public ServiceCart() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database,username ,password
        );
    }

    public int addToCart(int id_customer,int id_restaurant,int id_dish,int quantity) {
        try {
            // Sử dụng tại đây
            connection = dbConnector.connect();
            String query = "{CALL AddToCart(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id_customer);
            callableStatement.setInt(2, id_restaurant);
            callableStatement.setInt(3, id_dish);
            callableStatement.setInt(4, quantity);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int addMoreToCart(String id_order,int id_dish) {
        try {
            // Sử dụng tại đây
            connection = dbConnector.connect();
            String query = "{CALL AddMoreToCart(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, id_order);
            callableStatement.setInt(2, id_dish);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int MinusToCart(String id_order,int id_dish) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL MinusToCart(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, id_order);
            callableStatement.setInt(2, id_dish);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteToCart(int id) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL DeleteToCart(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setInt(1, id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public List<OrderDetail> listDishInCart(int id_customer) {
        List<OrderDetail> dishes = new ArrayList<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT dh.id as id_hd,c.*,d.image,d.Name from order_detail c,dish d, selling_order dh where c.Id_fk_dish = d.Id and c.Id_fk_donhang = dh.Id and dh.Process = 'Gio hang' and dh.Id_fk_customer = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_customer);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                OrderDetail dish = new OrderDetail();
                dish.setHd(resultSet.getString("id_hd"));
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

    public Restaurant getNameRestaurantByOder(int id) {
        try {
            connection = dbConnector.connect();
            String query = "SELECT r.Name,r.Id from selling_order d,restaurant r WHERE d.Id_fk_restaurant = r.Id and d.Process = 'Gio hang' and d.Id_fk_customer = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Restaurant(resultSet.getString("Name"),resultSet.getInt("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int applyVoucherRes(String order_id,int voucher_id) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL AddVoucherRestaurantToCart(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.setInt(2, voucher_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int applyVoucherSys(String order_id,int voucher_id) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL AddVoucherSystemToCart(?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.setInt(2, voucher_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String getOrderId(int id_fk_customer) {
        String dish = "";
        try {
            connection = dbConnector.connect();
            String query = "select Id from selling_order where Id_fk_customer = ? and Process = 'Gio hang'";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id_fk_customer);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = (resultSet.getString("Id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dish;
    }


    public Double sumVoucher(String order_id) {
        Double dish = 0.0;
        try {
            connection = dbConnector.connect();
            String query = "{CALL SumVoucherDiscount(?)}";
            statement = connection.prepareStatement(query);
            statement.setString(1, order_id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                dish = (resultSet.getDouble("Tong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dish;
    }


    public int confirm(String order_id, int payment, double latitude, double longitude) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL Confirm(?,?,?,?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);

            //Khởi tạo mặc định
            PaymentContext context = new PaymentContext(new DefaultPayment());

            // Khởi tạo đối tượng tương ứng (  new BankingPayment() ;  new CashPayment()  )
            PaymentStrategy paymentStrategy = getPaymentStrategy(payment);

            // Thay đổi đối tượng cần thực thi
            context.setStrategy(paymentStrategy);

            if (paymentStrategy == null) {
                throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
            }

            String method = paymentStrategy.getPaymentMethod();
            callableStatement.setString(2, method);
            callableStatement.setDouble(3, latitude);
            callableStatement.setDouble(4, longitude);
            callableStatement.execute();

            getBillTotal(order_id, context);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private PaymentStrategy getPaymentStrategy(int payment) {
        switch (payment) {
            case 1:
                return new BankingPayment();
            case 2:
                return new CashPayment();
            default:
                return null;
        }
    }
    
    public int cancel(String order_id) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL Cancel(?)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int accept(String order_id) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE selling_order SET Process = 'Dang lam mon' WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int done(String order_id) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE selling_order SET Process = 'Dang tim tai xe' WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delivering(String order_id) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE selling_order SET Process = 'Dang giao' WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int ok(String order_id,int shiper) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE selling_order SET Process = 'Tai xe da nhan don',Id_fk_shiper = ? WHERE Id = ? ";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(2, order_id);
            callableStatement.setInt(1, shiper);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int hoantat(String order_id,int shiper,double money) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE selling_order SET Process = 'Xong' WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, order_id);
            callableStatement.execute();
            cong(shiper,money);
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int cong(int shiper,double money) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE shiper SET Balance = COALESCE(Balance, 0) + ? WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setDouble(1, money);
            callableStatement.setInt(2, shiper);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int naptien(int id,double tien) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE shiper SET Balance = Balance + ? WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setDouble(1, tien);
            callableStatement.setInt(2, id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int trutien(int id,double tien) {
        try {
            connection = dbConnector.connect();
            String query = "UPDATE shiper SET Balance = Balance - ? WHERE Id = ?";
            callableStatement = connection.prepareCall(query);
            callableStatement.setDouble(1, tien);
            callableStatement.setInt(2, id);
            callableStatement.execute();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getBillTotal(String order_id, PaymentContext paymentContext) {
        double total = 0;
        Connection connection = null;
        PreparedStatement selectStatement = null;
        PreparedStatement updateStatement = null;
        ResultSet resultSet = null;

        try {
            connection = dbConnector.connect();
            
            String selectQuery = "SELECT total FROM bill WHERE Id_fk_donhang = ?";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, order_id);
            resultSet = selectStatement.executeQuery();
            
            if (resultSet.next()) {
                total = resultSet.getDouble("total");
                
                double updatedTotal = total + paymentContext.executeStrategy(total);
                
                String updateQuery = "UPDATE bill SET total = ? WHERE Id_fk_donhang = ?";
                updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setDouble(1, updatedTotal);
                updateStatement.setString(2, order_id);
                updateStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (updateStatement != null) updateStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }
}