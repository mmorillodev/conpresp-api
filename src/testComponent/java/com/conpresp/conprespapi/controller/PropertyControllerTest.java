package com.conpresp.conprespapi.controller;

import com.conpresp.conprespapi.ComponentTest;
import com.conpresp.conprespapi.MockMvcTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@ComponentTest
@WithMockUser(authorities = {"ADMINISTRATOR"})
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private MockMvcTestBuilder userMockMvc;

    @BeforeEach
    void setup() {
        this.userMockMvc = new MockMvcTestBuilder("/users", mockMvc);
    }

    @Test
    void shouldCreateAProperty() throws Exception {

    }
}
