package org.asidorkin.product.utils;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ItemAvailabilityDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.model.Item;

import java.util.HashMap;
import java.util.Map;

public class Utils {


    //todo
    public static ItemAvailabilityDTO EmptyItemAvailabilityDTO = new ItemAvailabilityDTO();

    public static ItemAvailabilityDTO constructAndReturnItemAvailabilityDTO(ItemsTransferDTO items, AvailabilityDTO availability, boolean filter_zeros) {
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


}
