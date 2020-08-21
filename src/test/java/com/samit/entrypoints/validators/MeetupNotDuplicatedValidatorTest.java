package com.samit.entrypoints.validators;

import com.samit.core.entities.Meetup;
import com.samit.core.exceptions.MeetupAlreadyExistsException;
import com.samit.core.repositories.db.GetMeetup;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MeetupNotDuplicatedValidatorTest {

    private GetMeetup getMeetup;
    private MeetupNotDuplicatedValidator meetupNotDuplicatedValidator;

    @Before
    public void setUp() {
        this.getMeetup = mock(GetMeetup.class);
        this.meetupNotDuplicatedValidator = new MeetupNotDuplicatedValidator(this.getMeetup);
    }

    @Test
    public void when_meetup_doesnt_exists_return_ok(){
        when(this.getMeetup.get(any())).thenReturn(null);
        this.meetupNotDuplicatedValidator.validate(new Meetup());
    }

    @Test(expected = MeetupAlreadyExistsException.class)
    public void when_meetup_exists_return_exception(){
        when(this.getMeetup.get(any())).thenReturn(new Meetup());
        this.meetupNotDuplicatedValidator.validate(new Meetup());
    }

}
