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
            List<Dog> dogs = mongoTemplate.find(Query.query(Criteria.where("name").is(dog.getName())), Dog.class);
            for(int i = 0; i < dogs.size(); i++){
                if(dogs.get(i).getOwnerName().equals(dog.getOwnerName())){
                    List<Dog> dogs2 = mongoTemplate.find(Query.query(Criteria.where("ownerName").is(dog.getOwnerName())), Dog.class);
                    for(int j = 0; j < dogs2.size(); j++) {
                        if(dogs2.get(i).getOwnerPhoneNumber().equals(dog.getOwnerPhoneNumber())){
                            throw new DogConflictException();
                        }
                    }
                }
            }
        }
        mongoTemplate.insert(dog);
    }

    public List<Dog> findAllDogs() {
        return mongoTemplate.findAll(Dog.class);
    }

}