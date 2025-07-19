package com.gym.springcore.service.util;

import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CredentialsUtil {

    public static String generateRandomPassword(){
        Stream<Character> allCharacters = Stream.concat(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().mapToObj(c->(char)c),
                Stream.concat("abcdefghijklmnopqrstuvwxyz".chars().mapToObj(c->(char)c),
                        "0123456789".chars().mapToObj(c->(char)c)));
        List<Character> characterList = allCharacters.collect(Collectors.toList());
        Collections.shuffle(characterList,new SecureRandom());
        return characterList.stream().limit(10).map(String::valueOf).collect(Collectors.joining());
    }
    public static boolean usernameExists(String username, TraineeDAO traineeDAO, TrainerDAO trainerDAO){
        boolean traineeExists= traineeDAO.findAll().stream()
                .anyMatch(trainee -> trainee.getUsername().equalsIgnoreCase(username));
        boolean trainerExists = trainerDAO.findAll().stream()
                .anyMatch(trainer -> trainer.getUsername().equalsIgnoreCase(username));

        return traineeExists || trainerExists;
    }
    public static String generateUsername(String firstName, String lastName, TraineeDAO traineeDAO, TrainerDAO trainerDAO){
        String baseUsername=firstName.toLowerCase()+"."+lastName.toLowerCase();
        String finalUsername=baseUsername;
        int counter=1;
        while(usernameExists(finalUsername,traineeDAO,trainerDAO)){
            finalUsername=baseUsername+counter;
            counter++;
        }
        return finalUsername;
    }
}
