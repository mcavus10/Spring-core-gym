package com.gym.springcore.service;

import com.gym.springcore.model.Trainer;

public interface TrainerService {
    Trainer createTrainer(String name, String username, String password, String specialization);
    Trainer selectTrainerProfile(Long id);

    Trainer updateTrainerProfile(Long id, String firstName, String lastName, Long trainingTypeId, boolean isActive);

}
