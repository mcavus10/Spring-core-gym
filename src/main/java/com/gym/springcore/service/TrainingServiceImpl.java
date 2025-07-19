package com.gym.springcore.service;

import com.gym.springcore.model.Trainee;
import com.gym.springcore.model.Trainer;
import com.gym.springcore.model.Training;
import com.gym.springcore.model.TrainingType;
import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;
import com.gym.springcore.repository.TrainingDAO;
import com.gym.springcore.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class TrainingServiceImpl implements TrainingService {

    private TraineeDAO traineeDAO;
    private TrainerDAO trainerDAO;
    private TrainingDAO trainingDAO;
    private InMemoryStorage storage;

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO) { this.traineeDAO = traineeDAO; }
    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) { this.trainerDAO = trainerDAO; }
    @Autowired
    public void setTrainingDAO(TrainingDAO trainingDAO) { this.trainingDAO = trainingDAO; }
    @Autowired
    public void setStorage(InMemoryStorage storage) { this.storage = storage; }

    @Override
    public Training createTraining(Long traineeId, Long trainerId, String trainingName, String trainingTypeName, LocalDate trainingDate, int duration) {
        Trainee trainee = traineeDAO.findById(traineeId);
        Trainer trainer = trainerDAO.findById(trainerId);
        TrainingType trainingType = storage.findTrainingTypeByName(trainingTypeName);

        if (trainee == null || trainer == null || trainingType == null) {
            return null; // Gerekli nesnelerden biri bile yoksa, eğitim oluşturulamaz.
        }

        Training newTraining = new Training();
        newTraining.setTrainee(trainee);
        newTraining.setTrainer(trainer);
        newTraining.setTrainingName(trainingName);
        newTraining.setTrainingType(trainingType);
        newTraining.setTrainingDate(trainingDate);
        newTraining.setTrainingDuration(duration);

        return trainingDAO.save(newTraining);
    }

    @Override
    public Training selectTraining(Long id) {
        return trainingDAO.findById(id);
    }
}