package com.samit.entrypoints.validators;

import com.samit.core.entities.User;
import com.samit.core.exceptions.UserDoesntExistsException;
import com.samit.core.exceptions.UserIsNotAdminException;
import org.junit.Before;
import org.junit.Test;

public class UserIsAdminValidatorTest {

    private UserIsAdminValidator userIsAdminValidator;

    @Before
    public void setUp() {
        this.userIsAdminValidator = new UserIsAdminValidator();
    }

    @Test
    public void when_user_is_admin_return_ok(){
        User user = new User();
        user.setAdmin(true);
        this.userIsAdminValidator.validate(user);
    }

    @Test(expected = UserIsNotAdminException.class)
    public void when_user_is_not_admin_return_exception(){
        User user = new User();
        user.setAdmin(false);
        this.userIsAdminValidator.validate(user);
    }

}
