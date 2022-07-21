package org.asidorkin.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogApplication.class, args);
    }

}
// Catalog application: holds online store product data in-memory from the product data set above.
// The application exposes REST API for retrieving products by ‘uniq_id’ and list of products by ‘sku’.
