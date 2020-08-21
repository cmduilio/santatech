package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.CreateUser;
import com.samit.entrypoints.validators.UserNotDuplicatedValidator;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashSet;

public class CreateUserEntrypoint implements Route {

    private UserNotDuplicatedValidator userNotDuplicatedValidator;
    private Gson gson;
    private CreateUser createUser;

    @Inject
    public CreateUserEntrypoint(Gson gson, UserNotDuplicatedValidator userNotDuplicatedValidator, CreateUser createUser){
        this.userNotDuplicatedValidator = userNotDuplicatedValidator;
        this.gson = gson;
        this.createUser = createUser;
    }

    @Override
    public Object handle(Request request, Response response) {
        User user = this.gson.fromJson(request.body(), User.class);
        user.setMeetups(new HashSet<>());

        this.userNotDuplicatedValidator.validate(user);

        this.createUser.save(user);
        return user;
    }
}