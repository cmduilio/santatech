package com.samit.entrypoints.dto;

import com.google.gson.annotations.SerializedName;
import com.samit.core.exceptions.DateNotInForecastException;

import java.util.List;

public class WeatherForecast {

    private List<Weather> list;

    public List<Weather> getList() {
        return list;
    }

    public void setList(List<Weather> list) {
        this.list = list;
    }

    public Double getTemperatureOfDate(String date) {
        Double temperature = null;
        for (Weather day: list) {
            if(day.getDate().equals(date)){
                temperature = day.getMain().getTemp();
                break;
            }
        }

        if(temperature == null){
            throw new DateNotInForecastException();
        }

        return temperature;
    }

    private class Weather {
        @SerializedName("dt_txt")
        private String date;
        private Data main;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Data getMain() {
            return main;
        }

        public void setMain(Data main) {
            this.main = main;
        }

        private class Data {
            private Double temp;

            public Double getTemp() {
                return temp;
            }

            public void setTemp(Double temp) {
                this.temp = temp;
            }
        }
    }
}
