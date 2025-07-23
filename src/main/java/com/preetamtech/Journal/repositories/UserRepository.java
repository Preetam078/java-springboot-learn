package com.preetamtech.Journal.repositories;

import com.preetamtech.Journal.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    //Because of Indexing through userName = we can search through username
    User findByUsername(String username);
}
