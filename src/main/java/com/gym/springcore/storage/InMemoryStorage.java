package com.gym.springcore.storage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.springcore.model.Trainee;
import com.gym.springcore.model.Trainer;
import com.gym.springcore.model.TrainingType;
import jakarta.annotation.PostConstruct; // Eğer bu import hata verirse 'javax.annotation.PostConstruct' olarak değiştir
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryStorage {
    private final Map<String, Map<Long, Object>> storage = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gym.data.initialization.file-path}")
    private Resource dataFile;

    @PostConstruct
    public void initialize() {
        objectMapper.registerModule(new JavaTimeModule());
        TypeReference<HashMap<String, List<Map<String, Object>>>> typeRef = new TypeReference<>() {};

        try (InputStream inputStream = dataFile.getInputStream()) {
            Map<String, List<Map<String, Object>>> dataMap = objectMapper.readValue(inputStream, typeRef);

            loadTrainingTypes(dataMap.get("trainingTypes"));
            loadTrainers(dataMap.get("trainers"));
            loadTrainees(dataMap.get("trainees"));

            System.out.println("Başlangıç verileri başarıyla yüklendi.");

        } catch (IOException e) {
            System.err.println("Başlangıç veri dosyası okunamadı: " + e.getMessage());
        }
    }

    private void loadTrainingTypes(List<Map<String, Object>> types) {
        if (types == null) return;
        Map<Long, Object> namespaceMap = storage.computeIfAbsent("trainingTypes", k -> new HashMap<>());
        for (Map<String, Object> data : types) {
            TrainingType type = new TrainingType();
            type.setTrainingTypeName((String) data.get("trainingTypeName"));

            Long id = generateNewId();
            namespaceMap.put(id, type);
        }
    }

    private void loadTrainees(List<Map<String, Object>> trainees) {
        if (trainees == null) return;
        Map<Long, Object> namespaceMap = storage.computeIfAbsent("trainees", k -> new HashMap<>());
        for (Map<String, Object> data : trainees) {
            Trainee trainee = new Trainee();
            Long id = generateNewId();
            trainee.setId(id);
            trainee.setFirstName((String) data.get("firstName"));
            trainee.setLastName((String) data.get("lastName"));
            trainee.setUsername((String) data.get("username"));
            trainee.setPassword((String) data.get("password"));
            trainee.setDateOfBirth(LocalDate.parse((String)data.get("dateOfBirth")));
            trainee.setAddress((String) data.get("address"));
            trainee.setActive((Boolean) data.get("isActive"));
            namespaceMap.put(id, trainee);
        }
    }

    private void loadTrainers(List<Map<String, Object>> trainers) {
        if (trainers == null) return;
        Map<Long, Object> namespaceMap = storage.computeIfAbsent("trainers", k -> new HashMap<>());
        for (Map<String, Object> data : trainers) {
            Trainer trainer = new Trainer();
            TrainingType specialization = findTrainingTypeByName((String) data.get("specializationName"));

            if (specialization != null) {
                Long id = generateNewId();
                trainer.setId(id);
                trainer.setFirstName((String) data.get("firstName"));
                trainer.setLastName((String) data.get("lastName"));
                trainer.setUsername((String) data.get("username"));
                trainer.setPassword((String) data.get("password"));
                trainer.setSpecialization(specialization);
                trainer.setActive((Boolean) data.get("isActive"));
                namespaceMap.put(id, trainer);
            }
        }
    }

    public Long generateNewId() {
        return idCounter.incrementAndGet();
    }

    public Map<String, Map<Long, Object>> getStorage() {
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