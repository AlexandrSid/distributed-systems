package org.asidorkin.product;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ListOfIDsDTO;
import org.asidorkin.product.dto.ProductResponseDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.model.Item;
import org.asidorkin.product.services.FeignToCatalogService;
import org.asidorkin.product.services.FeignToInventoryService;
import org.asidorkin.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RefreshScope
public class ProductController {

    @Value("${filter.zeros}")
    boolean filterZeros;
    @Value("${request.address.sku}")
    String requestAddress4Sku;
    @Value("${request.address.id}")
    String requestAddress4Id;

    @Autowired
    ProductService productService;
    @Autowired
    FeignToCatalogService catalogService;
    @Autowired
    FeignToInventoryService inventoryService;

    //    @HystrixCommand(fallbackMethod = "getFallback"
//            , commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")}
////            , ignoreExceptions = {HystrixRuntimeException.class, HystrixTimeoutException.class}
//    )
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ProductResponseDTO availableById(@PathVariable String id) {

        ItemsTransferDTO requestedByIdItem =
                catalogService.getByUniqId(id);
        if (requestedByIdItem.getItems().get(0).getUniqId() == null) {
            return productService.EmptyItemAvailabilityDTO;
        }

        AvailabilityDTO idToAvailableValues =
                inventoryService.getAvailability(new ListOfIDsDTO(List.of(id)));
        return productService.constructAndReturnProductResponseDTO(requestedByIdItem, idToAvailableValues, filterZeros);
    }

    //    @HystrixCommand(fallbackMethod = "getFallback"
//            , commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "4000")}
////            , ignoreExceptions = {HystrixRuntimeException.class, HystrixTimeoutException.class}
//    )
    @RequestMapping(value = "/sku/{sku}", method = RequestMethod.GET)
    public ProductResponseDTO availableBySku(@PathVariable String sku) {

        ItemsTransferDTO requestedByIdItem =
                catalogService.getBySku(sku);
        //extract list of uniq ids of returned records to further use it in request items availability.
        List<String> iDs = requestedByIdItem.getItems().stream().map(Item::getUniqId).collect(Collectors.toList());

        if (iDs.size() == 0) {
            return productService.EmptyItemAvailabilityDTO;
        }

        AvailabilityDTO idToAvailableValues =
                inventoryService.getAvailability(new ListOfIDsDTO(iDs));
        return productService.constructAndReturnProductResponseDTO(requestedByIdItem, idToAvailableValues, filterZeros);

    }

    @RequestMapping(value = "/string/sku/{sku}", method = RequestMethod.GET)
    public String stringBySku(@PathVariable String sku) {
        ProductResponseDTO itemAvailabilityDTO = availableBySku(sku);
        return itemAvailabilityDTO.makeSimpleRepresentation();
    }

    @RequestMapping(value = "/string/id/{id}", method = RequestMethod.GET)
    public String stringById(@PathVariable String id) {
        ProductResponseDTO itemAvailabilityDTO = availableById(id);
        return itemAvailabilityDTO.makeSimpleRepresentation();
    }

    public ProductResponseDTO getFallback(String string, Throwable e) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage() + "The requested server is unavailable, HTTP status 503.");
    }

}
