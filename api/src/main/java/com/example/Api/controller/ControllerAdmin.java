package com.example.Api.controller;

import com.example.Api.model.Shipper;
import com.example.Api.service.ServiceShipper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class ControllerAdmin {

    @Autowired
    private ServiceShipper serviceShipper;

    @PostMapping("/active")
    public String activeShiper(@RequestParam("id") int id
    ){
        serviceShipper.approveShiper(id);
        return "Actived success shiper";
    }

    @GetMapping("/listAllShiperRequest")
    public ResponseEntity<List<Shipper>> listAllShiperRequest() {
        return ResponseEntity.ok(serviceShipper.getPendingShippers());
    }

    @GetMapping("/listAllShiper")
    public ResponseEntity<List<Shipper>> listAllShiper() {
        return ResponseEntity.ok(serviceShipper.getApprovedShippers());
    }
}
