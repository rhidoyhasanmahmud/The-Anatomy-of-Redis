package com.codemechanix.devicemanagement.repo;

import com.codemechanix.devicemanagement.model.Product;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class ProductRepoImpl implements ProductRepo {
    private RedisTemplate<String, Product> redisTemplate;
    private HashOperations hashOperations;
    private String redisObjectName = "RcPendingOdDevice";

    public ProductRepoImpl(RedisTemplate<String, Product> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(Product product) {
        hashOperations.put(redisObjectName, product.getId(), product);
    }

    @Override
    public Map<String, Product> findAll() {
        return hashOperations.entries(redisObjectName);
    }

    @Override
    public Product findById(String deviceDetailsId) {
        return (Product) hashOperations.get(redisObjectName, deviceDetailsId);
    }

    @Override
    public void update(Product product) {
        save(product);
    }

    @Override
    public void delete(String deviceDetailsId) {
        hashOperations.delete(redisObjectName, deviceDetailsId);
    }
}
