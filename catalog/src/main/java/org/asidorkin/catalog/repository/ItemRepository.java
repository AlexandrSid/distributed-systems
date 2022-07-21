package org.asidorkin.catalog.repository;

import org.asidorkin.catalog.model.Item;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository {
    Item getItemByID(String id);

    List<Item> getItemsBySku(String sku);

}
