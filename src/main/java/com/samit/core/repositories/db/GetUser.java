package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.User;

import static com.mongodb.client.model.Filters.eq;

public class GetUser {

    private MongoCollection<User> mongoCollection;

    @Inject
    public GetUser(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(User.COLLECTION_NAME, User.class);;
    }

    public User get(String name){
        try {
            return mongoCollection.find(eq("name", name)).first();
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
