package com.example.Api.pattern.repository;

import com.example.Api.model.Shipper;
import java.util.List;

public interface IShipperRepository {
    int acceptShiper(int id);
    List<Shipper> listAllShiperRequest();
    List<Shipper> listAllShiper();
}
