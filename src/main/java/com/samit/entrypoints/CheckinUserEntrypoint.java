package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.core.repositories.db.UpdateUser;
import com.samit.entrypoints.dto.MeetupUserDto;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsNotAdminValidator;
import com.samit.entrypoints.validators.UserNotDuplicatedValidator;
import spark.Request;
import spark.Response;
import spark.Route;

public class CheckinUserEntrypoint implements Route {

    private Gson gson;
    private UpdateUser updateUser;
    private GetUser getUser;
    private GetMeetup getMeetup;
    private MeetupExistsValidator meetupExistsValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsNotAdminValidator userIsNotAdminValidator;

    @Inject
    public CheckinUserEntrypoint(Gson gson, UpdateUser updateUser, GetUser getUser, GetMeetup getMeetup,
                                 MeetupExistsValidator meetupExistsValidator, UserExistsValidator userExistsValidator,
                                 UserIsNotAdminValidator userIsNotAdminValidator){
        this.gson = gson;
        this.updateUser = updateUser;
        this.getUser = getUser;
        this.getMeetup = getMeetup;
        this.meetupExistsValidator = meetupExistsValidator;
        this.userExistsValidator = userExistsValidator;
        this.userIsNotAdminValidator = userIsNotAdminValidator;
    }

    @Override
    public Object handle(Request request, Response response) {
        MeetupUserDto meetupUserDto = this.gson.fromJson(request.body(), MeetupUserDto.class);

        Meetup meetup = this.getMeetup.get(meetupUserDto.getMeetupDate());
        User user = this.getUser.get(meetupUserDto.getUserName());

        this.userExistsValidator.validate(user);
        this.meetupExistsValidator.validate(meetup);
        this.userIsNotAdminValidator.validate(user);

        user.getMeetups().add(meetup.getDate());
        updateUser.update(user);

        return user;
    }
}