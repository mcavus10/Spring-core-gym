package com.gym.springcore.repository;

import com.gym.springcore.model.Trainee;
import com.gym.springcore.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TraineeDAO {
    private InMemoryStorage storage;
    @Autowired
    public void setStorage(InMemoryStorage storage){this.storage=storage;}

    public Trainee save(Trainee trainee){
        Long newId = storage.generateNewId();
        trainee.setId(newId);
        Map<Long,Object> traineeMap = storage.getStorage().computeIfAbsent("trainees",k->new HashMap<>());
        traineeMap.put(newId,trainee);
        return trainee;
    }

    public Trainee findById(Long id){
        Map<Long, Object> traineeMap = storage.getStorage().get("trainees");
        if(traineeMap==null){
            return null;
        }
        Object foundObject=traineeMap.get(id);
        if(foundObject!=null){
            return (Trainee) foundObject;
        }
        return null;
    }

    public List<Trainee> findAll(){
        Map<Long,Object> traineeMap = storage.getStorage().get("trainees");
        if(traineeMap==null){
            return new ArrayList<>();
        }
        return traineeMap.values().stream()
                .map(obj->(Trainee)obj)
                .collect(Collectors.toList());
    }

    public Trainee update(Trainee trainee){
        if(trainee ==null || trainee.getId()==null){
            return null;
        }
        Map<Long,Object> traineeMap = storage.getStorage().get("trainees");
        if(traineeMap==null || !traineeMap.containsKey(trainee.getId())){
            return null;
        }
        traineeMap.put(trainee.getId(),trainee);
        return trainee;
    }

    public void delete(Long id){
        if (id==null){
            return;
        }
        Map<Long,Object> traineeMap = storage.getStorage().get("trainees");
        if(traineeMap!=null && traineeMap.containsKey(id)){
            traineeMap.remove(id);
    }

}

}

