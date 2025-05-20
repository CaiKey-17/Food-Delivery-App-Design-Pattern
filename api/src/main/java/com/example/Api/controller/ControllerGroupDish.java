package com.example.Api.controller;

import com.example.Api.model.GroupDish;
import com.example.Api.service.ServiceGroupDish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/groupdish")
public class ControllerGroupDish {
    @Autowired
    private ServiceGroupDish serviceGroupDish;

    public ControllerGroupDish(ServiceGroupDish serviceGroupDish) {
        this.serviceGroupDish = serviceGroupDish;
    }

    @PostMapping("/add")
    public GroupDish addGroupDish(@RequestParam("name") String name, @RequestParam("image") MultipartFile imageFile) throws Exception {
        String folder = "src/main/resources/static/images/";

        // Kiểm tra kích thước tệp trước khi tiếp tục
        if (imageFile.getSize() > 10485760) { // 10MB
            throw new Exception("Kích thước tệp quá lớn");
        }

        // Lưu hình ảnh chính
        String imageFileName = imageFile.getOriginalFilename();
        Path imagePath = Paths.get(folder + imageFileName);

        try {
            Files.write(imagePath, imageFile.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Lỗi trong quá trình lưu hình ảnh: " + e.getMessage());
        }
        String image = "/images/" + imageFileName;

        int result = serviceGroupDish.addGroupDish(name,image);
        if(result == 1){
            return new GroupDish(name,image);
        }
        return null;

    }

    @GetMapping("/list")
    public ResponseEntity<List<GroupDish>> listGroupDish() {
        return ResponseEntity.ok(serviceGroupDish.getAllGroupDishes());
    }
}
