package org.asidorkin.catalogservice.controller;

import org.asidorkin.catalogservice.ItemService;
import org.asidorkin.catalogservice.dto.ItemsTransferDTO;
import org.asidorkin.catalogservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    private ItemRepository repository;
    @Autowired
    private ItemService service;

    @GetMapping("/initRepo")
    public String initRepo() {
        service.initRepo();
        return "repository initialized";
    }

    @GetMapping("/id/{id}")
    public ItemsTransferDTO getByUniqId(@PathVariable("id") String uniq_id) {
        return new ItemsTransferDTO(List.of(repository.getItemByUniqId(uniq_id)));
    }

    @GetMapping("/sku/{sku}")
    public ItemsTransferDTO getBySku(@PathVariable("sku") String sku) {
        return new ItemsTransferDTO(repository.getItemsBySku(sku));
    }

}
