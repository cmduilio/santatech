package com.samit.entrypoints.validators;

import com.samit.core.entities.Meetup;
import com.samit.core.exceptions.MeetupDateOutOfBoundsException;
import com.samit.core.exceptions.MeetupDoesntExistsException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class MeetupIsInTimeValidatorTest {

    private MeetupIsInTimeValidator meetupIsInTimeValidator;

    @Before
    public void setUp() {
        this.meetupIsInTimeValidator = new MeetupIsInTimeValidator();
    }

    @Test
    public void when_meetup_is_in_time_return_ok(){
        Meetup meetup = new Meetup();
        meetup.setDate(LocalDate.now().toString());
        this.meetupIsInTimeValidator.validate(meetup);
    }

    @Test(expected = MeetupDateOutOfBoundsException.class)
    public void when_meetup_is_before_today_return_exception(){
        Meetup meetup = new Meetup();
        meetup.setDate(LocalDate.now().plusDays(-1).toString());
        this.meetupIsInTimeValidator.validate(meetup);
    }

    @Test(expected = MeetupDateOutOfBoundsException.class)
    public void when_meetup_is_post_5_days_return_exception(){
        Meetup meetup = new Meetup();
        meetup.setDate(LocalDate.now().plusDays(7).toString());
        this.meetupIsInTimeValidator.validate(meetup);
    }

}
