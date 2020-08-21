package com.samit.core.exceptions;

public class UserIsNotAdminException extends RuntimeException {

    public UserIsNotAdminException() {
        super("User doesn't have permission.");
    }
}
