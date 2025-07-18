package com.gym.springcore.storage;

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
}
