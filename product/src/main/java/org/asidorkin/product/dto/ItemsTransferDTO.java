package org.asidorkin.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asidorkin.product.model.Item;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemsTransferDTO {
    private List<Item> items;
}
