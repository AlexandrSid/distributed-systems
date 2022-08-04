package org.asidorkin.inventory.controller;

import lombok.SneakyThrows;
import org.asidorkin.inventory.dto.AvailabilityDTO;
import org.asidorkin.inventory.dto.ListOfIDsDTO;
import org.asidorkin.inventory.repository.AvailabilityStorage;
import org.asidorkin.inventory.repository.InMemoryAvailabilityStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/availability")
public class Controller {

    AvailabilityStorage storage = new InMemoryAvailabilityStorage();

//    @RequestMapping(method = RequestMethod.GET, value = "/hello")
//    public ListOfIDsDTO hello(){
//        return new ListOfIDsDTO(List.of("a", "b", "c"));
//
//    }

//    @Value("${delay}")
//    int sleepTime;

    @SneakyThrows
    @RequestMapping(method = RequestMethod.POST)
    public AvailabilityDTO getAvailability(@RequestBody ListOfIDsDTO values)
    {
        Thread.sleep(300);
        return new AvailabilityDTO(storage.getAvailability(values.getListOfIDs()));
    }

}
