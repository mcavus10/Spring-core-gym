package com.gym.springcore.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Data
@EqualsAndHashCode(callSuper = true)
public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
}
