package org.cnu.realcoding.repository;

import org.cnu.realcoding.domain.Dog;
import org.cnu.realcoding.exception.DogConflictException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

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
        if (mongoTemplate.exists(Query.query(Criteria.where("name").is(dog.getName())), Dog.class)) {
            Dog newDog = mongoTemplate.findOne(Query.query(Criteria.where("name").is(dog.getName())), Dog.class);
            if (newDog.getOwnerName().equals(dog.getOwnerName()))
                if(newDog.getOwnerPhoneNumber().equals(dog.getOwnerPhoneNumber()))
                    throw new DogConflictException();
        }
        mongoTemplate.insert(dog);
    }
  
    public List<Dog> findDogByName(String name) {
        Criteria criteria = new Criteria(name);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Dog.class, "name");
    }

    public List<Dog> findDogByOwnerName(String ownerName) {
        Criteria criteria = new Criteria(ownerName);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Dog.class, "ownerName");
    }

    public List<Dog> findDogByOwnerPhoneNumber(String ownerPhoneNumber) {
        Criteria criteria = new Criteria(ownerPhoneNumber);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Dog.class, "ownerPhoneNumber");
    }
    public List<Dog> findDogByNameOwnerNameOwnerPhoneNumber(String name, String ownerName, String ownerPhoneNumber) {
        Criteria criteria = new Criteria(name);
        criteria.is(ownerName);
        criteria.is(ownerPhoneNumber);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, Dog.class, "ownerPhoneNumber");
    }



    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
    }

    public void updateDogs(String name, Dog dog) {
        Query query = new Query(Criteria.where("name").is(name));
        Update update = new Update();
        update.set("name", dog.getName());
        update.set("kind", dog.getKind());
        update.set("ownerName", dog.getOwnerName());
        update.set("ownerPhoneNumber", dog.getOwnerPhoneNumber());
        update.set("medicalRecords", findDog(name).getMedicalRecords());
        mongoTemplate.updateFirst(query, update, Dog.class);

    }

    public void modifyDogsKind(String name, String kind) {
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("kind", kind),Dog.class);
    }

    public void addMedicalRecords(String name, String records) {
        List<String> medical_records = findDog(name).getMedicalRecords();
        medical_records.add(0, records);
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is(name)),
                Update.update("medicalRecords", medical_records),
                Dog.class);
    }
}