package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import com.conpresp.conprespapi.dto.user.UserCreateRequest;
import com.conpresp.conprespapi.dto.user.UserPasswordRequest;
import com.conpresp.conprespapi.dto.user.UserUpdateRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentTest
@WithMockUser(authorities = {"ADMINISTRATOR"})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockMvcTestBuilder userMockMvc;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setup() {
        this.userMockMvc = new MockMvcTestBuilder("/users", mockMvc);
    }

    @Test
    void shouldCreateAUserAndReturnCreatedHTTPCodeAlongWithALocationHeader() throws Exception {
        var request = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "123456789", "MODERATOR", "UAM");

        var response = userMockMvc.post(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    void shouldReturnAnErrorWhenTryingToInsertAUserWithAExistingEmail() throws Exception {
        var request = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "123456789","MODERATOR", "UAM");
        var request2 = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "123456789","MODERATOR", "UAM");

        userMockMvc.post(request);

        userMockMvc.post(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cause").value("Duplicate entry"));
    }

    @Test
    void shouldReturnBadRequestWhenTryingToCreateAUserWithDifferentPasswords() throws Exception {
        var request = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "12345678910","MODERATOR", "UAM");


        userMockMvc.post(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cause").value("The given password are not the same."));
    }

    @Test
    void shouldReturnTheListOfUsers() throws Exception {
        var request1 = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "123456789","MODERATOR", "UAM");
        var request2 = new UserCreateRequest("Matheus", "Morillo", "matheus@mail.com", "123456789", "123456789","MODERATOR", "UAM");

        userMockMvc.post(request1);
        userMockMvc.post(request2);

        userMockMvc.get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(2))
                .andExpect(jsonPath("$.content[0].firstName").value("Matheus"))
                .andExpect(jsonPath("$.content[1].firstName").value("Raphael"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        var request1 = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "123456789","MODERATOR", "UAM");
        var request2 = new UserCreateRequest("Matheus", "Morillo", "matheus@mail.com", "123456789", "123456789","MODERATOR", "UAM");

        userMockMvc.post(request1);

        var response = userMockMvc.post(request2)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).delete();

        userMockMvc.get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Raphael"));
    }

    @Test
    void shouldReturnNotFoundByDeletingInvalidUser() throws Exception {
        userMockMvc.appendPathVar("InvalidUserId").delete().andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUserById() throws Exception {
        var request = new UserCreateRequest("Matheus", "Morillo", "matheus@mail.com", "123456789", "123456789","MODERATOR", "UAM");

        var response = userMockMvc.post(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        userMockMvc.appendPathVar(uuid).get()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid))
                .andExpect(jsonPath("$.firstName").value("Matheus"))
                .andExpect(jsonPath("$.userGroup").value("UAM"));
    }

    // TODO: move those bean validation tests to test module
    @Test
    void shouldReturnBadRequestWhenInvalidProfileSent() throws Exception {
        var request = new UserCreateRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "123456789","INVALID", "UAM");

        userMockMvc.post(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("profile")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("Invalid profile name! Options: MODERATOR, ADMINISTRATOR, COMMON")));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidGroupSent() throws Exception {
        var request = new UserCreateRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "123456789","COMMON", "invalid");

        userMockMvc.post(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("userGroup")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("Invalid Group name! Options: UAM, DHP, CONPRESP")));
    }

    @Test
    void shouldReturnNotFoundByInvalidId() throws Exception {
        userMockMvc.appendPathVar("invalidId").get()
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
        var userRequest = new UserCreateRequest();

        userMockMvc.post(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("firstName", "lastName", "email", "password", "confirmPassword", "profile", "userGroup")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("must not be blank")));
    }

    @Test
    void shouldReturnBadRequestAndMinimumPasswordSizeWhenLessThan8Characters() throws Exception {
        var userRequest = new UserCreateRequest("Raphael", "Nask", "Nask@mail.com", "123", "123","MODERATOR", "UAM");

        userMockMvc.post(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].error").value("size must be between 8 and 32"));
    }

    @Test
    void shouldReturnBadRequestAndMessageWhenEmailIsInvalid() throws Exception {
        var userRequest = new UserCreateRequest("Raphael", "Nask", "invalidemail", "123456789", "123456789","MODERATOR", "UAM");


        userMockMvc.post(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].error").value("must be a well-formed email address"));
    }

    @Test
    void shouldUpdateAUser() throws Exception {
        var userRequest = new UserCreateRequest("Raphael", "Nask","Nask@gmail.com", "123456789", "123456789","MODERATOR", "UAM");

        var response = userMockMvc.post(userRequest).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        var userUpdateRequest = new UserUpdateRequest("COMMON","Rodrigo", "Nascimento", "Nask@Hotmail.com", "UAM", "ACTIVE");

        userMockMvc.appendPathVar(uuid).put(userUpdateRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile").value("COMMON"))
                .andExpect(jsonPath("$.firstName").value("Rodrigo"))
                .andExpect(jsonPath("$.lastName").value("Nascimento"))
                .andExpect(jsonPath("$.email").value("Nask@Hotmail.com"))
                .andExpect(jsonPath("$.updatedAt").exists()).andDo(print());

    }

    @Test
    void shoudReturnNotFoundWhenTryingUpdateAnNonexistentUser() throws Exception {
        var userUpdateRequest = new UserUpdateRequest("COMMON", "Rodrigo", "Nascimento", "Nask@Hotmail.com", "UAM", "ACTIVE");

        userMockMvc.appendPathVar("InvalidId").put(userUpdateRequest)
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateAUserPassword() throws Exception {
        var userRequest = new UserCreateRequest("Raphael", "Nask","Nask@gmail.com", "123456789", "123456789","MODERATOR", "UAM");

        var response = userMockMvc.post(userRequest).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        var userPasswordRequest = new UserPasswordRequest("12345678910", "12345678910");

        userMockMvc.appendPathVar(uuid).appendPathVar("change-password").put(userPasswordRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Nask@gmail.com"));

    }
}
