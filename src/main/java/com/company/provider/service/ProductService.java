package com.company.provider.service;

import com.company.provider.exeption.RestException;
import com.company.provider.entity.Product;
import com.company.provider.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RestException("product_not_found_exception", null, true, false));
    }
}
