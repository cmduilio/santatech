package com.samit.core.repositories.rest;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.samit.entrypoints.dto.WeatherForecast;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

public class GetWeatherForecast {

    private static final String URL = "http://api.openweathermap.org/data/2.5/forecast";
    private static final String CITY = "Buenos Aires";
    private static final String APP_ID = "311fb39b7559680910be32f5ab2f7588";
    private Gson gson;

    @Inject
    public GetWeatherForecast(Gson gson) {
        this.gson = gson;
    }

    public WeatherForecast get(){
        try {
            HttpResponse<JsonNode> response = Unirest.get(URL)
                .header("accept", "application/json")
                .queryString("units", "metric")
                .queryString("q", CITY)
                .queryString("appid", APP_ID)
                .asJson();
            return this.gson.fromJson(response.getBody().toString(), WeatherForecast.class);
        }catch (Exception ex){
            throw new RuntimeException();
        }
    }
}
