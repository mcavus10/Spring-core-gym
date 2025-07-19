package com.gym.springcore.service;

import com.gym.springcore.model.Trainee;
import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TraineeServiceImpl implements TraineeService {

    private TraineeDAO traineeDAO;
    private TrainerDAO trainerDAO;

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }


    @Override
    public Trainee createTraineeProfile(String firstName, String lastName, LocalDate dateOfBirth, String address) {

        return null;
    }

    @Override
    public Trainee selectTraineeProfile(Long id) {

        return traineeDAO.findById(id);
    }

    @Override
    public Trainee updateTraineeProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {

        return null;
    }

    @Override
    public void deleteTraineeProfile(Long id) {
        .
    }
}