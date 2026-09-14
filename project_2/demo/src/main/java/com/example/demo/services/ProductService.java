package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.repositories.IProductRepository;

@Service
@PropertySource("classpath:config.properties")
public class ProductService {

    private final IProductRepository productRepository;

    @Autowired 
    private Environment environment;
    
    public ProductService(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.allFind().stream()
                .map(p -> new Product(p.getId(), p.getName(), p.getPrice() * (1 + environment.getProperty("tax", Double.class)), p.getDescription()))
                .toList(); // Return products with tax applied to price
    }

    public List<Product> getAllProductsWithoutTax() {
        return productRepository.allFind();
    }

    public String getProductNameById(Long id) {
        return productRepository.getProductNameById(id);
    }
}
