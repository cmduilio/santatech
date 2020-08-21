package com.samit.core.exceptions;

public class UserDoesntExistsException extends RuntimeException {

    public UserDoesntExistsException() {
        super("User doesnt exists.");
    }
}
