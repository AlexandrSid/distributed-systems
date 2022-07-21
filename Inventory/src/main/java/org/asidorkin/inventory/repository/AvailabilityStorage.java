package org.asidorkin.inventory.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AvailabilityStorage {
    Map<String,Integer> getAvailability(List<String> uniq_ids);
}
