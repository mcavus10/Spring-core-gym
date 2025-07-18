package com.gym.springcore.model;

import lombok.Data;

@Data
public class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
}
