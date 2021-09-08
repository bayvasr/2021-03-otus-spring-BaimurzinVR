package ru.otus.mylibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RestControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected String getBaseUrlTemplate() {
        return "";
    }

    protected MockHttpServletRequestBuilder getRequestBuilder(String s) {
        return get(getBaseUrlTemplate() + s).contentType(MediaType.APPLICATION_JSON);
    }

    protected MockHttpServletRequestBuilder postRequestBuilder(String s, String content) {
        return post(getBaseUrlTemplate() + s).contentType(MediaType.APPLICATION_JSON).content(content);
    }

    protected MockHttpServletRequestBuilder putRequestBuilder(String s, String content) {
        return put(getBaseUrlTemplate() + s).contentType(MediaType.APPLICATION_JSON).content(content);
    }

    protected MockHttpServletRequestBuilder deleteRequestBuilder(String s) {
        return delete(getBaseUrlTemplate() + s).contentType(MediaType.APPLICATION_JSON);
    }
}
