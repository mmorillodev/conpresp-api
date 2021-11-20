package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.dto.UserRequest;
import com.conpresp.conprespapi.dto.UserUpdateRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        var request = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "MODERATOR", "UAM");

        var response = makePostRequest(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    @Test
    public void shouldReturnAnErrorWhenTryingToInsertAUserWithAExistingEmail() throws Exception {
        var request = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "MODERATOR", "UAM");
        var request2 = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "MODERATOR", "UAM");

        makePostRequest(request);

        makePostRequest(request2)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.cause").value("Duplicate entry"));
    }

    @Test
    public void shouldReturnTheListOfUsers() throws Exception {
        var request1 = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "MODERATOR", "UAM");
        var request2 = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR", "UAM");

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
        var request1 = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123456789", "MODERATOR", "UAM");
        var request2 = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR", "UAM");

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
        var request = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "MODERATOR", "UAM");

        var response = makePostRequest(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        makeGetRequestById(uuid)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(uuid))
                .andExpect(jsonPath("$.firstName").value("Matheus"))
                .andExpect(jsonPath("$.userGroup").value("UAM"));
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProfileSent() throws Exception {
        var request = new UserRequest("Matheus", "Morillo", "matheus@mail.com", "1234567890", "INVALID", "UAM");

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
                .andExpect(jsonPath("$..field", Matchers.containsInAnyOrder("firstName", "lastName", "email", "password", "profile", "userGroup")))
                .andExpect(jsonPath("$..error", Matchers.hasItem("must not be blank")));
    }

    @Test
    public void shouldReturnBadRequestAndMinimumPasswordSizeWhenLessThan8Characters() throws Exception {
        var userRequest = new UserRequest("Raphael", "Nask", "Nask@mail.com", "123", "MODERATOR", "UAM");

        makePostRequest(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("password"))
                .andExpect(jsonPath("$[0].error").value("size must be between 8 and 32"));
    }

    @Test
    public void shouldReturnBadRequestAndMessageWhenEmailIsInvalid() throws Exception {
        var userRequest = new UserRequest("Raphael", "Nask", "invalidemail", "123456789", "MODERATOR", "UAM");


        makePostRequest(userRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].field").value("email"))
                .andExpect(jsonPath("$[0].error").value("must be a well-formed email address"));
    }

    @Test
    public void shouldUpdateAUser() throws Exception {
        var userRequest = new UserRequest("Raphael", "Nask", "Nask@gmail.com", "123456789", "MODERATOR", "UAM");

        var response = makePostRequest(userRequest).andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        var userUpdateRequest = new UserUpdateRequest("Rodrigo", "Nascimento", "Nask@Hotmail.com");

        makePutRequest(uuid, userUpdateRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Rodrigo"))
                .andExpect(jsonPath("$.lastName").value("Nascimento"))
                .andExpect(jsonPath("$.email").value("Nask@Hotmail.com"))
                .andExpect(jsonPath("$.updated_at").exists()).andDo(print());

    }

    @Test
    public void shoudReturnNotFoundWhenTryingUpdateAnNonexistentUser() throws Exception {
        var userUpdateRequest = new UserUpdateRequest("Rodrigo", "Nascimento", "Nask@Hotmail.com");

        makePutRequest("InvalidId", userUpdateRequest)
                .andExpect(status().isNotFound());
    }

    private <T> T decodeJSON(String content, Class<T> target) throws JsonProcessingException {
        return objectMapper.readValue(content, target);
    }

    private ResultActions makePostRequest(Object payload) throws Exception {
        return makePostRequest(payload, "/users");
    }

    private ResultActions makePostRequest(Object payload, String url) throws Exception {
        return mockMvc
                .perform(
                        post(url)
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

    private ResultActions makePutRequest(String uuid, Object payload) throws Exception {
        return mockMvc
                .perform(put("/users/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)));
    }
}
