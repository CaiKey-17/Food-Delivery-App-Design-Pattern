package com.example.Api.controller;

import com.example.Api.pattern.facade.VoucherFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import com.example.Api.model.VoucherRestaurant;
import java.util.*;
@RestController
@RequestMapping("/voucherRestaurant")
public class ControllerVoucherRestaurant {

    @Autowired
    private VoucherFacade voucherFacade;

    @PostMapping("/add")
    public VoucherRestaurant addRes(@RequestParam("name") String name,
                                    @RequestParam("quantity") int quantity,
                                    @RequestParam("price") Double price,
                                    @RequestParam("expiry") LocalDateTime expiry,
                                    @RequestParam("id_fk_restaurant") int id_fk_restaurant
                                     ){
        return  voucherFacade.addVoucherRes(name,quantity,price,expiry,id_fk_restaurant);
    }

    @PostMapping("/active")
    public String active(@RequestParam("id") int id
    ){
        voucherFacade.active(id);
        return "Actived";
    }

    @GetMapping("/listRequest")
    public List<VoucherRestaurant> listVoucherRequest(@RequestParam("id_fk") int id_fk
    ){
        return  voucherFacade.listVoucherRequest(id_fk);
    }

    @GetMapping("/listRequestAll")
    public List<VoucherRestaurant> listRequestAll(){
        return  voucherFacade.listVoucherRequestAll();
    }

    @GetMapping("/list")
    public List<VoucherRestaurant> listVoucher(@RequestParam("id_fk") int id_fk
    ){
        return  voucherFacade.listVoucher(id_fk);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVoucher(@PathVariable int id) {
        int result = voucherFacade.deleteVoucher(id);
        if (result > 0) {
            return ResponseEntity.ok("Voucher with id " + id + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Voucher not found.");
        }
    }

    @GetMapping("/listVoucherRestaurantOfCustomer")
    public List<VoucherRestaurant> listVoucherRestaurantOfCustomer(@RequestParam("id_customer") int id_customer
    ){
        return  voucherFacade.listVoucherRestaurantOfCustomer(id_customer);
    }

    @GetMapping("/listVoucherRestaurantOfCustomerInCart")
    public List<VoucherRestaurant> listVoucherRestaurantOfCustomerInCart(@RequestParam("id_customer") int id_customer, @RequestParam("id_res") int id_res
    ){
        return  voucherFacade.listVoucherRestaurantOfCustomerInCart(id_customer,id_res);
    }

    @GetMapping("/listVoucherRestaurantAdd")
    public List<VoucherRestaurant> listVoucherRestaurantAdd(){
        return  voucherFacade.listVoucherRestaurantAdd();
    }


    @PostMapping("/getVoucher")
    public String getVoucher(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        voucherFacade.getVoucherRes(id_customer,id_voucher);
        return "OK";
    }

    @GetMapping("/check")
    public Boolean check(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherExistsRes(id_customer,id_voucher);
    }

    @GetMapping("/checkExpiry")
    public Boolean checkExpiry(@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherExpiryRes(id_voucher);
    }


    @GetMapping("/checkQuantity")
    public Boolean checkQuantity(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherQuantityRes(id_customer,id_voucher);
    }

    @GetMapping("/checkExpiryAndQuantity")
    public int checkExpiryAndQuantity(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherStatusAndQuantityRes(id_customer,id_voucher);
    }














}
