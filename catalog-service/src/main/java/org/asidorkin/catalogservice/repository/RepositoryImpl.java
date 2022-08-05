package org.asidorkin.catalogservice.repository;

import lombok.SneakyThrows;
import org.asidorkin.catalogservice.model.Item;
import org.asidorkin.catalogservice.ItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class RepositoryImpl {

    @Autowired
    ItemService service;

    List<Item> itemHolder = service.readCSVFile();

    @SneakyThrows
    public Item getItemByID(String id) {
        Thread.sleep(1000);
        return itemHolder.stream().filter(i -> i.getUniqId().equals(id)).findFirst().orElse(new Item());
    }

    @SneakyThrows
    public List<Item> getItemsBySku(String sku) {
        Thread.sleep(1500);
        return itemHolder.stream().filter(i -> i.getSku().equals(sku)).collect(Collectors.toList());
    }


}
