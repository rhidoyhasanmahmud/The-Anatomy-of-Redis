package com.codemechanix.devicemanagement.repo;

import com.codemechanix.devicemanagement.model.Product;

import java.util.Map;

public interface ProductRepo {
    void save(Product rcPendingOdDeviceDto);

    Map<String, Product> findAll();

    Product findById(String deviceDetailsId);

    void update(Product deviceDetails);

    void delete(String deviceDetailsId);
}
