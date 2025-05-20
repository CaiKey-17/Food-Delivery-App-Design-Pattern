package com.example.Api.inheritance;

import java.util.Map;

public interface IServiceUser {
    public int changePasswordReal(int id, String newPassword);
    public int register(IUser user);
    public int login(String phone, String password);
    public boolean checkPassword(int id_cus, String password);
    public int verifyOTP(String phone, String otp);
    public int resendOTP(String phone, String newOtp);
    public String getUserNameByPhone(String phone);
    public String getGenderByPhone(String phone);
    public int getUserRoleByPhone(String phone);
    public int getUserIdByPhone(String phone);
    public Map<String, Object> getUserByPhone(String phone);
    public int loginEmail(String email, String fullname);
    public int findUserByEmail(String email);
    public int findUserByPhone(String phone);
    public int changePassword(String phone, String password);
    public int getIdByEmail(String email);
}
