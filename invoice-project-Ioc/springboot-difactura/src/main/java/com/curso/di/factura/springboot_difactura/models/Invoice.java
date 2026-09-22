package com.curso.di.factura.springboot_difactura.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component 
public class Invoice {

    @Autowired 
    private Client client;

    @Value("${invoice.description}")
    private String description;

    @Autowired 
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

    public int getTotalWithForEach(){
        int total = 0;

        for (Item item: items) {
            total += item.getImporte();
        }

        return total;
    }

    public int getTotalWithStream(){
        return items.stream().map(item -> item.getImporte()).reduce(0, (sum, item) -> sum + item);
    }
    


}
