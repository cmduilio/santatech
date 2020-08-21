package com.samit.entrypoints.validators;

import com.samit.core.entities.User;
import com.samit.core.exceptions.UserIsAdminException;
import com.samit.core.exceptions.UserIsNotAdminException;
import org.junit.Before;
import org.junit.Test;

public class UserIsNotAdminValidatorTest {

    private UserIsNotAdminValidator userIsNotAdminValidator;

    @Before
    public void setUp() {
        this.userIsNotAdminValidator = new UserIsNotAdminValidator();
    }

    @Test
    public void when_user_is_not_admin_return_ok(){
        User user = new User();
        user.setAdmin(false);
        this.userIsNotAdminValidator.validate(user);
    }

    @Test(expected = UserIsAdminException.class)
    public void when_user_is_admin_return_exception(){
        User user = new User();
        user.setAdmin(true);
        this.userIsNotAdminValidator.validate(user);
    }

}
