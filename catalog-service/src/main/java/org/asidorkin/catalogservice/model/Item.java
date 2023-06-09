package org.asidorkin.catalogservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String uniqId;
    private String sku;
    private String name_title;
    @Column(length = 10000)
    private String description;
    private String list_price;
    private String sale_price;
    private String category;
    private String category_tree;
    private String averageproductrating;
    @Column(length = 1024)
    private String product_url;
    @Column(length = 2500)
    private String productimageurls;
    private String brand;
    private String totalnumberreviews;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Review> reviews = new java.util.ArrayList<>();

}
