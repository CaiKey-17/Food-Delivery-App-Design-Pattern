


package com.example.Api.controller;


import com.example.Api.model.OrderDetail;
import com.example.Api.model.Restaurant;
import com.example.Api.pattern.command.*;
import com.example.Api.service.ServiceCart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class ControllerCart {
    private final CartInvoker invoker = new CartInvoker();
    private ServiceCart serviceCart;

    public ControllerCart(ServiceCart serviceCart) {
        this.serviceCart = serviceCart;
    }
    @PostMapping("/add")
    public ResponseEntity<String> addDishToCart(@RequestParam("id_customer") int id_customer,
                                                @RequestParam("id_restaurant") int id_restaurant,
                                                @RequestParam("id_dish") int id_dish,
                                                @RequestParam("quantity") int quantity) {
        ICommand addToCartCommand = new AddToCartCommand(serviceCart, id_customer, id_restaurant, id_dish, quantity);
        int result = invoker.executeCommand(addToCartCommand);
        if (result == 1) {
            return ResponseEntity.ok("Add to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/addMore")
    public ResponseEntity<String> addMore(@RequestParam("id_order") String id_order,
                                          @RequestParam("id_dish") int id_dish) {
        ICommand command = new AddMoreToCartCommand(serviceCart, id_order, id_dish);
        int result = invoker.executeCommand(command);
        if (result == 1) {
            return ResponseEntity.ok("Add more to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/minus")
    public ResponseEntity<String> minus(@RequestParam("id_order") String id_order,
                                        @RequestParam("id_dish") int id_dish) {
        ICommand command = new MinusToCartCommand(serviceCart, id_order, id_dish);
        int result = invoker.executeCommand(command);
        if (result == 1) {
            return ResponseEntity.ok("Minus to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteToCart(@RequestParam("id") int id) {
        ICommand command = new DeleteFromCartCommand(serviceCart, id);
        int result = invoker.executeCommand(command);
        if (result == 1) {
            return ResponseEntity.ok("Delete to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @GetMapping("/listDishInCart")
    public ResponseEntity<List<OrderDetail>> listDishInCart(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceCart.listDishInCart(id_customer));
    }

    @GetMapping("/getNameRestaurant")
    public ResponseEntity<Restaurant> getNameRestaurant(@RequestParam("id_customer") int id_customer) {
        return ResponseEntity.ok(serviceCart.getNameRestaurantByOder(id_customer));
    }

    @PostMapping("/applyVoucherRes")
    public ResponseEntity<String> applyVoucherRes(@RequestParam("oder_id") String oder_id,@RequestParam("voucher_id") int voucher_id) {
        int result = serviceCart.applyVoucherRes(oder_id,voucher_id);
        if (result == 1) {
            return ResponseEntity.ok("Apply to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/applyVoucherSys")
    public ResponseEntity<String> applyVoucherSys(@RequestParam("oder_id") String oder_id,@RequestParam("voucher_id") int voucher_id) {
        int result = serviceCart.applyVoucherSys(oder_id,voucher_id);
        if (result == 1) {
            return ResponseEntity.ok("Apply to cart success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @GetMapping("/getOrderId")
    public ResponseEntity<Map<String, String>> getOrderId(@RequestParam("id_customer") int id_customer) {
        String orderId = serviceCart.getOrderId(id_customer);
        Map<String, String> response = new HashMap<>();
        response.put("orderId", orderId);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/getSumVoucher")
    public ResponseEntity<Map<String, Double>> getOrderId(@RequestParam("oder_id") String oder_id) {
        Double sum = serviceCart.sumVoucher(oder_id);
        Map<String, Double> response = new HashMap<>();
        response.put("orderId", sum);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestParam("oder_id") String oder_id,@RequestParam("payment") int payment,
                                          @RequestParam("latitude") Double latitude,
                                          @RequestParam("longitude") Double longitude) {
        int result = serviceCart.confirm(oder_id,payment,latitude,longitude);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam("oder_id") String oder_id){
        int result = serviceCart.cancel(oder_id);
        if (result == 1) {
            return ResponseEntity.ok("Cancel success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<String> accept(@RequestParam("oder_id") String oder_id) {
        int result = serviceCart.accept(oder_id);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/done")
    public ResponseEntity<String> done(@RequestParam("oder_id") String oder_id) {
        int result = serviceCart.done(oder_id);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/naptien")
    public ResponseEntity<String> naptien(@RequestParam("id") int id,
                                          @RequestParam("tien") double tien) {
        int result = serviceCart.naptien(id,tien);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/trutien")
    public ResponseEntity<String> trutien(@RequestParam("id") int id,
                                          @RequestParam("tien") double tien) {
        int result = serviceCart.trutien(id,tien);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/delivering")
    public ResponseEntity<String> delivering(@RequestParam("oder_id") String oder_id) {
        int result = serviceCart.delivering(oder_id);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }

    @PostMapping("/ok")
    public ResponseEntity<String> ok(@RequestParam("oder_id") String oder_id,@RequestParam("shiper") int shiper) {
        int result = serviceCart.ok(oder_id,shiper);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }


    @PostMapping("/hoantat")
    public ResponseEntity<String> hoantat(@RequestParam("oder_id") String oder_id,@RequestParam("shiper") int shiper,@RequestParam("money") double money) {
        int result = serviceCart.hoantat(oder_id,shiper,money);
        if (result == 1) {
            return ResponseEntity.ok("Order success.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed.");
        }
    }
}