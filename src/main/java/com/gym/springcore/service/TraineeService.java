package com.gym.springcore.service;

import com.gym.springcore.model.Trainee;
import java.time.LocalDate;

public interface TraineeService {

    Trainee createTraineeProfile(String firstName, String lastName, LocalDate dateOfBirth, String address);

    Trainee selectTraineeProfile(Long id);

    Trainee updateTraineeProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive);

    void deleteTraineeProfile(Long id);
}