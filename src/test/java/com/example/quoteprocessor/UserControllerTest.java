package com.example.quoteprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = QuoteProcessorApplication.class)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
public class UserControllerTest {

    // there is the predefined set of samples in the H2 database
    // see the data.sql script for details
    // all tests are running against that data

    @Autowired private MockMvc mvc;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(mvc);
    }

    @Test
    public void getAllUsersThroughREST() throws Exception {

        mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].userId", equalTo(1)))
                .andExpect(jsonPath("$[0].name", equalTo("BlazeRider")))
                .andExpect(jsonPath("$[0].landingPage", equalTo("path_to_user_page")));

    }

    @Test
    public void getUserThroughREST() throws Exception {

        mvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("BlazeRider")))
                .andExpect(jsonPath("$.created_time", is(notNullValue())))
                .andExpect(jsonPath("$.landingPage", equalTo("path_to_user_page")));

    }

    @Test
    public void addUserThroughREST() throws Exception {

        String addUser = "{\"name\":\"testUser\",\"email\":\"test@test.com\",\"password\":\"test_password\"}";

        mvc.perform(post("/users")
                        .content(addUser)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(notNullValue())))
                .andExpect(jsonPath("$.name", equalTo("testUser")))
                .andExpect(jsonPath("$.created_time", is(notNullValue())))
                .andExpect(jsonPath("$.landingPage", equalTo("path_to_user_page")));

    }

    @Test
    public void addUserThroughRESTWithShortName_expectError() throws Exception {

        String addUser = "{\"name\":\"ter\",\"email\":\"test@test.com\",\"password\":\"test_password\"}";

        mvc.perform(post("/users")
                        .content(addUser)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Validation error: name: size must be between 6 and 32")));

    }

    @Test
    public void addUserThroughRESTWithInvalidEmail_expectError() throws Exception {

        String addUser = "{\"name\":\"terex743\",\"email\":\"test.com\",\"password\":\"test_password\"}";

        mvc.perform(post("/users")
                        .content(addUser)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Validation error: email: must be a well-formed email address")));

    }
}