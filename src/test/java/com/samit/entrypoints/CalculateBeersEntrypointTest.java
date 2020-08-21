package com.samit.entrypoints;

import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.core.repositories.rest.GetWeatherForecast;
import com.samit.entrypoints.dto.WeatherForecast;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import com.samit.entrypoints.validators.MeetupExistsValidatorTest;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsAdminValidator;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CalculateBeersEntrypointTest {

    private GetWeatherForecast getWeatherForecast;
    private MeetupExistsValidator meetupExistsValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsAdminValidator userIsAdminValidator;
    private GetMeetup getMeetup;
    private GetUser getUser;
    private CalculateBeersEntrypoint calculateBeersEntrypoint;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        this.getMeetup = mock(GetMeetup.class);
        this.meetupExistsValidator = mock(MeetupExistsValidator.class);
        this.getWeatherForecast = mock(GetWeatherForecast.class);
        this.userExistsValidator = mock(UserExistsValidator.class);
        this.userIsAdminValidator = mock(UserIsAdminValidator.class);
        this.getUser = mock(GetUser.class);

        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.calculateBeersEntrypoint = new CalculateBeersEntrypoint(this.getWeatherForecast,
                this.meetupExistsValidator, this.userExistsValidator, this.userIsAdminValidator,
                this.getMeetup, this.getUser);
    }

    @Test
    public void when_calculate_beers_return_0(){
        String meetupDate = LocalDate.now().toString();
        String username = "Mike Tyson";
        Double temperature = 0D;
        long expected = 0;

        Set attendees = new HashSet<>();
        Meetup meetup = new Meetup();
        meetup.setDate(meetupDate);
        meetup.setAttendees(attendees);
        User user = new User();
        user.setName(username);

        WeatherForecast weatherForecast = mock(WeatherForecast.class);

        when(this.request.params("meetupDate")).thenReturn(meetupDate);
        when(this.request.params("username")).thenReturn(username);
        when(this.getMeetup.get(anyString())).thenReturn(meetup);
        when(this.getUser.get(anyString())).thenReturn(user);

        doNothing().when(this.meetupExistsValidator).validate(any());
        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.userIsAdminValidator).validate(any());
        when(this.getWeatherForecast.get()).thenReturn(weatherForecast);
        when(weatherForecast.getTemperatureOfDate(any())).thenReturn(temperature);

        long result = (long) this.calculateBeersEntrypoint.handle(this.request, this.response);
        assertEquals(result, expected);
    }

    @Test
    public void when_calculate_beers_return_1_box(){
        String meetupDate = LocalDate.now().toString();
        String username = "Mike Tyson";
        Double temperature = 21D;
        long expected = 1;

        Set attendees = new HashSet<>();
        for(int i = 0; i < 4; i++){
            attendees.add(username+i);
        }
        Meetup meetup = new Meetup();
        meetup.setDate(meetupDate);
        meetup.setAttendees(attendees);
        User user = new User();
        user.setName(username);

        WeatherForecast weatherForecast = mock(WeatherForecast.class);

        when(this.request.params("meetupDate")).thenReturn(meetupDate);
        when(this.request.params("username")).thenReturn(username);
        when(this.getMeetup.get(anyString())).thenReturn(meetup);
        when(this.getUser.get(anyString())).thenReturn(user);

        doNothing().when(this.meetupExistsValidator).validate(any());
        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.userIsAdminValidator).validate(any());
        when(this.getWeatherForecast.get()).thenReturn(weatherForecast);
        when(weatherForecast.getTemperatureOfDate(any())).thenReturn(temperature);

        long result = (long) this.calculateBeersEntrypoint.handle(this.request, this.response);
        assertEquals(expected, result);
    }


    @Test
    public void when_calculate_beers_return_4_boxes(){
        String meetupDate = LocalDate.now().toString();
        String username = "Mike Tyson";
        Double temperature = 25D;
        long expected = 4;

        Set attendees = new HashSet<>();
        for(int i = 0; i < 14; i++){
            attendees.add(username+i);
        }
        Meetup meetup = new Meetup();
        meetup.setDate(meetupDate);
        meetup.setAttendees(attendees);
        User user = new User();
        user.setName(username);

        WeatherForecast weatherForecast = mock(WeatherForecast.class);

        when(this.request.params("meetupDate")).thenReturn(meetupDate);
        when(this.request.params("username")).thenReturn(username);
        when(this.getMeetup.get(anyString())).thenReturn(meetup);
        when(this.getUser.get(anyString())).thenReturn(user);

        doNothing().when(this.meetupExistsValidator).validate(any());
        doNothing().when(this.userExistsValidator).validate(any());
        doNothing().when(this.userIsAdminValidator).validate(any());
        when(this.getWeatherForecast.get()).thenReturn(weatherForecast);
        when(weatherForecast.getTemperatureOfDate(any())).thenReturn(temperature);

        long result = (long) this.calculateBeersEntrypoint.handle(this.request, this.response);
        assertEquals(expected, result);
    }
}
