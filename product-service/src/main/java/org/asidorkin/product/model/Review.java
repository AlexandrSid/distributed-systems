package org.asidorkin.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long id;

    private String review;

    public Review(String review){
        this.review = review;
    }
}