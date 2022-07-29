package org.asidorkin.product.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ListOfIDsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InventoryService {

    @Value("${request.address.availability}")
    String requestAddress4Availability;

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackInventoryRequest", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    },
            threadPoolKey = "idRequestKey",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "10"),
                    @HystrixProperty(name = "maxQueueSize", value = "5")
            })
    public AvailabilityDTO getAvailabilityFromInventory(List<String> iDs) {
        return restTemplate.postForObject(requestAddress4Availability, new ListOfIDsDTO(iDs), AvailabilityDTO.class);
    }

    public AvailabilityDTO getFallbackInventoryRequest(List<String> iDs) {
//        return null;
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The requested server is unavailable, HTTP status 503. Inventory service issue");
    }
}
