package com.curso.di.factura.springboot_difactura.models;

import java.util.List;

import org.springframework.stereotype.Component;


@Component 
public class Invoice {
    private Client client;
    private String description;
    private List<Item> items;

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Item> getItems() {
        return items;
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }
    


}
