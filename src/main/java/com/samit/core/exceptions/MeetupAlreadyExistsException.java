package com.samit.core.exceptions;

public class MeetupAlreadyExistsException extends RuntimeException {

    public MeetupAlreadyExistsException() {
        super("Meetup already exists.");
    }
}
