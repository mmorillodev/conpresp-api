package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ComponentTest
@WithMockUser(authorities = {"ADMINISTRATOR"})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAUserAndReturnCreatedHTTPCodeAlongWithALocationHeader() throws Exception {
        var request = new UserRequest("Raphael", "Nask","Nask@mail.com", "123456789", "MODERATOR");

        var response = makePostRequest(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    public void shouldReturnTheListOfUsers() throws Exception {
        var request1 = new UserRequest("Raphael", "Nask","Nask@mail.com", "123456789", "MODERATOR");
        var request2 = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR");

        makePostRequest(request1);
        makePostRequest(request2);

        makeGetRequest()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.users[0].firstName").value("Raphael"))
                .andExpect(jsonPath("$.users[1].firstName").value("Matheus"));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        var request1 = new UserRequest("Raphael", "Nask","Nask@mail.com", "123456789", "MODERATOR");
        var request2 = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR");

        makePostRequest(request1);

        var response = makePostRequest(request2)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        makeDeleteRequest(uuid);
        makeGetRequest()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.users[0].firstName").value("Raphael"));
    }

    @Test
    public void shouldReturnNotFoundByDeletingInvalidUser() throws Exception {
        makeDeleteRequest("InvalidUserId").andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnUserById() throws Exception {
        var request = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR");

        var response = makePostRequest(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        makeGetRequestById(uuid)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid))
                .andExpect(jsonPath("$.firstName").value("Matheus"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProfileSent() throws Exception {
        var request = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "INVALID");

        makePostRequest(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("profile")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("Invalid profile name! Options: MODERATOR, ADMINISTRATOR, COMMON")));
    }

    @Test
    public void shouldReturnNotFoundByInvalidId() throws Exception {
        makeGetRequestById("invalidId")
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnBadRequestWhenBodyIsInvalid() throws Exception {
        var userRequest = new UserRequest();

        makePostRequest(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("firstName", "lastName", "email", "password", "profile")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("must not be blank")));
    }

    @Test
    public void shouldReturnBadRequestAndMinimumPasswordSizeWhenLessThan8Characters() throws Exception {
        var userRequest = new UserRequest("Raphael", "Nask","Nask@mail.com", "123", "MODERATOR");

        makePostRequest(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].error").value("size must be between 8 and 32"));
    }

    @Test
    public void shouldReturnBadRequestAndMessageWhenEmailIsInvalid() throws Exception {
        var userRequest = new UserRequest("Raphael", "Nask","invalidemail", "123456789", "MODERATOR");


        makePostRequest(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].error").value("must be a well-formed email address"));
    }

    private ResultActions makePostRequest(Object payload) throws Exception {
        return mockMvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload))
                );
    }

    private ResultActions makeGetRequest() throws Exception {
        return mockMvc
                .perform(get("/users").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions makeGetRequestById(String uuid) throws Exception {
        return mockMvc
                .perform(get("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }

    private ResultActions makeDeleteRequest(String uuid) throws Exception {
        return mockMvc
                .perform(delete("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));
    }
}
