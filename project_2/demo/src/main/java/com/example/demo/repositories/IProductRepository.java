package com.example.demo.repositories;

import java.util.List;

import com.example.demo.models.Product;

public interface IProductRepository {
    
    public String getProductNameById(Long id);
    public List<Product> allFind();

}
