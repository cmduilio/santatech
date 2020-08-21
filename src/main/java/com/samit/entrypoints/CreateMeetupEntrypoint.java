package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.CreateMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.entrypoints.validators.MeetupIsInTimeValidator;
import com.samit.entrypoints.validators.MeetupNotDuplicatedValidator;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsAdminValidator;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashSet;

public class CreateMeetupEntrypoint implements Route {

    private MeetupNotDuplicatedValidator meetupNotDuplicatedValidator;
    private MeetupIsInTimeValidator meetupIsInTimeValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsAdminValidator userIsAdminValidator;
    private Gson gson;
    private CreateMeetup createMeetup;
    private GetUser getUser;

    @Inject
    public CreateMeetupEntrypoint(Gson gson, MeetupNotDuplicatedValidator meetupNotDuplicatedValidator,
                                  MeetupIsInTimeValidator meetupIsInTimeValidator, UserIsAdminValidator userIsAdminValidator,
                                  UserExistsValidator userExistsValidator, GetUser getUser,
                                  CreateMeetup createMeetup){
        this.meetupNotDuplicatedValidator = meetupNotDuplicatedValidator;
        this.meetupIsInTimeValidator = meetupIsInTimeValidator;
        this.userExistsValidator = userExistsValidator;
        this.userIsAdminValidator = userIsAdminValidator;
        this.gson = gson;
        this.createMeetup = createMeetup;
        this.getUser = getUser;
    }

    @Override
    public Object handle(Request request, Response response) {
        String userName = request.queryParams("username");
        Meetup meetup = this.gson.fromJson(request.body(), Meetup.class);
        meetup.setAttendees(new HashSet<>());

        User user = this.getUser.get(userName);

        this.userExistsValidator.validate(user);
        this.userIsAdminValidator.validate(user);
        this.meetupNotDuplicatedValidator.validate(meetup);
        this.meetupIsInTimeValidator.validate(meetup);

        this.createMeetup.save(meetup);
        return meetup;
    }
}