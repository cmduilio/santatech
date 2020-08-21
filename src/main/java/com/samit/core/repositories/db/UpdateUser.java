package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.User;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class UpdateUser {

    private MongoCollection<User> mongoCollection;

    @Inject
    public UpdateUser(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(User.COLLECTION_NAME, User.class);;
    }

    public void update(User user){
        try {
            mongoCollection.updateOne(eq("name", user.getName()), combine(set("meetups", user.getMeetups())));
        }catch (Exception ex){
            System.out.println("falló el insert");
        }
    }
}
