package org.cnu.realcoding.service;

import lombok.Getter;
import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.exception.DogConflictException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogManagementService {

    @Getter
    private List<Dog> dogs = new ArrayList<>();

    public void insertDog(Dog dog) { // 신규 강아지 등록
        for (Dog value : dogs) {
            if (value.getName().equals(dog.getName())) {
                if (value.getOwnerName().equals(dog.getOwnerName()))
                    if (value.getOwnerPhoneNumber().equals(dog.getOwnerPhoneNumber()))
                        throw new DogConflictException();
            }
        }
        dogs.add(dog);
    }
}
