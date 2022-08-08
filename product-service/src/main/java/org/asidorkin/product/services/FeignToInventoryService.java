package org.asidorkin.product.services;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ListOfIDsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service")
public interface FeignToInventoryService {

    @PostMapping("/availability")
    AvailabilityDTO getAvailability(@RequestBody ListOfIDsDTO values);

}
