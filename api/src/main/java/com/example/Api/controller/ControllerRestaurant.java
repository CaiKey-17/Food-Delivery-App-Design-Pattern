package com.example.Api.controller;

import com.example.Api.model.Dish;
import com.example.Api.model.Restaurant;
import com.example.Api.service.ServiceRestaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class ControllerRestaurant {

    @Autowired
    private ServiceRestaurant serviceRestaurant;

    public ControllerRestaurant(ServiceRestaurant serviceRestaurant) {
        this.serviceRestaurant = serviceRestaurant;
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Restaurant>> listAllRestaurant(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceRestaurant.listAllRestaurant(id_customer));
    }

    @GetMapping("/listAll1")
    public ResponseEntity<List<Restaurant>> listAllRestaurant1(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceRestaurant.listAllRestaurant1(id_customer));
    }

    @GetMapping("/listAllRestaurantAdmin")
    public ResponseEntity<List<Restaurant>> listAllRestaurantAdmin() {
        return ResponseEntity.ok(serviceRestaurant.listAllRestaurantAdmin());
    }

    @GetMapping("/listAllSearch")
    public ResponseEntity<List<Restaurant>> listAllSearch(@RequestParam("id_customer") int id_customer,
                                                               @RequestParam("name") String name) {
        return ResponseEntity.ok(serviceRestaurant.listAllSearch(id_customer,name));
    }

    @GetMapping("/detail")
    public ResponseEntity<Restaurant> detailOfRestaurant(@RequestParam("id_customer") int id_customer,@RequestParam("id_res") int id_res) {
        return ResponseEntity.ok(serviceRestaurant.detailOfRestaurant(id_customer,id_res));
    }
}
