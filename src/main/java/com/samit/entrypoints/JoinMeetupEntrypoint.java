package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.core.repositories.db.UpdateMeetup;
import com.samit.entrypoints.dto.MeetupUserDto;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsNotAdminValidator;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinMeetupEntrypoint implements Route {

    private Gson gson;
    private UpdateMeetup updateMeetup;
    private GetUser getUser;
    private GetMeetup getMeetup;
    private MeetupExistsValidator meetupExistsValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsNotAdminValidator userIsNotAdminValidator;

    @Inject
    public JoinMeetupEntrypoint(Gson gson, UpdateMeetup updateMeetup, GetUser getUser, GetMeetup getMeetup,
                                MeetupExistsValidator meetupExistsValidator, UserExistsValidator userExistsValidator,
                                UserIsNotAdminValidator userIsNotAdminValidator){
        this.gson = gson;
        this.updateMeetup = updateMeetup;
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

        meetup.getAttendees().add(user.getName());
        this.updateMeetup.update(meetup);

        return meetup;
    }
}