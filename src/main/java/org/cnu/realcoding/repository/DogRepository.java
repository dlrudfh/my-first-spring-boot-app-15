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

    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
    }

}