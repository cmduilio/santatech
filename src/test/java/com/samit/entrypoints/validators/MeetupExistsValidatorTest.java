package com.samit.entrypoints.validators;

import com.samit.core.entities.Meetup;
import com.samit.core.exceptions.MeetupDoesntExistsException;
import org.junit.Before;
import org.junit.Test;

public class MeetupExistsValidatorTest {

    private MeetupExistsValidator meetupExistsValidator;

    @Before
    public void setUp() {
        this.meetupExistsValidator = new MeetupExistsValidator();
    }

    @Test
    public void when_meetup_exists_return_ok(){
        this.meetupExistsValidator.validate(new Meetup());
    }

    @Test(expected = MeetupDoesntExistsException.class)
    public void when_meetup_doesnt_exists_return_exception(){
        this.meetupExistsValidator.validate(null);
    }

}
