package org.cnu.realcoding.controller;

import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.service.DogManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class DogController {

    @Autowired
    private DogManagementService dogManagementService;

    @PostMapping("/dogs")
    @ResponseStatus(HttpStatus.CREATED)
    public void createDogs(@RequestBody  Dog dog) {
        dogManagementService.insertDog(dog);
    }

    /*
    //test
    @GetMapping("/dogs")
    public List<Dog> getAllDogs() {
        return dogManagementService.getDogs();
    }
    */

    // 등록된 강아지 조회
    @GetMapping("/dogs/by-name/{name}")
    public List<Dog> getDogByName(@PathVariable String name) {
        return dogManagementService.getDogByName(name);
    }

    @GetMapping("/dogs/by-ownerName/{ownerName}")
    public List<Dog> getDogByOwnerName(@PathVariable String ownerName) {
        return dogManagementService.getDogByOwnerName(ownerName);
    }

    @GetMapping("/dogs/by-ownerPhoneNumber/{ownerPhoneNumber}")
    public List<Dog> getDogByOwnerPhoneNumber(@PathVariable String ownerPhoneNumber) {
        return dogManagementService.getDogByOwnerPhoneNumber(ownerPhoneNumber);
    }

    // http://localhost:8080/dogs?name=ian&ownername=ian&owe
    @GetMapping("/dogs")
    public List<Dog> getDogByNameOwnerNameOwnerPhoneNumber(@RequestParam String name, @RequestParam String ownerName , @RequestParam String ownerPhoneNumber) {
        return dogManagementService.getDogByNameOwnerNameOwnerPhoneNumber(name, ownerName, ownerPhoneNumber);
    }

}
