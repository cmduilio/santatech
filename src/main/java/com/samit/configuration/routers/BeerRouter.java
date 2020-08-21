package com.samit.configuration.routers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.samit.entrypoints.CalculateBeersEntrypoint;
import com.samit.entrypoints.CreateUserEntrypoint;
import spark.RouteGroup;

import static spark.Spark.get;
import static spark.Spark.post;

public class BeerRouter implements RouteGroup {

    private Injector injector;

    @Inject
    public BeerRouter(Injector injector) {
        this.injector = injector;
    }

    @Override
    public void addRoutes() {
        get("/:meetupDate/:username", injector.getInstance(CalculateBeersEntrypoint.class), injector.getInstance(Gson.class)::toJson);
    }
}
