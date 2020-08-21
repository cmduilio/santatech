package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.User;
import com.samit.core.exceptions.UserIsAdminException;

public class UserIsNotAdminValidator {

    @Inject
    public UserIsNotAdminValidator() {
    }

    public void validate(User user){

        if(user.isAdmin()) {
            throw new UserIsAdminException();
        }
    }
}
