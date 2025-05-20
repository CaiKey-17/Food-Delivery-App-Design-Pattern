package com.example.Api.controller;

import com.example.Api.model.OrderDetail;
import com.example.Api.model.SellingOrder;
import com.example.Api.service.ServiceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class ControllerOrder {

    @Autowired
    private ServiceOrder serviceOrder;


    @GetMapping("/list")
    public ResponseEntity<List<SellingOrder>> getAllOrderOfRestaurant(@RequestParam("id_restaurant") int id_restaurant) {
        return ResponseEntity.ok(serviceOrder.getAllOrderOfRestaurant(id_restaurant));
    }

    @GetMapping("/listHis")
    public ResponseEntity<List<SellingOrder>> getAllOrderOfRestaurantHis(@RequestParam("id_restaurant") int id_restaurant) {
        return ResponseEntity.ok(serviceOrder.getAllOrderOfRestaurantHis(id_restaurant));
    }
    @GetMapping("/listAccept")
    public ResponseEntity<List<SellingOrder>> getAllOrderAcceptOfRestaurant(@RequestParam("id_restaurant") int id_restaurant) {
        return ResponseEntity.ok(serviceOrder.getAllOrderAcceptOfRestaurant(id_restaurant));
    }


    @GetMapping("/listDetail")
    public ResponseEntity<List<OrderDetail>> getDetailOrderOfRestaurant(@RequestParam("order_id") String order_id) {
        return ResponseEntity.ok(serviceOrder.listDetailOrder(order_id));
    }


    @GetMapping("/listOrder")
    public ResponseEntity<List<SellingOrder>> getAllOrderOfCustomer(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceOrder.getAllOrderOfCustomer(id_customer));
    }

    @GetMapping("/listOrderDelivering")
    public ResponseEntity<List<SellingOrder>> getAllOrderOfCustomerDelivering(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceOrder.getAllOrderOfCustomerDelivering(id_customer));
    }
    @GetMapping("/listOrderHistory")
    public ResponseEntity<List<SellingOrder>> listOrderHistory(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceOrder.getAllOrderOfCustomerHistory(id_customer));
    }

    @PostMapping("/ratingCus")
    public String ratingCus(@RequestParam("orderId") String orderId,
                            @RequestParam("id_fk_restaurant") int id_fk_restaurant,
                            @RequestParam("ratingRes") double ratingRes,
                            @RequestParam("id_fk_shiper") int id_fk_shiper,
                            @RequestParam("ratingShiper") double ratingShiper) {
        serviceOrder.ratingCus(orderId,id_fk_restaurant,ratingRes,id_fk_shiper,ratingShiper);
        return "Success rating";

    }






}
