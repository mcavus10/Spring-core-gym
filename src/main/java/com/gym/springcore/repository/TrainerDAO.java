package com.gym.springcore.repository;

import com.gym.springcore.model.Trainer;
import com.gym.springcore.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TrainerDAO {
    private InMemoryStorage storage;
    @Autowired
    public void setStorage(InMemoryStorage storage){this.storage=storage;}

    public Trainer save(Trainer trainer){
        Long newId = storage.generateNewId();
        trainer.setId(newId);
        Map<Long,Object> trainerMap = storage.getStorage().computeIfAbsent("trainers", k->new HashMap<>());
        trainerMap.put(newId,trainer);
        return trainer;
    }

    public Trainer findById(Long id){
        Map<Long, Object> trainerMap = storage.getStorage().get("trainers");
        if(trainerMap==null){
            return null;
        }
        Object foundObject=trainerMap.get(id);
        if(foundObject!=null){
            return (Trainer) foundObject;
        }
        return null;
    }

    public List<Trainer> findAll(){
        Map<Long,Object> trainerMap = storage.getStorage().get("trainers");
        if(trainerMap==null){
            return new ArrayList<>();
        }
        return trainerMap.values().stream()
                .map(obj->(Trainer)obj)
                .collect(Collectors.toList());
    }

    public Trainer update(Trainer Trainer){
        if(Trainer ==null || Trainer.getId()==null){
            return null;
        }
        Map<Long,Object> trainerMap = storage.getStorage().get("trainers");
        if(trainerMap==null || !trainerMap.containsKey(Trainer.getId())){
            return null;
        }
        trainerMap.put(Trainer.getId(),Trainer);
        return Trainer;
    }


}
