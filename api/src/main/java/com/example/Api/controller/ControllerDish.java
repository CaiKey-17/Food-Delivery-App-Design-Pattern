package com.example.Api.controller;

import com.example.Api.model.Dish;
import com.example.Api.service.ServiceDish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class ControllerDish {

    @Autowired
    private ServiceDish serviceDish;

    public ControllerDish(ServiceDish serviceDish) {
        this.serviceDish = serviceDish;
    }

    @PostMapping("/add")
    public Dish addGroupDish(@RequestParam("name") String name,
                                                @RequestParam("price") Double price,
                                                @RequestParam("quantity") int quantity,
                                                @RequestParam("describe") String describe,
                                                @RequestParam("id_restaurant") int id_restaurant,
                                                @RequestParam("id_group") String id_group,
                                                @RequestParam("image") MultipartFile imageFile) {
        // Save the image to a folder
        String folder = "src/main/resources/static/images/";
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes()); // Save the image
        } catch (Exception e) {
            e.printStackTrace();
        }
        String image = "/images/" + imageFileName;

        return serviceDish.addDish(name,price,quantity,describe,id_restaurant,id_group,image);
    }

    @GetMapping("/listByRestaurant")
    public ResponseEntity<List<Dish>> listRestaurantDish(@RequestParam("restaurant") int restaurant) {
        return ResponseEntity.ok(serviceDish.listAllDishByRestaurant(restaurant));
    }

    @GetMapping("/listByGroup")
    public ResponseEntity<List<Dish>> listGroupDish(@RequestParam("groupdish") String groupdish) {
        return ResponseEntity.ok(serviceDish.listAllDishByGroupDish(groupdish));
    }

    @GetMapping("/listGroupDishSearch")
    public ResponseEntity<List<Dish>> listGroupDishSearch(@RequestParam("groupdish") String groupdish,
                                                          @RequestParam("name") String name
                                                          ) {
        return ResponseEntity.ok(serviceDish.listAllDishByGroupDishSearch(groupdish,name));
    }

    @GetMapping("/listDish")
    public ResponseEntity<List<Dish>> listDish() {
        return ResponseEntity.ok(serviceDish.listDish());
    }

    @GetMapping("/listLimit10")
    public ResponseEntity<List<Dish>> listAllDishLimit10() {
        return ResponseEntity.ok(serviceDish.listAllDishLimit10());
    }

    @GetMapping("/listDishGoiY")
    public ResponseEntity<List<Dish>> listDishGoiY() {
        return ResponseEntity.ok(serviceDish.listDishGoiY());
    }

    @GetMapping("/detail")
    public ResponseEntity<Dish> detailDish(@RequestParam("id") int id) {
        return ResponseEntity.ok(serviceDish.detail(id));
    }

    @GetMapping("/searchDish")
    public ResponseEntity<List<Dish>> searchDish(@RequestParam("name") String name) {
        return ResponseEntity.ok(serviceDish.searchDishByName(name));
    }
    @GetMapping("/getIdRestaurant")
    public ResponseEntity<Integer> getIdRestaurant(@RequestParam("id") int id) {
        return ResponseEntity.ok(serviceDish.getIdRestaurant(id));
    }

    @PutMapping("/update")
    public String updateDish(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam int quantity,
            @RequestParam String describe,
            @RequestParam int id_fk_restaurant,
            @RequestParam String id_fk_group_dish,
            @RequestParam MultipartFile imageFile
    ) {
        String folder = "src/main/resources/static/images/";
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes()); // Save the image
        } catch (Exception e) {
            e.printStackTrace();
        }
        String image = "/images/" + imageFileName;

        serviceDish.updateDish(id, name, price, quantity, describe, id_fk_restaurant, id_fk_group_dish, image);
        return "Success update";
    }



    @PutMapping("/update1")
    public String updateDish1(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam int quantity,
            @RequestParam String describe,
            @RequestParam int id_fk_restaurant,
            @RequestParam String id_fk_group_dish
    ) {


        serviceDish.updateDish1(id, name, price, quantity, describe, id_fk_restaurant, id_fk_group_dish);
        return "Success update";
    }




}
