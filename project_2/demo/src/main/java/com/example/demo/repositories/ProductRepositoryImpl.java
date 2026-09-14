package com.example.demo.repositories;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Product;

@Repository
public class ProductRepositoryImpl implements IProductRepository {


    private List<Product> data;

    public ProductRepositoryImpl() {
        // Initialize the data list with some sample products
        this.data = Arrays.asList(
            new Product(1L, "Product 1", 10.0, "Description of Product 1"),
            new Product(2L, "Product 2", 20.0, "Description of Product 2"),
            new Product(3L, "Product 3", 30.0, "Description of Product 3")
        );
    }
    
    @Override
    public String getProductNameById(Long id) {
        for (Product product : data) {
            if (product.getId().equals(id)) {
                return product.getName();
            }
        }
        return null; // Placeholder return statement
    }

    @Override
    public List<Product> allFind() {
        return data; // Return the list of products
    }
    
}
