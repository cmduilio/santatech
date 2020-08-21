package com.samit.entrypoints;

import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.rest.GetWeatherForecast;
import com.samit.entrypoints.dto.WeatherForecast;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;

public class GetMeetupTemperatureEntrypoint implements Route {

    private GetMeetup getMeetup;
    private MeetupExistsValidator meetupExistsValidator;
    private GetWeatherForecast getWeatherForecast;

    @Inject
    public GetMeetupTemperatureEntrypoint(GetMeetup getMeetup,
                                          MeetupExistsValidator meetupExistsValidator,
                                          GetWeatherForecast getWeatherForecast){
        this.getMeetup = getMeetup;
        this.meetupExistsValidator = meetupExistsValidator;
        this.getWeatherForecast = getWeatherForecast;
    }

    @Override
    public Object handle(Request request, Response response) {
        String meetupDate = request.params("meetupDate");

        Meetup meetup = this.getMeetup.get(meetupDate);

        this.meetupExistsValidator.validate(meetup);

        WeatherForecast weatherForecast = this.getWeatherForecast.get();
        Double temperature = weatherForecast.getTemperatureOfDate(meetupDate + " 12:00:00");

        return temperature;
    }
}