package com.samit.core.exceptions;

public class MeetupDateOutOfBoundsException extends RuntimeException {

    public MeetupDateOutOfBoundsException() {
        super("Meetup Date is out of bounds. Should be between today and 4 days from today.");
    }
}
