package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.User;
import com.samit.core.exceptions.UserAlreadyExistsException;
import com.samit.core.exceptions.UserDoesntExistsException;
import com.samit.core.repositories.db.GetUser;

public class UserExistsValidator {

    @Inject
    public UserExistsValidator() {
    }

    public void validate(User user){

        if(user == null) {
            throw new UserDoesntExistsException();
        }
    }
}
