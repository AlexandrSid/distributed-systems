package org.asidorkin.inventory.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Repository
public class InMemoryAvailabilityStorage implements AvailabilityStorage {

    public static final int maxRandomImMemoryStorageCapacityPerId = 1000;

    Map<String, Integer> availableMap = new HashMap<>();//in-memory storage
    Random random = new Random();

    @Override
    public Map<String, Integer> getAvailability(List<String> uniq_ids) {

        Map<String, Integer> result = new HashMap<>();

        for (String id : new HashSet<>(uniq_ids)) {//turns into Set to prevent duplications
            if (availableMap.containsKey(id)) {
                result.put(id, availableMap.get(id));
            } else {
                int value = random.nextInt(0, maxRandomImMemoryStorageCapacityPerId);
                value = value - maxRandomImMemoryStorageCapacityPerId / 3;//to increase number of zeros to 30%
                if (value < 0) value = 0;
                result.put(id, value);
            }
        }
        availableMap.putAll(result);
        return result;
    }
}
