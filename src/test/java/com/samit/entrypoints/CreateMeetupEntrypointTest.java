package com.samit.entrypoints;

import com.google.gson.Gson;
import com.samit.core.entities.Meetup;
import com.samit.core.exceptions.MeetupDateOutOfBoundsException;
import com.samit.core.repositories.db.CreateMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.entrypoints.validators.MeetupIsInTimeValidator;
import com.samit.entrypoints.validators.MeetupNotDuplicatedValidator;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsAdminValidator;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateMeetupEntrypointTest {

    private CreateMeetupEntrypoint createMeetupEntrypoint;
    private Gson gson;
    private MeetupNotDuplicatedValidator meetupNotDuplicatedValidator;
    private MeetupIsInTimeValidator meetupIsInTimeValidator;
    private UserIsAdminValidator userIsAdminValidator;
    private UserExistsValidator userExistsValidator;
    private GetUser getUser;
    private CreateMeetup createMeetup;
    private Request request;
    private Response response;

    @Before
    public void setUp(){
        this.gson =new Gson();
        this.meetupNotDuplicatedValidator = mock(MeetupNotDuplicatedValidator.class);
        this.meetupIsInTimeValidator = mock(MeetupIsInTimeValidator.class);
        this.userIsAdminValidator = mock(UserIsAdminValidator.class);
        this.userExistsValidator = mock(UserExistsValidator.class);
        this.getUser = mock(GetUser.class);
        this.createMeetup = mock(CreateMeetup.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.createMeetupEntrypoint = new CreateMeetupEntrypoint(this.gson,
                this.meetupNotDuplicatedValidator, this.meetupIsInTimeValidator,
                this.userIsAdminValidator, this.userExistsValidator,
                this.getUser, this.createMeetup);
    }

    @Test
    public void when_creating_meetup_return_ok(){
        Meetup expected = new Meetup();
        expected.setDate(LocalDate.now().toString());
        String username = "Mike Tyson";

        when(this.request.body()).thenReturn(getMeetupString());
        when(this.request.queryParams("username")).thenReturn(username);

        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.userIsAdminValidator).validate(any());
        doNothing().when(this.meetupNotDuplicatedValidator).validate(expected);
        doCallRealMethod().when(this.meetupIsInTimeValidator).validate(any());

        doNothing().when(this.createMeetup).save(expected);

        Meetup result =  (Meetup) this.createMeetupEntrypoint.handle(this.request, this.response);
        assertEquals(expected.getDate(), result.getDate());
    }

    @Test(expected = MeetupDateOutOfBoundsException.class)
    public void when_creating_meetup_return_date_out_of_bounds(){
        Meetup expected = new Meetup();
        expected.setDate(LocalDate.now().toString());
        String username = "Mike Tyson";

        when(this.request.body()).thenReturn(getMeetupNotInTimeString());
        when(this.request.queryParams("username")).thenReturn(username);

        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.userIsAdminValidator).validate(any());
        doNothing().when(this.meetupNotDuplicatedValidator).validate(expected);
        doCallRealMethod().when(this.meetupIsInTimeValidator).validate(any());

        doNothing().when(this.createMeetup).save(expected);

        this.createMeetupEntrypoint.handle(this.request, this.response);
    }

    private String getMeetupString() {
        return "{\n" +
                "    \"date\" : \"" + LocalDate.now().toString() + "\"\n" +
                "}";
    }

    private String getMeetupNotInTimeString() {
        return "{\n" +
                "    \"date\" : \"" + LocalDate.now().plusDays(-1).toString() + "\"\n" +
                "}";
    }
}
