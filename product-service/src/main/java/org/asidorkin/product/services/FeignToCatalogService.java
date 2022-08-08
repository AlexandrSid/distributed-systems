package org.asidorkin.product.services;

import org.asidorkin.product.dto.ItemsTransferDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service")
public interface FeignToCatalogService {
    @GetMapping("/id/{id}")
    ItemsTransferDTO getByUniqId(@PathVariable("id") String uniq_id);

    @GetMapping("/sku/{sku}")
    ItemsTransferDTO getBySku(@PathVariable("sku") String sku);
}
