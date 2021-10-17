package com.codemechanix.devicemanagement.controller;

import com.codemechanix.devicemanagement.model.Product;
import com.codemechanix.devicemanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    private final ProductService productService;

    @PostMapping
    private void create(@RequestBody Product product) {
        productService.crate(product);
    }

    @PutMapping
    private void update(@RequestBody Product product) {
        productService.update(product);
    }

    @GetMapping("/get/{id}")
    private Product getById(@PathVariable Long productId) {
        return productService.getById(productId);
    }

    @GetMapping("/getAll")
    private Map<String, Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("deleteBy/{id}")
    private void deleteById(@PathVariable Long productId) {
        productService.deleteById(productId);
    }
}
