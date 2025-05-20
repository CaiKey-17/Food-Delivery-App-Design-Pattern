package com.example.Api.controller;

import com.example.Api.inheritance.IUser;
import com.example.Api.model.User;
import com.example.Api.pattern.proxy.ServiceUserProxy;
import com.example.Api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/register")
public class ControllerRegister {
    @Autowired
    ServiceUserProxy serviceUserProxy;

    public ControllerRegister(ServiceUser serviceUser) {
        this.serviceUserProxy = new ServiceUserProxy(serviceUser);
    }

    @GetMapping("/checkPassword")
    public Boolean checkPassword(@RequestParam("id_customer") int id_customer, @RequestParam("password") String password
    ) {
        return serviceUserProxy.checkPassword(id_customer, password);
    }

    @PostMapping("/changePassword")
    public int changePassword(@RequestParam("id_customer") int id_customer, @RequestParam("password") String password) {
        return serviceUserProxy.changePasswordReal(id_customer, password);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestParam("phone") String phone,
                                            @RequestParam("otp") String otp) {
        int result = serviceUserProxy.verifyOTP(phone, otp);
        if (result == 1) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or phone number.");
        }
    }


    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOTP(@RequestParam("phone") String phone,
                                            @RequestParam("otp") String newotp) {
        int result = serviceUserProxy.resendOTP(phone, newotp);
        if (result == 1) {
            return ResponseEntity.ok("OTP verified successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or phone number.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("phone") String phone,
                                                     @RequestParam("password") String password) {
        int result = serviceUserProxy.login(phone, password);
        Map<String, Object> response = new HashMap<>();

        if (result == 1) {
            System.out.println("Ok");
            Map<String, Object> user = serviceUserProxy.getUserByPhone(phone);

            response.put("status", "success");
            response.put("message", "Login successfully.");
            response.put("id_fk_role", user.get("id_fk_role"));
            response.put("name", user.get("name"));
            response.put("gender", user.get("gender"));
            response.put("address", user.get("address"));
            response.put("birthday", user.get("birthday"));
            response.put("image", user.get("image"));
            response.put("phone", phone);
            response.put("email", user.get("email"));
            response.put("id", user.get("id"));

            response.put("user", Map.of("name", user.get("name"), "gender", user.get("gender"), "address", user.get("address"), "birthday", user.get("birthday"), "image", user.get("image"), "id_fk_role", user.get("id_fk_role"), "phone", phone, "email", user.get("email"), "id", user.get("id")));
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Failed.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    @PostMapping("/google-signin")
    public ResponseEntity<String> googleSignIn(@RequestParam String email, @RequestParam String fullname) {
        int result = serviceUserProxy.loginEmail(email, fullname);
        if (result == 1) {
            return ResponseEntity.ok("Login by email successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email or user not active.");
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        int result = serviceUserProxy.findUserByEmail(email);
        if (result == 1) {
            return new ResponseEntity<>("Email exists", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/reset")
    public ResponseEntity<String> reset(@RequestParam String phone, @RequestParam String password) {
        int result = serviceUserProxy.findUserByPhone(phone);
        if (result == 1) {
            int result1 = serviceUserProxy.changePassword(phone, password);
            return new ResponseEntity<>("Reset success", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByEmail")
    public int findUserByEmail(@RequestParam String email) {
        return serviceUserProxy.getIdByEmail(email);
    }


    @PostMapping("/add/{role}")
    public ResponseEntity<IUser> addUser(@RequestParam("fullName") String fullName,
                                        @RequestParam("phoneNumber") String phoneNumber,
                                        @RequestParam("passW") String passW,
                                        @RequestParam("otp") String otp_code,
                                        @PathVariable("role") int roleId) {
        IUser user = new User(fullName, phoneNumber, null, passW, roleId, otp_code);
        if (serviceUserProxy.register(user) == 1) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}
