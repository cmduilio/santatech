package com.samit.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.samit.configuration.routers.BeerRouter;
import com.samit.configuration.routers.MeetupRouter;
import com.samit.configuration.routers.UserRouter;
import com.samit.core.exceptions.DateNotInForecastException;
import com.samit.core.exceptions.UserAlreadyExistsException;
import com.samit.core.repositories.db.*;
import com.samit.core.repositories.rest.GetWeatherForecast;
import com.samit.entrypoints.CreateMeetupEntrypoint;
import com.samit.entrypoints.CreateUserEntrypoint;
import com.samit.entrypoints.JoinMeetupEntrypoint;
import com.samit.entrypoints.CheckinUserEntrypoint;
import org.apache.http.HttpStatus;
import org.bson.codecs.configuration.CodecRegistry;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import org.bson.codecs.pojo.PojoCodecProvider;
import spark.Request;
import spark.Response;


import static spark.Spark.*;

public class Main extends AbstractModule {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new Main());
        port(8080);

        before((request, response) -> response.type("application/json"));

        path("/santa", () -> {
            path("/beer", new BeerRouter(injector));
            path("/user", new UserRouter(injector));
            path("/meetup", new MeetupRouter(injector));
        });

        System.out.println("READY TO ROCK AND ROLL");
    }

    @Override
    protected void configure() {
        bind(CreateUserEntrypoint.class);
        bind(CheckinUserEntrypoint.class);

        bind(CreateMeetupEntrypoint.class);
        bind(JoinMeetupEntrypoint.class);

        bind(GetUser.class);
        bind(CreateUser.class);
        bind(UpdateUser.class);

        bind(GetMeetup.class);
        bind(CreateMeetup.class);
        bind(UpdateMeetup.class);

        bind(GetWeatherForecast.class);

        bind(MongoDatabase.class).toInstance(getMongoInstance());

        exception(DateNotInForecastException.class, this::badRequest);
        exception(UserAlreadyExistsException.class, this::badRequest);
        exception(RuntimeException.class, this::badRequest);

    }

    private MongoDatabase getMongoInstance(){
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient.getDatabase("local");
    }

    private void badRequest(Exception exception, Request req, Response res) {
        res.status(HttpStatus.SC_BAD_REQUEST);
        res.body(exception.getMessage());
    }
}
