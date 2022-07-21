package org.asidorkin.catalog.repository;

import org.asidorkin.catalog.model.Item;
import org.asidorkin.catalog.utilities.Utilities;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RepositoryImpl implements ItemRepository {

    List<Item> itemHolder = Utilities.readCSVFile();

    @Override
    public Item getItemByID(String id) {
        return itemHolder.stream().filter(i -> i.getUniqId().equals(id)).findFirst().orElse(new Item());
    }

    @Override
    public List<Item> getItemsBySku(String sku) {
        return itemHolder.stream().filter(i -> i.getSku().equals(sku)).collect(Collectors.toList());
    }


}
