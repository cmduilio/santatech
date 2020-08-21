package com.samit.core.exceptions;

public class DateNotInForecastException extends RuntimeException {

    public DateNotInForecastException() {
        super("Please check a date between today and 5 days on");
    }
}
