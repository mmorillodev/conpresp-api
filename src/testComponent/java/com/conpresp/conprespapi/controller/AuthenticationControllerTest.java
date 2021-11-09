package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.dto.AuthRequest;
import com.conpresp.conprespapi.dto.UserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnABearerTokenWhenValidAuthEntries() throws Exception {
        insertAUser();

        AuthRequest request = new AuthRequest("mail@mail.com", "122345634");

        makePostRequest(request, "/auth")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.token").exists());
    }

    private void insertAUser() throws Exception {
        var request = new UserRequest("Nask", "mail@mail.com", "122345634");

        makePostRequest(request, "/users");
    }

    private ResultActions makePostRequest(Object payload, String resourcePath) throws Exception {
        return mockMvc
                .perform(
                        post(resourcePath)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payload))
                );
    }
}
