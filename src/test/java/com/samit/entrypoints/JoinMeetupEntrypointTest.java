package com.samit.entrypoints;

import com.google.gson.Gson;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.core.repositories.db.UpdateMeetup;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import com.samit.entrypoints.validators.MeetupExistsValidatorTest;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsNotAdminValidator;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class JoinMeetupEntrypointTest {


    private Gson gson;
    private UpdateMeetup updateMeetup;
    private GetUser getUser;
    private GetMeetup getMeetup;
    private MeetupExistsValidator meetupExistsValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsNotAdminValidator userIsNotAdminValidator;
    private JoinMeetupEntrypoint joinMeetupEntrypoint;
    private Request request;
    private Response response;

    @Before
    public void setUp(){
        this.gson =new Gson();
        this.updateMeetup = mock(UpdateMeetup.class);
        this.getUser = mock(GetUser.class);
        this.getMeetup = mock(GetMeetup.class);
        this.meetupExistsValidator = mock(MeetupExistsValidator.class);
        this.userExistsValidator = mock(UserExistsValidator.class);
        this.userIsNotAdminValidator = mock(UserIsNotAdminValidator.class);

        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.joinMeetupEntrypoint = new JoinMeetupEntrypoint(this.gson, this.updateMeetup, this.getUser,
                this.getMeetup, this.meetupExistsValidator, this.userExistsValidator, this.userIsNotAdminValidator);
    }

    @Test
    public void when_creating_user_return_ok(){
        Meetup expected = new Meetup();
        expected.setAttendees(new HashSet<>());
        User user = new User();

        when(this.request.body()).thenReturn(getMeetupAndUser());
        when(this.getMeetup.get(any())).thenReturn(expected);
        when(this.getUser.get(any())).thenReturn(user);

        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.meetupExistsValidator).validate(any());
        doNothing().when(this.userIsNotAdminValidator).validate(any());

        doNothing().when(this.updateMeetup).update(any());

        Meetup result =  (Meetup) this.joinMeetupEntrypoint.handle(this.request, this.response);
        assertEquals(result.getAttendees().size(), 1);
    }

    private String getMeetupAndUser() {
        return "{\n" +
                "    \"user_name\" : \"Mike Tyson3\",\n" +
                "    \"meetup_date\": \"2020-08-14\"\n" +
                "}";
    }
}
