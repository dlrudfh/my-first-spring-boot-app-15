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

    public void insertDog(Dog dog) { // 신규 강아지 등록
        dogRepository.insertDog(dog);
    }

    public List<Dog> getDogs() {
        return dogRepository.findAllDogs();
    }

    public List<Dog> getDogByName(String name) {
        if(dogRepository.findDogByName(name) == null) {
            throw new DogNotFoundException();
        }
        return dogRepository.findDogByName(name);
    }

    public List<Dog> getDogByOwnerName(String ownerName) {
        if(dogRepository.findDogByOwnerName(ownerName) == null) {
            throw new DogNotFoundException();
        }
        return dogRepository.findDogByOwnerName(ownerName);
    }

    public List<Dog> getDogByOwnerPhoneNumber(String ownerPhoneNumber) {
        if(dogRepository.findDogByOwnerPhoneNumber(ownerPhoneNumber) == null) {
            throw new DogNotFoundException();
        }
        return dogRepository.findDogByOwnerPhoneNumber(ownerPhoneNumber);
    }

    public List<Dog> getDogByNameOwnerNameOwnerPhoneNumber(String name, String ownerName,String ownerPhoneNumber) {
        if(dogRepository.findDogByNameOwnerNameOwnerPhoneNumber(name, ownerName, ownerPhoneNumber) == null) {
            throw new DogNotFoundException();
        }
        return dogRepository.findDogByNameOwnerNameOwnerPhoneNumber(name, ownerName, ownerPhoneNumber);
    }

    /*
    public List<Dog> getAllDogs() {
        return dogRepository.findAllDogs();
    }
    */

    public void updateDogs(String name, Dog dog) {  // 통째로 강아지 정보 수정
        if (dogRepository.findDog(name) == null) {
            throw new DogNotFoundException();
        }
        dogRepository.updateDogs(name, dog);
    }
  
    public void modifyDogsKind(String name, String kind) {  // 강아지 kind 정보 수정
        if (dogRepository.findDog(name) == null) {
            throw new DogNotFoundException();
        }
        dogRepository.modifyDogsKind(name, kind);
    }

    public void addMedicalRecords(String name, String records) {  // 강아지 진료기록 추가
        if (dogRepository.findDog(name) == null) {
            throw new DogNotFoundException();
        }
        dogRepository.addMedicalRecords(name, records);
    }

}

