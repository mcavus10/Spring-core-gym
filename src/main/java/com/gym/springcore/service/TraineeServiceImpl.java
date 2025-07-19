package com.gym.springcore.service;

import com.gym.springcore.model.Trainee;
import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;
import com.gym.springcore.service.util.CredentialsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        String password = CredentialsUtil.generateRandomPassword();
        String username = CredentialsUtil.generateUsername(firstName, lastName, traineeDAO, trainerDAO);

        Trainee newTrainee = new Trainee();

        newTrainee.setFirstName(firstName);
        newTrainee.setLastName(lastName);
        newTrainee.setDateOfBirth(dateOfBirth);
        newTrainee.setAddress(address);
        newTrainee.setUsername(username);
        newTrainee.setPassword(password);
        newTrainee.setActive(true);

        return traineeDAO.save(newTrainee);
    }

    @Override
    public Trainee selectTraineeProfile(Long id) {

        return traineeDAO.findById(id);
    }

    @Override
    public Trainee updateTraineeProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String address, boolean isActive) {
        Trainee trainee = traineeDAO.findById(id);
        if (trainee != null) {
            trainee.setFirstName(firstName);
            trainee.setLastName(lastName);
            trainee.setDateOfBirth(dateOfBirth);
            trainee.setAddress(address);
            trainee.setActive(isActive);
            return traineeDAO.update(trainee);
        }
        return null;

    }

    @Override
    public void deleteTraineeProfile(Long id) {
        traineeDAO.delete(id);
    }

}