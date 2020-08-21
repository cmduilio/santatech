package com.samit.configuration.routers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.samit.entrypoints.CreateMeetupEntrypoint;
import com.samit.entrypoints.GetMeetupTemperatureEntrypoint;
import com.samit.entrypoints.JoinMeetupEntrypoint;
import spark.RouteGroup;

import static spark.Spark.*;

public class MeetupRouter implements RouteGroup {

    private Injector injector;

    @Inject
    public MeetupRouter(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void addRoutes() {
        post("", injector.getInstance(CreateMeetupEntrypoint.class), injector.getInstance(Gson.class)::toJson);
        put("/join", injector.getInstance(JoinMeetupEntrypoint.class), injector.getInstance(Gson.class)::toJson);
        get("/temperature/:meetupDate", injector.getInstance(GetMeetupTemperatureEntrypoint.class), injector.getInstance(Gson.class)::toJson);
    }
}
