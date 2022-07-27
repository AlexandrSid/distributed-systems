package org.asidorkin.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
//@EnableCircuitBreaker
public class ProductApplication {

//    @Bean
//    @LoadBalanced
//    public WebClient.Builder getWebClientBuilder(){
//        return WebClient.builder();
//    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}

//Product application: returns product data to end-clients.
// The application exposes REST API for retrieving available products data
// by ‘uniq_id’ and by ‘sku’ (multiple products are returned).
// The REST service makes REST call to catalog application to get product data by ‘uniq_id’ or by ‘sku’,
// and make a call to the inventory application to get product availability and filter out only available product before returning.
