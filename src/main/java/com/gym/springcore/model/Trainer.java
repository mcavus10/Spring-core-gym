package com.gym.springcore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = true)
public class Trainer extends User {
    private TrainingType specialization;
}
