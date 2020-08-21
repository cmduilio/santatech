package com.samit.entrypoints;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.samit.core.entities.User;
import com.samit.core.repositories.db.CreateUser;
import com.samit.entrypoints.validators.UserNotDuplicatedValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CreateUserEntrypointTest {

    private CreateUserEntrypoint createUserEntrypoint;
    private Gson gson;
    private UserNotDuplicatedValidator userNotDuplicatedValidator;
    private CreateUser createUser;
    private Request request;
    private Response response;

    @Before
    public void setUp(){
        this.gson =new Gson();
        this.userNotDuplicatedValidator = mock(UserNotDuplicatedValidator.class);
        this.createUser = mock(CreateUser.class);
        this.request = mock(Request.class);
        this.response = mock(Response.class);
        this.createUserEntrypoint = new CreateUserEntrypoint(this.gson,
                this.userNotDuplicatedValidator, this.createUser);
    }

    @Test
    public void when_creating_user_return_ok(){
        User expected = new User();
        expected.setName("Mike Tyson3");
        expected.setAdmin(true);

        when(this.request.body()).thenReturn(getUserString());
        doNothing().when(this.userNotDuplicatedValidator).validate(expected);
        doNothing().when(this.createUser).save(expected);

        User result =  (User) this.createUserEntrypoint.handle(this.request, this.response);
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.isAdmin(), result.isAdmin());
    }

    private String getUserString() {
        return "{\n" +
                "    \"name\" : \"Mike Tyson3\",\n" +
                "    \"admin\": true\n" +
                "}";
    }
}
