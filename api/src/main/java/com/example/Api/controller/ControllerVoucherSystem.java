package com.example.Api.controller;


import com.example.Api.model.VoucherSystem;
import com.example.Api.pattern.facade.VoucherFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/voucherSystem")
public class ControllerVoucherSystem {
    @Autowired
    private VoucherFacade voucherFacade;

    @PostMapping("/add")
    public VoucherSystem addRes(@RequestParam("name") String name,
                                     @RequestParam("quantity") int quantity,
                                     @RequestParam("price") Double price,
                                     @RequestParam("expiry") LocalDateTime expiry,
                                     @RequestParam("id_fk") int id_fk
    ){
        return  voucherFacade.addVoucherSys(name,quantity,price,expiry,id_fk);
    }


    @GetMapping("/listVoucherSystemOfCustomer")
    public List<VoucherSystem> listVoucherSystemOfCustomer(@RequestParam("id_customer") int id_customer
    ){
        return  voucherFacade.listVoucherSystemOfCustomer(id_customer);
    }


    @GetMapping("/listVoucherSystemOfCustomer1")
    public List<VoucherSystem> listVoucherSystemOfCustomer1(@RequestParam("id_customer") int id_customer
    ){
        return  voucherFacade.listVoucherSystemOfCustomer1(id_customer);
    }
    @GetMapping("/listVoucherSystemAdd")
    public List<VoucherSystem> listVoucherSystemAdd(){
        return  voucherFacade.listVoucherSystemAdd();
    }

    @GetMapping("/listVoucherSystemManager")
    public List<VoucherSystem> listVoucherSystemManager(){
        return  voucherFacade.listVoucherSystemManager();
    }




    @PostMapping("/getVoucher")
    public String getVoucher(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        voucherFacade.getVoucherSys(id_customer,id_voucher);
        return "Ok";
    }

    @GetMapping("/check")
    public Boolean check(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherExistsSys(id_customer,id_voucher);
    }

    @GetMapping("/checkExpiry")
    public Boolean checkExpiry(@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherExpirySys(id_voucher);
    }


    @GetMapping("/checkQuantity")
    public Boolean checkQuantity(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherQuantitySys(id_customer,id_voucher);
    }


    @GetMapping("/checkExpiryAndQuantity")
    public int checkExpiryAndQuantity(@RequestParam("id_customer") int id_customer,@RequestParam("id_voucher") int id_voucher
    ){
        return  voucherFacade.checkVoucherStatusAndQuantitySys(id_customer,id_voucher);
    }

}
