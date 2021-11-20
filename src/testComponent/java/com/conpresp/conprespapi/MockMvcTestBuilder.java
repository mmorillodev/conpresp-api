package com.conpresp.conprespapi;

import antlr.collections.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;

public class MockMvcTestBuilder {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final String url;
    private final ArrayList<String> pathVars;
    private final HttpHeaders headers;

    public MockMvcTestBuilder(String url, MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.url = url;
        this.objectMapper = new ObjectMapper();
        this.pathVars = new ArrayList<>();
        this.headers = new HttpHeaders();
    }

    public MockMvcTestBuilder appendHeader(Pair<String, String> header) {
        this.headers.put(header.getFirst(), Collections.singletonList(header.getSecond()));
        return this;
    }

    public MockMvcTestBuilder appendPathVar(String pathVar) {
        this.pathVars.add(pathVar);
        return this;
    }

    public ResultActions post(Object content) throws Exception {
        var result = mockMvc.perform(
                MockMvcRequestBuilders.post(url + "/" + String.join("/", pathVars))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                        .headers(headers)
        );

        pathVars.clear();

        return result;
    }

    public ResultActions put(Object content) throws Exception {
        var result = mockMvc.perform(
                MockMvcRequestBuilders.put(url + "/" + String.join("/", pathVars))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                        .headers(headers)
        );

        pathVars.clear();

        return result;
    }

    public ResultActions get() throws Exception {
        var result = mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" + String.join("/", pathVars))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(headers)
        );

        pathVars.clear();

        return result;
    }

    public ResultActions delete() throws Exception {
        var result = mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/" + String.join("/", pathVars))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(headers)
        );

        pathVars.clear();

        return result;
    }
}
