package com.samit.entrypoints.validators;

import com.samit.core.entities.User;
import com.samit.core.exceptions.UserAlreadyExistsException;
import com.samit.core.exceptions.UserDoesntExistsException;
import com.samit.core.repositories.db.GetUser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserNotDuplicateValidatorTest {

    private GetUser getUser;
    private UserNotDuplicatedValidator userNotDuplicatedValidator;

    @Before
    public void setUp() {
        this.getUser = mock(GetUser.class);
        this.userNotDuplicatedValidator = new UserNotDuplicatedValidator(this.getUser);
    }

    @Test
    public void when_user_doesnt_exists_return_ok(){
        when(this.getUser.get(any())).thenReturn(null);
        this.userNotDuplicatedValidator.validate(new User());
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void when_user_exists_return_exception(){
        when(this.getUser.get(any())).thenReturn(new User());
        this.userNotDuplicatedValidator.validate(new User());
    }

}
