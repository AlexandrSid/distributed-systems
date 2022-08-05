package org.asidorkin.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asidorkin.product.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private List<ItemsAvailability> itemsAvailable;
    public ProductResponseDTO(HashMap<Item, Integer> map){
        itemsAvailable = map
                .entrySet()
                .stream()
                .map(entry -> new ItemsAvailability(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @AllArgsConstructor
    @Data
    private class ItemsAvailability {
        private Item item;
        private Integer available;
    }

    public String makeSimpleRepresentation(){
        return itemsAvailable
                .stream()
                .map(e -> "id = " + e.getItem().getUniqId() + "; " +
                        "sku = " + e.getItem().getSku() + "; " +
                        "available number = " + e.getAvailable() + "<br />")
                .reduce("Item(s): <br />", (subtotal, item) -> subtotal + item);
    }
}
