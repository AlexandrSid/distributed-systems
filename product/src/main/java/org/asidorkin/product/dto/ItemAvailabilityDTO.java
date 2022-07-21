package org.asidorkin.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asidorkin.product.model.Item;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAvailabilityDTO {
    private HashMap<Item, Integer> itemsAvailable;

}
