package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.Meetup;

public class CreateMeetup {

    private MongoCollection<Meetup> mongoCollection;

    @Inject
    public CreateMeetup(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(Meetup.COLLECTION_NAME, Meetup.class);;
    }

    public void save(Meetup meetup){
        try {
            mongoCollection.insertOne(meetup);
        }catch (Exception ex){
            System.out.println("falló el insert");
        }
    }
}
