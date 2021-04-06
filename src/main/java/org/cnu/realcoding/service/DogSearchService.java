package org.cnu.realcoding.service;


import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.exception.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DogSearchService {

    @Autowired
    private DogManagementService dogManagementService;

    public List<Dog> getDogByName(String name) {  // 강아지 이름이 일치하는지 찾는 함수
        List<Dog> temp = new ArrayList<>();
        for (Dog dog : dogManagementService.getDogs()) {
            if (dog.getName().equals(name)) {
                temp.add(dog);
            }
        }
        if(temp.size() > 0) return temp;
        else throw new DogNotFoundException();
    }

    public List<Dog> getDogByOwnerName(String ownerName) {  // 소유주 이름이 일치하는지 찾는 함수
        List<Dog> temp = new ArrayList<>();
        for (Dog dog : dogManagementService.getDogs()) {
            if (dog.getOwnerName().equals(ownerName)) {
                temp.add(dog);
            }
        }
        if(temp.size() > 0) return temp;
        else throw new DogNotFoundException();
    }

    public List<Dog> getDogByOwnerPhoneNumber(String ownerPhoneNumber) {  // 소유주 번호가 일치하는지 찾는 함수
        List<Dog> temp = new ArrayList<>();
        for (Dog dog : dogManagementService.getDogs()) {
            if (dog.getOwnerPhoneNumber().equals(ownerPhoneNumber)) {
                temp.add(dog);
            }
        }
        if(temp.size() > 0) return temp;
        else throw new DogNotFoundException();
    }

    public List<Dog> getDogByNameOwnerNameOwnerPhoneNumber(String name, String ownerName , String ownerPhoneNumber) {  // 세가지 패러미터가 일치하는지 찾는 함수
        List<Dog> temp = new ArrayList<>();
        for (Dog dog : dogManagementService.getDogs()) {
            if (dog.getName().equals(name) &&
                    dog.getOwnerName().equals(ownerName) &&
                    dog.getOwnerPhoneNumber().equals(ownerPhoneNumber)) {
                temp.add(dog);
            }
        }
        if(temp.size() > 0) return temp;
        else throw new DogNotFoundException();
    }

}
