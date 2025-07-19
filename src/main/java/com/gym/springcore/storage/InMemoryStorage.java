package com.gym.springcore.storage;

import com.gym.springcore.model.TrainingType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {
    private final Map<String,Map<Long, Object>> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Long generateNewId(){
        return idCounter.incrementAndGet();
    }

    public Map<String,Map<Long,Object>> getStorage(){
        return storage;
    }
    public TrainingType findTrainingTypeByName(String name) {
        Map<Long, Object> trainingTypesMap = storage.get("trainingTypes");
        if (trainingTypesMap == null || name == null) {
            return null;
        }
        for (Object obj : trainingTypesMap.values()) {
            TrainingType type = (TrainingType) obj;
            if (name.equalsIgnoreCase(type.getTrainingTypeName())) {
                return type;
            }
        }
        return null;
    }
}
