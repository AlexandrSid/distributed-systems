package org.asidorkin.product.services;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ProductResponseDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.model.Item;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductService {

    public ProductResponseDTO EmptyItemAvailabilityDTO = new ProductResponseDTO();

    public ProductResponseDTO constructAndReturnProductResponseDTO(ItemsTransferDTO items, AvailabilityDTO availability, boolean filter_zeros) {
        HashMap<Item, Integer> result = new HashMap<>();

        Map<String, Integer> availabilityMap = availability.getData();
        for (Item item : items.getItems()) {
            Integer itemAvailable = availabilityMap.get(item.getUniqId());
            if (!filter_zeros || itemAvailable > 0) {
                result.put(item, itemAvailable);
            }
        }
        return new ProductResponseDTO(result);
    }

}
