package com.gym.springcore.service;

import com.gym.springcore.model.Trainer;
import com.gym.springcore.model.TrainingType;
import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {
    private TrainerDAO trainerDAO;
    private TraineeDAO traineeDAO;
    private TrainingType trainingType;
    @Override
    public Trainer createTrainer(String name, String username, String password, String specialization) {
        return null;
    }

    @Override
    public Trainer selectTrainerProfile(Long id) {
        return null;
    }

    @Override
    public Trainer updateTrainerProfile(Long id, String firstName, String lastName, Long trainingTypeId, boolean isActive) {
        return null;
    }
}
