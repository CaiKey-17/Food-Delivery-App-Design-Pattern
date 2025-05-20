package com.example.Api.pattern.proxy;

import com.example.Api.inheritance.IServiceUser;
import com.example.Api.service.ServiceUser;
import com.example.Api.inheritance.IUser;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class ServiceUserProxy implements IServiceUser {
    private final ServiceUser realServiceUser;
    private static final Logger logger = Logger.getLogger(ServiceUserProxy.class.getName());

    public ServiceUserProxy(ServiceUser realServiceUser) {
        this.realServiceUser = realServiceUser;
    }

    @Override
    public int changePasswordReal(int id, String newPassword) {
        logger.info("User with ID " + id + " is attempting to change their password.");
        int result = realServiceUser.changePasswordReal(id, newPassword);
        if (result == 1) {
            logger.info("User with ID " + id + " successfully changed their password.");
        } else {
            logger.warning("Failed to change password for user ID " + id);
        }
        return result;
    }

    @Override
    public int register(IUser user) {
        logger.info("User registration attempted for phone number: " + user.getPhoneNumber());
        int result = realServiceUser.register(user);
        if (result == 1) {
            logger.info("User registered successfully: " + user.getPhoneNumber());
        } else {
            logger.warning("User registration failed for: " + user.getPhoneNumber());
        }
        return result;
    }

    @Override
    public int login(String phone, String password) {
        logger.info("Login attempt for phone number: " + phone);
        int result = realServiceUser.login(phone, password);
        if (result == 1) {
            logger.info("User logged in successfully: " + phone);
        } else {
            logger.warning("Login failed for phone number: " + phone);
        }
        return result;
    }

    @Override
    public boolean checkPassword(int id_cus, String password) {
        logger.info("Checking password for user ID: " + id_cus);
        return realServiceUser.checkPassword(id_cus, password);
    }

    @Override
    public int verifyOTP(String phone, String otp) {
        logger.info("Verifying OTP for phone number: " + phone);
        return realServiceUser.verifyOTP(phone, otp);
    }

    @Override
    public int resendOTP(String phone, String newOtp) {
        logger.info("Resending OTP for phone number: " + phone);
        return realServiceUser.resendOTP(phone, newOtp);
    }

    @Override
    public String getUserNameByPhone(String phone) {
        logger.info("Fetching username for phone number: " + phone);
        return realServiceUser.getUserNameByPhone(phone);
    }

    @Override
    public String getGenderByPhone(String phone) {
        logger.info("Fetching gender for phone number: " + phone);
        return realServiceUser.getGenderByPhone(phone);
    }

    @Override
    public int getUserRoleByPhone(String phone) {
        logger.info("Fetching user role for phone number: " + phone);
        return realServiceUser.getUserRoleByPhone(phone);
    }

    @Override
    public int getUserIdByPhone(String phone) {
        logger.info("Fetching user ID for phone number: " + phone);
        return realServiceUser.getUserIdByPhone(phone);
    }

    @Override
    public Map<String, Object> getUserByPhone(String phone) {
        logger.info("Fetching user details for phone number: " + phone);
        return realServiceUser.getUserByPhone(phone);
    }

    @Override
    public int loginEmail(String email, String fullname) {
        logger.info("Login attempt via email: " + email);
        return realServiceUser.loginEmail(email, fullname);
    }

    @Override
    public int findUserByEmail(String email) {
        logger.info("Checking if user exists with email: " + email);
        return realServiceUser.findUserByEmail(email);
    }

    @Override
    public int findUserByPhone(String phone) {
        logger.info("Checking if user exists with phone: " + phone);
        return realServiceUser.findUserByPhone(phone);
    }

    @Override
    public int changePassword(String phone, String password) {
        logger.info("Changing password for phone number: " + phone);
        return realServiceUser.changePassword(phone, password);
    }

    @Override
    public int getIdByEmail(String email) {
        logger.info("Fetching user ID for email: " + email);
        return realServiceUser.getIdByEmail(email);
    }
}
