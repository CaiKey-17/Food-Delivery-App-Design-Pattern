package com.example.Api.service;

import com.example.Api.pattern.repository.ShipperRepository;
import com.example.Api.model.Shipper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceShipper {
    private final ShipperRepository shipperRepository;

    public ServiceShipper(ShipperRepository shiperRepository) {
        this.shipperRepository = shiperRepository;
    }

    public boolean approveShiper(int id) {
        return shipperRepository.acceptShiper(id) > 0;
    }

    public List<Shipper> getPendingShippers() {
        return shipperRepository.listAllShiperRequest();
    }

    public List<Shipper> getApprovedShippers() {
        return shipperRepository.listAllShiper();
    }
}
