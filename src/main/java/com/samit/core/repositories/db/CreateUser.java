package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.User;

public class CreateUser {

    private MongoCollection<User> mongoCollection;

    @Inject
    public CreateUser(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(User.COLLECTION_NAME, User.class);;
    }

    public void save(User user){
        try {
            mongoCollection.insertOne(user);
        }catch (Exception ex){
            System.out.println("falló el insert");
        }
    }
}
