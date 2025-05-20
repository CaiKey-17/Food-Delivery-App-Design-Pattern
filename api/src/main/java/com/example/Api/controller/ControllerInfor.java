package com.example.Api.controller;


import com.example.Api.inheritance.IUser;
import com.example.Api.model.User;
import com.example.Api.service.ServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/infor")
public class ControllerInfor {

    @Autowired
    private ServiceUser serviceUser;

    public ControllerInfor(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @GetMapping()
    public IUser getInfor(@RequestParam("id_user") int id_user){
        return serviceUser.getInforById(id_user);
    }

    @PostMapping("changePhoneNumber")
    public int changePhoneNumber(@RequestParam("id_user") int id_user,@RequestParam("phone") String phone ){
        return serviceUser.changePhoneNumber(id_user,phone);

    }

    @PostMapping("changeAddress")
    public int changeAddress(@RequestParam("id_user") int id_user,@RequestParam("address") String address ){
        return serviceUser.updateAddress(id_user,address);
    }

    @PostMapping("updateNameRes")
    public int updateNameRes(@RequestParam("id_user") int id_user,@RequestParam("name") String name ){
        return serviceUser.updateNameRes(id_user,name);
    }

    @GetMapping("/getNameRes")
    public String getNameRes(@RequestParam("id_user") int id_user){
        return serviceUser.getNameRes(id_user);
    }

    @PostMapping("changeName")
    public int changeName(@RequestParam("id_user") int id_user,@RequestParam("name") String name ){
        return serviceUser.changeName(id_user,name);

    }

    @PostMapping("changeEmailOk")
    public int changeEmail(@RequestParam("id_user") int id_user,@RequestParam("email") String email ){
        return serviceUser.changeEmail(id_user,email);

    }

    @PostMapping("/change")
    public int change(@RequestParam("id_user") int id_user,
                            @RequestParam("gender") String gender,
                            @RequestParam("image") MultipartFile imageFile) {
        String folder = "src/main/resources/static/images/";
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String image = "/images/" + imageFileName;


        return serviceUser.change(id_user, gender, image);
    }
    @PostMapping("/changeTemp")
    public int changeTemp(@RequestParam("id_user") int id_user,
                            @RequestParam("gender") String gender
                           ) {



        return serviceUser.changeTemp(id_user, gender);
    }

    @GetMapping("/getPositionRestaurant")
    public double[] getPositionRestaurant(@RequestParam("order_id") String order_id){
        return serviceUser.getCoordinates(order_id);
    }

    @GetMapping("/getInforOrder")
    public String[] getInforOrder(@RequestParam("order_id") String order_id){
        return serviceUser.getInforOrder(order_id);
    }

    @GetMapping("/getInforOrderCus")
    public String[] getInforOrderCus(@RequestParam("order_id") String order_id){
        return serviceUser.getInforOrderCus(order_id);
    }

    @PostMapping("/updateImage")
    public int updateImage(@RequestParam("id_user") int id_user,
                      @RequestParam("image") MultipartFile imageFile) {
        String folder = "src/main/resources/static/images/";
        String imageFileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String image = "/images/" + imageFileName;


        return serviceUser.updateImage(id_user, image);
    }

    @PostMapping("/updatePosition")
    public int updatePosition(@RequestParam("id_user") int id_user,
                              @RequestParam("latitude") Double latitude,
                              @RequestParam("longtitude") Double longtitude) {

        return serviceUser.updatePosition(id_user, latitude,longtitude);
    }

    @GetMapping("/getMoney")
    public String[] getMoney(@RequestParam("id_user") int id_user){
        return serviceUser.getMoney(id_user);
    }

    @GetMapping("/sodu")
    public Double sodu(@RequestParam("id_user") int id_user){
        return serviceUser.sodu(id_user);
    }

    @GetMapping("/tonghoadon")
    public Double tonghoadon(@RequestParam("orderId") String orderId){
        return serviceUser.tonghoadon(orderId);
    }

    @GetMapping("/checkStatus")
    public int checkStatus(@RequestParam("id_user") int id_user){
        return serviceUser.checkStatus(id_user);
    }

    @GetMapping("/checkPhoneAndAddress")
    public int checkPhoneAndAddress(@RequestParam("id_user") int id_user){
        return serviceUser.checkPhoneAndAddress(id_user);
    }

    @GetMapping("/checkNameAndAddress")
    public int checkNameAndAddress(@RequestParam("id_user") int id_user){
        return serviceUser.checkNameAndAddress(id_user);
    }






}
