package com.example.Api.service;

import com.example.Api.inheritance.IServiceUser;
import com.example.Api.inheritance.IUser;
import com.example.Api.pattern.factory.UserRoleFactory;
import com.example.Api.pattern.factory.UserRoleHandler;
import com.example.Api.model.User;
import com.example.Api.pattern.template.DatabaseConnector;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.Api.global.Connection.*;
import static com.example.Api.global.Connection.password;

@Service
public class ServiceUser implements IServiceUser {
    private final UserRoleFactory factory = new UserRoleFactory();
    private final DatabaseConnector dbConnector;
    Connection connection = null;
    PreparedStatement selectStatement = null;

    PreparedStatement loginStatement = null;

    PreparedStatement statement = null;
    ResultSet resultSet = null;
    CallableStatement callableStatement = null;
    PreparedStatement updateStatement = null;

    PreparedStatement preparedStatement = null;
    PreparedStatement checkStatement = null;


    public ServiceUser() {
        this.dbConnector = DatabaseConnector.getInstance(
                type_connection, database, username, password
        );
    }

    public boolean checkPassword(int id_cus, String password) {
        try {
            connection = dbConnector.connect();
            String checkQuery = "SELECT COUNT(*) FROM user WHERE Id = ? AND PassW = ?";
            preparedStatement = connection.prepareStatement(checkQuery);
            preparedStatement.setInt(1, id_cus);
            preparedStatement.setString(2, password);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int changePasswordReal(int id, String newPassword) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET PassW = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newPassword);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int register(IUser user) {
        try {
            connection = dbConnector.connect();
            String checkQuery = "SELECT COUNT(*) FROM user WHERE PhoneNumber = ? and Active = 0";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user.getPhoneNumber());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {

                String updateQuery = "UPDATE user SET FullName = ?,PassW = ?, Otp = ?, Id_role = ?  WHERE PhoneNumber = ? and Active = 0";
                updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, user.getFullName());
                updateStatement.setString(2, user.getPassword());
                updateStatement.setString(3, user.getOtp());
                updateStatement.setInt(4, user.getId_fk_role());
                updateStatement.setString(5, user.getPhoneNumber());

                updateStatement.executeUpdate();
            } else {
                String storedProc = "{CALL Signup(?, ?, ?, ?, ?, ?)}";
                callableStatement = connection.prepareCall(storedProc);
                callableStatement.setString(1, user.getFullName());
                callableStatement.setString(2, user.getPhoneNumber());
                callableStatement.setString(3, user.getUsername());
                callableStatement.setString(4, user.getPassword());
                callableStatement.setInt(5, user.getId_fk_role());
                callableStatement.setString(6, user.getOtp());
                callableStatement.executeUpdate();
            }

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public UserRoleHandler createHandler(int role) {
        return factory.getHandler(role);
    }

    public int verifyOTP(String phone, String otp) {
        try {
            connection = dbConnector.connect();
            String checkQuery = "SELECT COUNT(*) FROM user WHERE PhoneNumber = ? AND Otp = ?";
            checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, phone);
            checkStatement.setString(2, otp);

            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                String updateQuery = "UPDATE user SET Active = 1 WHERE PhoneNumber = ? AND Otp = ?";
                updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, phone);
                updateStatement.setString(2, otp);

                updateStatement.executeUpdate();

                int id_user = getUserIdByPhone(phone);
                int role_user = getUserRoleByPhone(phone);

                // Áp dụng Factory Pattern
                UserRoleHandler handler = createHandler(role_user);
                handler.createSubTable(id_user);

                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int resendOTP(String phone, String newOtp) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Otp = ? WHERE PhoneNumber = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newOtp);
            updateStatement.setString(2, phone);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int login(String phone, String password) {
        try {
            connection = dbConnector.connect();
            String loginQuery = "SELECT COUNT(*) FROM user WHERE PhoneNumber = ? AND PassW = ? AND Active = 1";
            loginStatement = connection.prepareStatement(loginQuery);
            loginStatement.setString(1, phone);
            loginStatement.setString(2, password);

            resultSet = loginStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public String getUserNameByPhone(String phone) {
        String userName = null;
        try {
            connection = dbConnector.connect();
            String query = "SELECT FullName FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userName = resultSet.getString("FullName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }

    public String getGenderByPhone(String phone) {
        String gender = null;
        try {
            connection = dbConnector.connect();
            String query = "SELECT Gender FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                gender = resultSet.getString("Gender");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gender;
    }


    public int getUserRoleByPhone(String phone) {
        int role = 4;
        try {
            connection = dbConnector.connect();
            String query = "SELECT Id_role FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getInt("Id_role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }


    public int getUserIdByPhone(String phone) {
        int id = 4;
        try {
            connection = dbConnector.connect();
            String query = "SELECT Id FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public Map<String, Object> getUserByPhone(String phone) {
        Map<String, Object> userData = new HashMap<>();
        try {
            connection = dbConnector.connect();
            String query = "SELECT Id, FullName, PhoneNumber, Gender, Id_role, Address, Birthday, image,email FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userData.put("id", resultSet.getInt("Id"));
                userData.put("name", getSafeString(resultSet, "FullName"));
                userData.put("phone", getSafeString(resultSet, "PhoneNumber"));
                userData.put("gender", getSafeString(resultSet, "Gender"));
                userData.put("id_fk_role", getSafeString(resultSet, "Id_role"));
                userData.put("address", getSafeString(resultSet, "Address"));
                userData.put("birthday", getSafeString(resultSet, "Birthday"));
                userData.put("image", getSafeString(resultSet, "image"));
                userData.put("email", getSafeString(resultSet, "email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userData.isEmpty() ? null : userData;
    }

    private String getSafeString(ResultSet resultSet, String columnLabel) throws SQLException {
        String value = resultSet.getString(columnLabel);
        return (value != null) ? value : "";
    }


    public int loginEmail(String email, String fullname) {
        try {
            connection = dbConnector.connect();
            String query = "{CALL LoginByEmail(?,?, 4)}";
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, email);
            callableStatement.setString(2, fullname);

            callableStatement.execute();

            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int findUserByEmail(String email) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "SELECT COUNT(*) FROM user WHERE Email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);
            resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int findUserByPhone(String phone) {
        int result = 0;
        try {
            connection = dbConnector.connect();
            String query = "SELECT COUNT(*) FROM user WHERE PhoneNumber = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, phone);

            resultSet = statement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                result = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int changePassword(String phone, String password) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET PassW = ? WHERE PhoneNumber = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, password);
            updateStatement.setString(2, phone);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getIdByEmail(String email) {
        int userId = -1;
        try {
            connection = dbConnector.connect();
            String query = "SELECT id FROM user WHERE Email = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, email);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }


    public User getInforById(int id) {
        User user = new User();
        try {
            connection = dbConnector.connect();
            String query = "SELECT * FROM user WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setFullName(resultSet.getString("FullName"));
                user.setGender(resultSet.getString("Gender"));
                user.setBirthday(resultSet.getDate("Birthday"));
                user.setAddress(resultSet.getString("Address"));
                user.setEmail(resultSet.getString("Email"));
                user.setImage(resultSet.getString("image"));
                user.setPhoneNumber(resultSet.getString("PhoneNumber"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public int changePhoneNumber(int id, String newPhone) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET PhoneNumber = ? WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, newPhone);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int changeName(int id, String name) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET FullName = ? WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, name);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int changeEmail(int id, String email) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Email = ? WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, email);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int change(int id, String gender, String image) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Gender = ? , image = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, gender);
            updateStatement.setString(2, image);
            updateStatement.setInt(3, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int updateImage(int id, String image) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET image = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, image);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updatePosition(int id, double latitude, double longtitude) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Latitude = ?, Longitude = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setDouble(1, latitude);
            updateStatement.setDouble(2, longtitude);
            updateStatement.setInt(3, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int updateAddress(int id, String address) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Address = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, address);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int changeTemp(int id, String gender) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE user SET Gender = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, gender);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double[] getCoordinates(String orderId) {
        double[] coordinates = new double[2];
        try {
            connection = dbConnector.connect();
            String selectQuery = "SELECT d.Latitude,d.Longitude from selling_order d,user n WHERE d.Id_fk_restaurant = n.Id and d.Id = ?";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, orderId);

            resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                coordinates[0] = resultSet.getDouble("Latitude");
                coordinates[1] = resultSet.getDouble("Longitude");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coordinates;
    }


    public String[] getInforOrderCus(String orderId) {
        String[] infors = new String[6];
        try {
            connection = dbConnector.connect();
            String selectQuery = "select d.Id,d.Total,b.Active, n.Address,n.FullName,n.PhoneNumber from selling_order d, user n, bill b where d.Id = ? and d.Id_fk_customer = n.Id and d.Id = b.Id_fk_donhang";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, orderId);

            resultSet = selectStatement.executeQuery();


            if (resultSet.next()) {
                infors[0] = resultSet.getString("Id");

                BigDecimal number = new BigDecimal(resultSet.getString("Total"));


                String total = number.toPlainString();

                infors[1] = total;
                infors[2] = resultSet.getString("Active");

                if (resultSet.getString("Address") != null) {

                    infors[3] = resultSet.getString("Address");
                } else {
                    infors[3] = "";
                }

                infors[4] = resultSet.getString("FullName");
                infors[5] = resultSet.getString("PhoneNumber");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infors;
    }

    public String[] getInforOrder(String orderId) {
        String[] infors = new String[5];
        try {
            connection = dbConnector.connect();
            String selectQuery = "select d.Id,d.Total,b.Active, n.Address,r.Name from selling_order d, user n,restaurant r ,bill b where d.Id = ? and d.Id_fk_restaurant = n.Id and n.Id = r.Id and d.Id = b.Id_fk_donhang";
            selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, orderId);

            resultSet = selectStatement.executeQuery();


            if (resultSet.next()) {
                infors[0] = resultSet.getString("Id");

                BigDecimal number = new BigDecimal(resultSet.getString("Total"));


                String total = number.toPlainString();

                infors[1] = total;
                infors[2] = resultSet.getString("Active");

                if (resultSet.getString("Address") != null) {

                    infors[3] = resultSet.getString("Address");
                } else {
                    infors[3] = "";
                }

                infors[4] = resultSet.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infors;
    }


    public String[] getMoney(int id) {
        String[] infors = new String[2];
        try {
            connection = dbConnector.connect();
            String query = "SELECT Balance,Rating FROM shiper WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                infors[0] = resultSet.getString("Balance");
                infors[1] = resultSet.getString("Rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infors;
    }


    public String getNameRes(int id) {
        String result = "";
        try {
            connection = dbConnector.connect();
            String query = "SELECT Name FROM restaurant WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public Double sodu(int id) {
        System.out.println(id);
        Double t = 0.0;
        try {
            connection = dbConnector.connect();
            String query = "SELECT Balance FROM shiper WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                t = resultSet.getDouble("Balance");
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public Double tonghoadon(String orderID) {
        Double t = 0.0;
        try {
            connection = dbConnector.connect();
            String query = "select Total from selling_order WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, orderID);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                t = resultSet.getDouble("Total");
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return t;
    }

    public int checkStatus(int id) {
        int s = 0;
        try {
            connection = dbConnector.connect();
            String query = "SELECT status_request FROM shiper WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                s = resultSet.getInt("status_request");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }


    public int checkPhoneAndAddress(int id) {
        String address, phoneNumber;
        try {
            connection = dbConnector.connect();
            String query = "SELECT Address, PhoneNumber FROM user WHERE Id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                address = resultSet.getString("Address");
                phoneNumber = resultSet.getString("PhoneNumber");

                if (address == null || phoneNumber == null) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int checkNameAndAddress(int id) {
        String address, phoneNumber;
        try {
            connection = dbConnector.connect();
            String query = "SELECT n.Address, r.Name FROM user n,restaurant r WHERE n.Id = r.Id and r.Id =  ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                address = resultSet.getString("Address");
                phoneNumber = resultSet.getString("Name");

                if (address == null || phoneNumber == null) {
                    return 0;
                } else {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }


    public int updateNameRes(int id, String name) {
        try {
            connection = dbConnector.connect();
            String updateQuery = "UPDATE restaurant SET Name = ?  WHERE Id = ?";
            updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, name);
            updateStatement.setInt(2, id);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
