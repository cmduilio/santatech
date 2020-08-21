package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.Meetup;

import static com.mongodb.client.model.Filters.eq;

public class GetMeetup {

    private MongoCollection<Meetup> mongoCollection;

    @Inject
    public GetMeetup(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(Meetup.COLLECTION_NAME, Meetup.class);;
    }

    public Meetup get(String date){
        try {
            return mongoCollection.find(eq("date", date)).first();
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
