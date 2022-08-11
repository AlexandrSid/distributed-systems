package org.asidorkin.product.services;

import org.asidorkin.product.dto.ItemsTransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

//TODO remove class
@Service
public class CatalogService {
    public ItemsTransferDTO getFallbackCatalogRequest(String uri) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "The requested server is unavailable, HTTP status 503. Catalog Service issue");
    }
}
