package com.example.Api.pattern.facade;

import com.example.Api.model.VoucherRestaurant;
import com.example.Api.model.VoucherSystem;
import com.example.Api.service.ServiceVoucherRestaurant;
import com.example.Api.service.ServiceVoucherSystem;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class VoucherFacade {
    private final ServiceVoucherRestaurant serviceVoucherRestaurant;
    private final ServiceVoucherSystem serviceVoucherSystem;



    public VoucherFacade() {
        this.serviceVoucherRestaurant = new ServiceVoucherRestaurant();
        this.serviceVoucherSystem = new ServiceVoucherSystem();
    }

    public VoucherRestaurant addVoucherRes(String name, int quantity, Double price, LocalDateTime expiry, int id_fk_restaurant) {
        return serviceVoucherRestaurant.addVoucher(name, quantity, price, expiry, id_fk_restaurant);
    }

    public int active(int id) {
        return serviceVoucherRestaurant.active(id);
    }


    public List<VoucherRestaurant> listVoucherRequest(int id_fk) {
        return serviceVoucherRestaurant.listVoucherRequest(id_fk);
    }

    public List<VoucherRestaurant> listVoucherRequestAll() {
        return serviceVoucherRestaurant.listVoucherRequestAll();
    }


    public List<VoucherRestaurant> listVoucher(int id_fk) {
        return serviceVoucherRestaurant.listVoucher(id_fk);
    }

    public int deleteVoucher(int id) {
        return serviceVoucherRestaurant.deleteVoucher(id);
    }


    public List<VoucherRestaurant> listVoucherRestaurantOfCustomer(int id) {
        return serviceVoucherRestaurant.listVoucherRestaurantOfCustomer(id);
    }

    public List<VoucherRestaurant> listVoucherRestaurantOfCustomerInCart(int id_cus, int id_res) {
        return serviceVoucherRestaurant.listVoucherRestaurantOfCustomerInCart(id_cus,id_res);
    }


    public List<VoucherRestaurant> listVoucherRestaurantAdd() {
        return serviceVoucherRestaurant.listVoucherRestaurantAdd();
    }

    public int getVoucherRes(int id_cus, int id_voucher) {
        return serviceVoucherRestaurant.getVoucher(id_cus,id_voucher);
    }

    public boolean checkVoucherExistsRes(int id_cus, int id_voucher) {
        return serviceVoucherRestaurant.checkVoucherExists(id_cus,id_voucher);

    }

    public boolean checkVoucherExpiryRes(int id_voucher) {
        return serviceVoucherRestaurant.checkVoucherExpiry(id_voucher);
    }


    public boolean checkVoucherQuantityRes(int id_customer, int id_voucher) {
        return serviceVoucherRestaurant.checkVoucherQuantity(id_customer, id_voucher);
    }

    public int checkVoucherStatusAndQuantityRes(int id_customer, int id_voucher) {
        return serviceVoucherRestaurant.checkVoucherStatusAndQuantity(id_customer,id_voucher);
    }


    public VoucherSystem addVoucherSys(String name, int quantity, Double price, LocalDateTime expiry, int id_fk) {
        return serviceVoucherSystem.addVoucher(name, quantity, price, expiry, id_fk);
    }


    public List<VoucherSystem> listVoucherSystemOfCustomer(int id) {
        return serviceVoucherSystem.listVoucherSystemOfCustomer(id);
    }
    public List<VoucherSystem> listVoucherSystemOfCustomer1(int id) {
        return serviceVoucherSystem.listVoucherSystemOfCustomer1(id);
    }


    public List<VoucherSystem> listVoucherSystemAdd() {
        return serviceVoucherSystem.listVoucherSystemAdd();
    }

    public List<VoucherSystem> listVoucherSystemManager() {
        return serviceVoucherSystem.listVoucherSystemManager();
    }

    public int getVoucherSys(int id_cus,int id_voucher) {
        return serviceVoucherSystem.getVoucher(id_cus, id_voucher);
    }
    public boolean checkVoucherExistsSys(int id_cus, int id_voucher) {
        return serviceVoucherSystem.checkVoucherExists(id_cus, id_voucher);
    }

    public boolean checkVoucherExpirySys(int id_voucher) {
        return serviceVoucherSystem.checkVoucherExpiry(id_voucher);
    }

    public boolean checkVoucherQuantitySys(int id_customer, int id_voucher) {
        return serviceVoucherSystem.checkVoucherQuantity(id_customer, id_voucher);
    }
    public int checkVoucherStatusAndQuantitySys(int id_customer, int id_voucher) {
        return serviceVoucherSystem.checkVoucherStatusAndQuantity(id_customer, id_voucher);
    }


}
