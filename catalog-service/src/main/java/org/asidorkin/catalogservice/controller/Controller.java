package org.asidorkin.catalogservice.controller;

import lombok.SneakyThrows;
import org.asidorkin.catalogservice.dto.ItemsTransferDTO;
import org.asidorkin.catalogservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private ItemRepository repository;

    @Value("${sleep.id:0}")
    private int sleepId;
    @Value("${sleep.sku:0}")
    private int sleepSku;

    @SneakyThrows
    @GetMapping("/id/{id}")
    public ItemsTransferDTO getByUniqId(@PathVariable("id") String uniq_id) {
        Thread.sleep(sleepId);
        return new ItemsTransferDTO(List.of(repository.getItemByUniqId(uniq_id)));
    }

    @SneakyThrows
    @GetMapping("/sku/{sku}")
    public ItemsTransferDTO getBySku(@PathVariable("sku") String sku) {
        Thread.sleep(sleepSku);
        return new ItemsTransferDTO(repository.getItemsBySku(sku));
    }

}

