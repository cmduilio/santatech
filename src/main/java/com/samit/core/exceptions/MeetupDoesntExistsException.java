package com.samit.core.exceptions;

public class MeetupDoesntExistsException extends RuntimeException {

    public MeetupDoesntExistsException() {
        super("Meetup doesn't exists.");
    }
}
