package org.asidorkin.product.services;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ItemAvailabilityDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.model.Item;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    public ItemAvailabilityDTO EmptyItemAvailabilityDTO = new ItemAvailabilityDTO();

    public  ItemAvailabilityDTO constructAndReturnItemAvailabilityDTO(ItemsTransferDTO items, AvailabilityDTO availability, boolean filter_zeros) {
        HashMap<Item, Integer> result = new HashMap<>();

        Map<String, Integer> availabilityMap = availability.getData();
        for (Item item : items.getItems()) {
            Integer itemAvailable = availabilityMap.get(item.getUniqId());
            if (!filter_zeros || itemAvailable > 0) {
                result.put(item, itemAvailable);
            }
        }
        return new ItemAvailabilityDTO(result);
    }

    public String convertResponse2String(ItemAvailabilityDTO itemAvailabilityDTO) {
        return itemAvailabilityDTO.getItemsAvailable().entrySet()
                .stream()
                .map(e -> "id = " + e.getKey().getUniqId() + "; " +
                        "sku = " + e.getKey().getSku() + "; " +
                        "available number = " + e.getValue() + "<br />")
                .reduce("Item(s): <br />", (subtotal, item) -> subtotal + item);
    }
}
