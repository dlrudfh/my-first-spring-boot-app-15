package org.cnu.realcoding.service;

import lombok.Getter;
import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.exception.DogConflictException;
import org.cnu.realcoding.exception.DogNotFoundException;
import org.cnu.realcoding.repository.DogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogManagementService {

    @Autowired
    private DogRepository dogRepository;

    public void insertDog(Dog dog) {
        dogRepository.insertDog(dog);
    }

    public List<Dog> getAllDogs() {
        return dogRepository.findAllDogs();
    }

    public void modifyDogsKind(String name, String kind) {
        if (dogRepository.findDog(name) == null) {
            throw new DogNotFoundException();
        }
        dogRepository.modifyDogsKind(name, kind);
    }

    public void addMedicalRecords(String name, String records) {
        if (dogRepository.findDog(name) == null) {
            throw new DogNotFoundException();
        }
        dogRepository.addMedicalRecords(name, records);
    }


}
