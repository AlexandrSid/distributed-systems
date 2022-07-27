package org.asidorkin.product;

import org.asidorkin.product.dto.AvailabilityDTO;
import org.asidorkin.product.dto.ItemsTransferDTO;
import org.asidorkin.product.dto.ListOfIDsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ServiceFlux {

//    @Autowired
//    WebClient.Builder webClientBuilder;
//
//    private Mono<ItemsTransferDTO> getItemsFromCatalog(String uri) {
//        return webClientBuilder.build()
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(ItemsTransferDTO.class);
//    }
//
//    private Mono<AvailabilityDTO> getAvailabilityFromInventory(List<String> iDs) {
//        return webClientBuilder.build()
//                .post()
//                .uri("https://inventory-service/availability")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(new ListOfIDsDTO(iDs)), ListOfIDsDTO.class)
//                .retrieve()
//                .bodyToMono(AvailabilityDTO.class);
//    }
}
