package com.samit.entrypoints.validators;

import com.samit.core.entities.User;
import com.samit.core.exceptions.UserDoesntExistsException;
import org.junit.Before;
import org.junit.Test;

public class UserExistsValidatorTest {

    private UserExistsValidator userExistsValidator;

    @Before
    public void setUp() {
        this.userExistsValidator = new UserExistsValidator();
    }

    @Test
    public void when_user_exists_return_ok(){
        this.userExistsValidator.validate(new User());
    }

    @Test(expected = UserDoesntExistsException.class)
    public void when_user_doesnt_exists_return_exception(){
        this.userExistsValidator.validate(null);
    }

}
