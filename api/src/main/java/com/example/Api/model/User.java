package com.example.Api.model;

import com.example.Api.inheritance.IUser;
import com.example.Api.inheritance.IVisitor;

import java.util.Date;

public class User extends IUser {
    public User() {
        super();
    }

    public User(String fullName, String phoneNumber, String username, String password, int id_fk_role) {
        super(fullName, phoneNumber, username, password, id_fk_role);
    }

    public User(String fullName, String phoneNumber, String username, String password, int id_fk_role, String otp) {
        super(fullName, phoneNumber, username, password, id_fk_role, otp);
    }
    public User(int id, String fullName, String gender, Date birthday, String address, String phoneNumber, String email, String username, String password, String otp, int active, int id_fk_role) {
        super(id, fullName, gender, birthday, address, phoneNumber, email, username, password, otp, active, id_fk_role);
    }

    public void accept(IVisitor visitor) {
        visitor.visitUser();
    }
}
