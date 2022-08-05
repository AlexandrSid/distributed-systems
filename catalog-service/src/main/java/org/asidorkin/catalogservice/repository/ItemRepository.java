package org.asidorkin.catalogservice.repository;

import org.asidorkin.catalogservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    Item getItemByUniqId(String id);

    List<Item> getItemsBySku(String sku);

}
