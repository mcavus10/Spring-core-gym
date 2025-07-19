package com.gym.springcore.service;

import com.gym.springcore.model.Training;
import java.time.LocalDate;

public interface TrainingService {
    Training createTraining(Long traineeId, Long trainerId, String trainingName, String trainingTypeName, LocalDate trainingDate, int duration);
    Training selectTraining(Long id);
}