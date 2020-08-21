package com.samit.core.exceptions;

public class UserIsAdminException extends RuntimeException {

    public UserIsAdminException() {
        super("User cant do this task.");
    }
}
