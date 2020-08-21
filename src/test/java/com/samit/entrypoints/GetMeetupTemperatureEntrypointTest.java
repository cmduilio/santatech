package com.samit.entrypoints;

import com.samit.core.entities.Meetup;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.rest.GetWeatherForecast;
import com.samit.entrypoints.dto.WeatherForecast;
import com.samit.entrypoints.validators.*;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class GetMeetupTemperatureEntrypointTest {

    private GetMeetup getMeetup;
    private MeetupExistsValidator meetupExistsValidator;
    private GetWeatherForecast getWeatherForecast;
    private GetMeetupTemperatureEntrypoint getMeetupTemperatureEntrypoint;
    private Request request;
    private Response response;

    @Before
    public void setUp() {
        this.getMeetup = mock(GetMeetup.class);
        this.meetupExistsValidator = mock(MeetupExistsValidator.class);
        this.getWeatherForecast = mock(GetWeatherForecast.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.getMeetupTemperatureEntrypoint = new GetMeetupTemperatureEntrypoint(this.getMeetup,
                this.meetupExistsValidator, this.getWeatherForecast);
    }

    @Test
    public void when_creating_meetup_return_ok() {
        String meetupDate = LocalDate.now().toString();
        Meetup meetup = new Meetup();
        meetup.setDate(meetupDate);
        WeatherForecast weatherForecast = mock(WeatherForecast.class);

        when(this.request.params("meetupDate")).thenReturn(meetupDate);
        when(this.getMeetup.get(anyString())).thenReturn(meetup);
        doNothing().when(this.meetupExistsValidator).validate(any());
        when(this.getWeatherForecast.get()).thenReturn(weatherForecast);
        when(weatherForecast.getTemperatureOfDate(any())).thenReturn(0D);

        Double result = (Double) this.getMeetupTemperatureEntrypoint.handle(this.request, this.response);
        assertEquals(result, Double.valueOf(0));
    }
}
