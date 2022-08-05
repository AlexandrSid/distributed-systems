package org.asidorkin.catalogservice.repository;

import org.asidorkin.catalogservice.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, String> {
    Item getItemByUniqId(String id);

    List<Item> getItemsBySku(String sku);

}
