package com.curso.di.factura.springboot_difactura;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.curso.di.factura.springboot_difactura.models.Item;
import com.curso.di.factura.springboot_difactura.models.Product;

@Configuration 
@PropertySource("classpath:data.properties")
public class AppConfig {
    
    @Bean
    List<Item> itemInvoice(){
        Product product1 = new Product("HeadhPhone JBL", 100);
        Product product2 = new Product("Casio G shock", 200);
        return Arrays.asList(new Item(product1, 5), new Item(product2, 10));
    }
    
}
