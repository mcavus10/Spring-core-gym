// TrainerServiceImpl.java'nın doğru hali

package com.gym.springcore.service;

import com.gym.springcore.model.Trainer;
import com.gym.springcore.model.TrainingType;
import com.gym.springcore.repository.TraineeDAO;
import com.gym.springcore.repository.TrainerDAO;
import com.gym.springcore.service.util.CredentialsUtil;
import com.gym.springcore.storage.InMemoryStorage; // Bu import gerekli
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TraineeDAO traineeDAO;
    private TrainerDAO trainerDAO;
    private InMemoryStorage storage; // Depoya doğrudan erişim için

    @Autowired
    public void setTraineeDAO(TraineeDAO traineeDAO) { this.traineeDAO = traineeDAO; }

    @Autowired
    public void setTrainerDAO(TrainerDAO trainerDAO) { this.trainerDAO = trainerDAO; }

    @Autowired
    public void setStorage(InMemoryStorage storage) { this.storage = storage; }


    @Override
    public Trainer createTrainerProfile(String firstName, String lastName, String specializationName) {
        // 1. Gelen isme göre uzmanlık alanını depodan bul.
        TrainingType specialization = storage.findTrainingTypeByName(specializationName);
        if (specialization == null) {
            System.err.println("HATA: Geçersiz uzmanlık alanı adı: " + specializationName);
            return null; // Veya bir hata fırlatılabilir.
        }

        // 2. Ortak yardımcı sınıfı kullanarak kullanıcı adı ve şifre üret.
        String username = CredentialsUtil.generateUsername(firstName, lastName, traineeDAO, trainerDAO);
        String password = CredentialsUtil.generateRandomPassword();

        // 3. Yeni Trainer nesnesini oluştur ve doldur.
        Trainer newTrainer = new Trainer();
        newTrainer.setFirstName(firstName);
        newTrainer.setLastName(lastName);
        newTrainer.setUsername(username);
        newTrainer.setPassword(password);
        newTrainer.setSpecialization(specialization); // Bulunan nesneyi ata.
        newTrainer.setActive(true);

        // 4. DAO ile kaydet ve sonucu döndür.
        return trainerDAO.save(newTrainer);
    }

    @Override
    public Trainer selectTrainerProfile(Long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public Trainer updateTrainerProfile(Long id, String firstName, String lastName, String specializationName, boolean isActive) {
        Trainer trainer = trainerDAO.findById(id);
        TrainingType specialization = storage.findTrainingTypeByName(specializationName);

        if (trainer != null && specialization != null) {
            trainer.setFirstName(firstName);
            trainer.setLastName(lastName);
            trainer.setSpecialization(specialization);
            trainer.setActive(isActive);
            return trainerDAO.update(trainer);
        }

        return null;
    }
}