package com.gym.springcore.repository;

import com.gym.springcore.model.Training;
import com.gym.springcore.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Repository
public class TrainingDAO {
    private InMemoryStorage storage;
    @Autowired
    public void setStorage(InMemoryStorage storage){this.storage=storage;}

    public Training save(Training training){
        Long newId = storage.generateNewId();
        training.setId(newId);
        Map<Long,Object> trainingMap = storage.getStorage().computeIfAbsent("trainings", k->new HashMap<>());
        trainingMap.put(newId,training);
        return training;
    }

    public Training findById(Long id){
        Map<Long, Object> trainingMap = storage.getStorage().get("trainings");
        if(trainingMap==null){
            return null;
        }
        Object foundObject=trainingMap.get(id);
        if(foundObject!=null){
            return (Training) foundObject;
        }
        return null;
    }

    public List<Training> findAll(){
        Map<Long,Object> trainingMap = storage.getStorage().get("trainings");
        if(trainingMap==null){
            return new ArrayList<>();
        }
        return trainingMap.values().stream()
                .map(obj->(Training)obj)
                .collect(Collectors.toList());
    }


}
