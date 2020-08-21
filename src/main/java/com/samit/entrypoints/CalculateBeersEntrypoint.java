package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.core.entities.Meetup;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.GetMeetup;
import com.samit.core.repositories.db.GetUser;
import com.samit.entrypoints.dto.WeatherForecast;
import com.samit.core.repositories.rest.GetWeatherForecast;
import com.samit.entrypoints.validators.MeetupExistsValidator;
import com.samit.entrypoints.validators.UserExistsValidator;
import com.samit.entrypoints.validators.UserIsAdminValidator;
import spark.Request;
import spark.Response;
import spark.Route;

import java.time.LocalDate;

public class CalculateBeersEntrypoint implements Route {

    private GetWeatherForecast getWeatherForecast;
    private MeetupExistsValidator meetupExistsValidator;
    private UserExistsValidator userExistsValidator;
    private UserIsAdminValidator userIsAdminValidator;
    private GetMeetup getMeetup;
    private GetUser getUser;

    @Inject
    public CalculateBeersEntrypoint(GetWeatherForecast getWeatherForecast, MeetupExistsValidator meetupExistsValidator,
                                    UserExistsValidator userExistsValidator, UserIsAdminValidator userIsAdminValidator,
                                    GetMeetup getMeetup, GetUser getUser){
        this.getWeatherForecast = getWeatherForecast;
        this.meetupExistsValidator = meetupExistsValidator;
        this.userExistsValidator = userExistsValidator;
        this.userIsAdminValidator = userIsAdminValidator;
        this.getMeetup = getMeetup;
        this.getUser = getUser;
    }

    @Override
    public Object handle(Request request, Response response){
        String meetupDate = request.params("meetupDate");
        String username = request.params("username");

        Meetup meetup = this.getMeetup.get(meetupDate);
        User user = this.getUser.get(username);

        this.meetupExistsValidator.validate(meetup);
        this.userExistsValidator.validate(user);
        this.userIsAdminValidator.validate(user);

        WeatherForecast weatherForecast = this.getWeatherForecast.get();
        Double temperature = weatherForecast.getTemperatureOfDate(meetupDate + " 12:00:00");

        return CalculateBeersFromTemperature(temperature, meetup.getAttendees().size());
    }

    private long CalculateBeersFromTemperature(Double temperature, int attendees) {
        long beers = attendees;
        if(temperature > 24){
            beers = beers*2;
        } else if (temperature < 20){
            beers = Math.round(beers*0.75);
        }

        //Busco el siguiente múltiplo de 6 porque las cajas se venden de a 6 birras
        beers = beers + ((6 - beers) % 6);

        //Divido por 6 porque quiero saber las CAJAS de birras
        return beers / 6;
    }
}