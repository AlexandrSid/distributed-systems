package org.asidorkin.product;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.SneakyThrows;
import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ItemAvailabilityDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.dto.ListOfIDsDTO;
import org.asidorkin.product.model.Item;
import org.asidorkin.product.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    RestTemplate restTemplate;

    @Value("${filter.zeros}")
    boolean filterZeros;

    @SneakyThrows
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
//    @HystrixCommand(fallbackMethod = "getFallback")
    public ItemAvailabilityDTO availableById(@PathVariable String id) {

        ItemsTransferDTO requestedByIdItem = getItemsFromCatalog("http://catalog-service/id/" + id);

        if (requestedByIdItem.getItems().get(0).equals(null)) {
            return Utils.EmptyItemAvailabilityDTO;
        }

        AvailabilityDTO idToAvailableValues = getAvailabilityFromInventory(List.of(id));

        return Utils.constructAndReturnItemAvailabilityDTO(requestedByIdItem, idToAvailableValues, filterZeros);

    }

    @SneakyThrows
    @RequestMapping(value = "/sku/{sku}", method = RequestMethod.GET)
//    @HystrixCommand(fallbackMethod = "getFallback")
    public ItemAvailabilityDTO availableBySku(@PathVariable String sku) {

        ItemsTransferDTO requestedByIdItem = getItemsFromCatalog("http://localhost:8081/sku/" + sku);

        List<String> iDs = requestedByIdItem.getItems().stream().map(Item::getUniqId).collect(Collectors.toList());

        AvailabilityDTO idToAvailableValues = getAvailabilityFromInventory(iDs);

        return Utils.constructAndReturnItemAvailabilityDTO(requestedByIdItem, idToAvailableValues, filterZeros);

    }

    @RequestMapping(value = "/string/sku/{sku}", method = RequestMethod.GET)
    public String stringBySku(@PathVariable String sku) {

        ItemAvailabilityDTO itemAvailabilityDTO = availableBySku(sku);

        return itemAvailabilityDTO.getItemsAvailable().entrySet()
                .stream()
                .map(e -> "id = " + e.getKey().getUniqId() + "; " +
                        "sku = " + e.getKey().getSku() + "; " +
                        "available number = " + e.getValue() + "<br />")
                .reduce("Items: <br />", (subtotal, item) -> subtotal + item);
    }

    @RequestMapping(value = "/string/id/{id}", method = RequestMethod.GET)
    public String stringById(@PathVariable String id) {
        ItemAvailabilityDTO itemAvailabilityDTO = availableById(id);
        return itemAvailabilityDTO.getItemsAvailable().entrySet()
                .stream()
                .map(e -> "id = " + e.getKey().getUniqId() + "; " +
                        "sku = " + e.getKey().getSku() + "; " +
                        "available number = " + e.getValue() + "<br />")
                .reduce("Item (should be one only): <br />", (subtotal, item) -> subtotal + item);
    }

    public ItemAvailabilityDTO getFallback(@PathVariable String sku) {
        return new ItemAvailabilityDTO(new HashMap<>());
    }


    private ItemsTransferDTO getItemsFromCatalog(String uri) {
        return restTemplate.getForObject(uri, ItemsTransferDTO.class);

    }


    private AvailabilityDTO getAvailabilityFromInventory(List<String> iDs) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Map<String, Object> map = Map.of("iDs", new ListOfIDsDTO(iDs));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        return restTemplate.postForObject("http://inventory-service/availability", entity, AvailabilityDTO.class);
    }
}
