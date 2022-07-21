package org.asidorkin.eurekaserver;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    private String[] reviews;

    @Override
    public String toString() {
        List<String> collect = Arrays.stream(reviews).map(s -> "    " + s + '\n').collect(Collectors.toList());

        return "----------------------- \n " +
                "Item{" + '\n' +
                ", uniqId='" + uniqId + '\'' + '\n' +
                ", sku='" + sku + '\'' + '\n' +
                ", name_title='" + name_title + '\'' + '\n' +
                ", description='" + description + '\'' + '\n' +
                ", list_price='" + list_price + '\'' + '\n' +
                ", sale_price='" + sale_price + '\'' + '\n' +
                ", category='" + category + '\'' + '\n' +
                ", category_tree='" + category_tree + '\'' + '\n' +
                ", averageproductrating='" + averageproductrating + '\'' + '\n' +
                ", product_url='" + product_url + '\'' + '\n' +
                ", productimageurls='" + productimageurls + '\'' + '\n' +
                ", brand='" + brand + '\'' + '\n' +
                ", totalnumberreviews='" + totalnumberreviews + '\'' + '\n' +
                ", reviews=" + '\n' +
                collect +
                '\n' + '\n' + "-----------------------";
    }
}
