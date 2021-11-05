package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldCreateAUserAndReturnCreatedHTTPCodeAlongWithALocationHeader() throws Exception {
        var request = new UserRequest("name", "email@mail.com", "password");

        var response = makeRequest(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"))
                .andReturn();

        var location = response.getResponse().getHeader("location");
        var uuid = location.substring(location.lastIndexOf("/") + 1);

        assertDoesNotThrow(() -> UUID.fromString(uuid));
    }

    private ResultActions makeRequest(Object payload) throws Exception {
        return mockMvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload)
                                )
                );
    }
}
