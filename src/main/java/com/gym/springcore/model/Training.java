package com.gym.springcore.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Training {
    private Long id;
    private Trainee trainee;
    private Trainer trainer;
    private String trainingName;
    private TrainingType trainingType;
    private int trainingDuration; //minutes
    private LocalDate trainingDate;
}
