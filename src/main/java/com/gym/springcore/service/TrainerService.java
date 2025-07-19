package com.gym.springcore.service;

import com.gym.springcore.model.Trainer;

public interface TrainerService {

    Trainer createTrainerProfile(String firstName, String lastName, String specializationName);

    Trainer selectTrainerProfile(Long id);

    Trainer updateTrainerProfile(Long id, String firstName, String lastName, String specializationName, boolean isActive);
}