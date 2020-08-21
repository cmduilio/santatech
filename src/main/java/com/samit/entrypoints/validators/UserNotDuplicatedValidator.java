package com.samit.entrypoints.validators;

import com.google.inject.Inject;
import com.samit.core.entities.User;
import com.samit.core.exceptions.UserAlreadyExistsException;
import com.samit.core.repositories.db.GetUser;

public class UserNotDuplicatedValidator {

    private GetUser getUser;

    @Inject
    public UserNotDuplicatedValidator(GetUser getUser) {
        this.getUser = getUser;
    }

    public void validate(User user){
        User mongoUser = this.getUser.get(user.getName());

        if(mongoUser != null) {
            throw new UserAlreadyExistsException();
        }
    }
}
