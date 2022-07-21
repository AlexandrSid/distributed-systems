package org.asidorkin.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }

}
// Inventory application: holds online store product availability data.
// Generate random availability status for each product from the product data set above
// and keep it in an in-memory data structure.
// The application exposes REST API for retrieving product availability by a list of ‘uniq_id’.
