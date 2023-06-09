package org.asidorkin.product.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class Item {
    private String uniqId;
    private String sku;
    private String name_title;
    private String description;
    private String list_price;
    private String sale_price;
    private String category;
    private String category_tree;
    private String averageproductrating;
    private String product_url;
    private String productimageurls;
    private String brand;
    private String totalnumberreviews;
    private List<Review> reviews;

}
