package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.User;
import com.samit.core.exceptions.UserDoesntExistsException;
import com.samit.core.exceptions.UserIsNotAdminException;

public class UserIsAdminValidator {

    @Inject
    public UserIsAdminValidator() {
    }

    public void validate(User user){

        if(!user.isAdmin()) {
            throw new UserIsNotAdminException();
        }
    }
}
