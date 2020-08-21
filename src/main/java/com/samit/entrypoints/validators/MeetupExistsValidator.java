package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.exceptions.MeetupDoesntExistsException;
import com.samit.core.exceptions.UserDoesntExistsException;

public class MeetupExistsValidator {

    @Inject
    public MeetupExistsValidator() {
    }

    public void validate(Meetup meetup){

        if(meetup == null) {
            throw new MeetupDoesntExistsException();
        }
    }
}
