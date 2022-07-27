package org.asidorkin.catalog.repository;

import lombok.SneakyThrows;
import org.asidorkin.catalog.model.Item;
import org.asidorkin.catalog.utilities.Utilities;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryImpl implements ItemRepository {

    List<Item> itemHolder = Utilities.readCSVFile();

    @SneakyThrows
    @Override
    public Item getItemByID(String id) {
        Thread.sleep(1000);
        return itemHolder.stream().filter(i -> i.getUniqId().equals(id)).findFirst().orElse(new Item());
    }

    @SneakyThrows
    @Override
    public List<Item> getItemsBySku(String sku) {
        Thread.sleep(1500);
        return itemHolder.stream().filter(i -> i.getSku().equals(sku)).collect(Collectors.toList());
    }


}
