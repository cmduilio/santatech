package com.samit.configuration.routers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.samit.entrypoints.CreateUserEntrypoint;
import com.samit.entrypoints.CheckinUserEntrypoint;
import spark.RouteGroup;

import static spark.Spark.*;

public class UserRouter implements RouteGroup {

    private Injector injector;

    @Inject
    public UserRouter(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void addRoutes() {
        post("", injector.getInstance(CreateUserEntrypoint.class), injector.getInstance(Gson.class)::toJson);
        put("/checkin", injector.getInstance(CheckinUserEntrypoint.class), injector.getInstance(Gson.class)::toJson);
    }
}
