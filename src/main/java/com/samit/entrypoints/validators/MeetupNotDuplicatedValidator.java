package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.exceptions.MeetupAlreadyExistsException;
import com.samit.core.exceptions.UserAlreadyExistsException;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;

public class MeetupNotDuplicatedValidator {

    private GetMeetup getMeetup;

    @Inject
    public MeetupNotDuplicatedValidator(GetMeetup getMeetup) {
        this.getMeetup = getMeetup;
    }

    public void validate(Meetup meetup){
        Meetup mongoUser = this.getMeetup.get(meetup.getDate());

        if(mongoUser != null) {
            throw new MeetupAlreadyExistsException();
        }
    }
}
