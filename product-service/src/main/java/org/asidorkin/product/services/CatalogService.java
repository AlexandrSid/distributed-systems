package org.asidorkin.product.services;

import org.asidorkin.product.dto.ItemsTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CatalogService {

    @Autowired
    RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "getFallbackCatalogRequest", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
//    },
//            threadPoolKey = "idRequestKey",
//            threadPoolProperties = {
//                    @HystrixProperty(name = "coreSize", value = "10"),
//                    @HystrixProperty(name = "maxQueueSize", value = "5")
//            })

    public ItemsTransferDTO getItemsFromCatalog(String uri) {
        return restTemplate.getForObject(uri, ItemsTransferDTO.class);
    }

    public ItemsTransferDTO getFallbackCatalogRequest(String uri) {
//        return null;
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The requested server is unavailable, HTTP status 503. Catalog Service issue");
    }
}
