package com.codemechanix.devicemanagement.service;

import com.codemechanix.devicemanagement.model.Product;
import com.codemechanix.devicemanagement.repo.ProductRepoImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepoImpl _repo;

    public void deleteById(Long productId) {
        _repo.delete(productId.toString());
    }

    public Map<String, Product> getAllProducts() {
        return _repo.findAll();
    }

    public Product getById(Long productId) {
        return _repo.findById(productId.toString());
    }

    public void update(Product product) {
        _repo.save(product);
    }

    public void crate(Product product) {
        _repo.save(product);
    }
}
