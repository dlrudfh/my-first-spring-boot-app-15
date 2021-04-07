package org.cnu.realcoding.repository;

import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.exception.DogConflictException;
import org.cnu.realcoding.exception.DogNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DogRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Dog findDog(String name) {
        return mongoTemplate
                .findOne(
                        Query.query(Criteria.where("name").is(name)),
                        Dog.class
                );
    }

    public void insertDog(Dog dog) {
        if(isDupplicated(dog))
            throw new DogConflictException();
        else
        mongoTemplate.insert(dog);
    }


    public List<Dog> findDogByName(String name) {
        return mongoTemplate
                .find(
                        Query.query(Criteria.where("name").is(name)),
                        Dog.class
                );
    }

    public List<Dog> findDogByOwnerName(String ownerName) {
        return mongoTemplate
                .find(
                        Query.query(Criteria.where("ownerName").is(ownerName)),
                        Dog.class
                );
    }

    public List<Dog> findDogByOwnerPhoneNumber(String ownerPhoneNumber) {
        return mongoTemplate
                .find(
                        Query.query(Criteria.where("ownerPhoneNumber").is(ownerPhoneNumber)),
                        Dog.class
                );
    }

    public Dog findDogByNameOwnerNameOwnerPhoneNumber(String name, String ownerName, String ownerPhoneNumber) {

        Query query = new Query();
        Criteria criteria1 = new Criteria();
        criteria1.where("name").is(name);
        criteria1.and("ownerName").is(ownerName);
        criteria1.and("ownerPhoneNumber").is(ownerPhoneNumber);
        query.addCriteria(criteria1);
        return mongoTemplate.findOne(query, Dog.class);
    }


    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
    }

    public boolean isDupplicated(Dog dog) {
        if (mongoTemplate.exists(Query.query(Criteria.where("name").is(dog.getName())), Dog.class)) {

            List<Dog> dogs = mongoTemplate.find(Query.query(Criteria.where("name").is(dog.getName())), Dog.class);
            for (int i = 0; i < dogs.size(); i++) {
                if (dogs.get(i).getOwnerName().equals(dog.getOwnerName())) {
                    List<Dog> dogs2 = mongoTemplate.find(Query.query(Criteria.where("ownerName").is(dog.getOwnerName())), Dog.class);
                    for (int j = 0; j < dogs2.size(); j++) {
                        if (dogs2.get(i).getOwnerPhoneNumber().equals(dog.getOwnerPhoneNumber())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    public void updateDogs(String name, String ownername, String ownerphonenumber,Dog dog) {
        if (isDupplicated(dog)) {
            throw new DogConflictException();
        } else {
            Criteria criteria = new Criteria();
            criteria.where("name").is(name);
            criteria.and("ownerName").is(ownername);
            criteria.and("ownerPhoneNumber").is(ownerphonenumber);
            Query query = new Query();
            query.addCriteria(criteria);

            Update update = new Update();
            update.set("name", dog.getName());
            update.set("kind", dog.getKind());
            update.set("ownerName", dog.getOwnerName());
            update.set("ownerPhoneNumber", dog.getOwnerPhoneNumber());
            update.set("medicalRecords", findDog(name).getMedicalRecords());
            mongoTemplate.updateFirst(query, update, Dog.class);
        }
    }


    public void modifyDogsKind(String name, String kind) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("kind", kind), Dog.class);
    }

    public void addMedicalRecords(String name, String records) {
        List<String> medical_records = findDog(name).getMedicalRecords();
        medical_records.add(0, records);
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("medicalRecords", medical_records),
                Dog.class);
    }
}