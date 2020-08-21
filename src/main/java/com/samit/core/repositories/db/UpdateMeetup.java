package com.samit.core.repositories.db;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class UpdateMeetup {

    private MongoCollection<Meetup> mongoCollection;

    @Inject
    public UpdateMeetup(MongoDatabase mongoDatabase) {
        this.mongoCollection = mongoDatabase.getCollection(Meetup.COLLECTION_NAME, Meetup.class);;
    }

    public void update(Meetup meetup){
        try {
            mongoCollection.updateOne(eq("date", meetup.getDate()), combine(set("attendees", meetup.getAttendees())));
        }catch (Exception ex){
            System.out.println("falló el insert");
        }
    }
}
