package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import com.conpresp.conprespapi.dto.AuthRequest;
import com.conpresp.conprespapi.dto.UserCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentTest
@WithMockUser(authorities = {"ADMINISTRATOR"})
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MockMvcTestBuilder userMockMvc;
    private MockMvcTestBuilder authMockMvc;

    @BeforeEach
    void setup() {
        userMockMvc = new MockMvcTestBuilder("/users", mockMvc);
        authMockMvc = new MockMvcTestBuilder("/auth", mockMvc);
    }

    @Test
    void shouldReturnABearerTokenWhenValidAuthEntries() throws Exception {
        insertAUser();

        AuthRequest request = new AuthRequest("Nask@mail.com", "123456789");

        authMockMvc.post(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void shouldReturnUnauthorizedWhenBadCredentials() throws Exception {
        AuthRequest request = new AuthRequest("mail@mail.com", "122345634");

        authMockMvc.post(request)
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.cause").value("Bad credentials"));
    }

    private void insertAUser() throws Exception {
        var  request = new UserCreateRequest("Raphael", "Nask","Nask@mail.com", "123456789", "MODERATOR", "UAM");

        userMockMvc.post(request);
    }
}
