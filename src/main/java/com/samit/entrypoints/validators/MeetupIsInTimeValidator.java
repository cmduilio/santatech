package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.exceptions.MeetupAlreadyExistsException;
import com.samit.core.exceptions.MeetupDateOutOfBoundsException;
import com.samit.core.repositories.db.GetMeetup;
import jdk.vm.ci.meta.Local;

import java.time.LocalDate;

public class MeetupIsInTimeValidator {

    @Inject
    public MeetupIsInTimeValidator(){
    }

    public void validate(Meetup meetup){
        LocalDate meetupDate = LocalDate.parse(meetup.getDate());

        if(meetupDate.isBefore(LocalDate.now()) || meetupDate.isAfter(LocalDate.now().plusDays(4))) {
            throw new MeetupDateOutOfBoundsException();
        }
    }
}
