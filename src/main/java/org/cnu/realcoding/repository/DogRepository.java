package org.cnu.realcoding.repository;

import org.cnu.realcoding.domain.Dog;
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
        mongoTemplate.insert(dog);
    }

    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
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