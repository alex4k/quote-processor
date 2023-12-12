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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = QuoteProcessorApplication.class)
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
public class QuoteControllerTest {

    // there is the predefined set of samples in the H2 database
    // see the data.sql script for details
    // all tests are running against that data

    @Autowired private MockMvc mvc;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(mvc);
    }

    @Test
    public void getAllQuotesThroughREST() throws Exception {

        mvc.perform(get("/quotes").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quoteId", is(notNullValue())))
                .andExpect(jsonPath("$[0].content", is(notNullValue())))
                .andExpect(jsonPath("$[0].totalVotes", is(notNullValue())));

    }

    @Test
    public void getTop10QuotesThroughREST() throws Exception {

        mvc.perform(get("/quotes/top10").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quoteId", is(notNullValue())))
                .andExpect(jsonPath("$[0].content", is(notNullValue())))
                .andExpect(jsonPath("$[0].totalVotes", is(notNullValue())));

    }

    @Test
    public void getWorst10QuotesThroughREST() throws Exception {

        mvc.perform(get("/quotes/worst10").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].quoteId", is(notNullValue())))
                .andExpect(jsonPath("$[0].content", is(notNullValue())))
                .andExpect(jsonPath("$[0].totalVotes", is(notNullValue())));

    }

    @Test
    public void getQuoteThroughREST() throws Exception {

        mvc.perform(get("/quotes/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.quoteId", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.totalVotes", is(notNullValue())));

    }

    @Test
    public void postQuoteThroughREST() throws Exception {

        String body = "{\"content\":\"The only limit to our realization of tomorrow will be our doubts of today !!!. \"," +
                "\"owner\": { \"userId\":1}}";

        mvc.perform(post("/quotes")
                        .content(body)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quoteId", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.totalVotes", equalTo(0)))
                .andExpect(jsonPath("$.createdTime", is(notNullValue())))
                .andExpect(jsonPath("$.owner", is(notNullValue())))
                .andExpect(jsonPath("$.owner.name", is(notNullValue())))
                .andExpect(jsonPath("$.owner.landingPage", equalTo("path_to_user_page")));


    }

    @Test
    public void putQuoteThroughREST() throws Exception {

        String body = "{\"content\":\"The only limit to our realization of tomorrow will be our doubts of today !!!. \"}";

        mvc.perform(put("/quotes/1")
                        .content(body)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quoteId", is(notNullValue())))
                .andExpect(jsonPath("$.content", is(notNullValue())))
                .andExpect(jsonPath("$.createdTime", is(notNullValue())))
                .andExpect(jsonPath("$.owner", is(notNullValue())))
                .andExpect(jsonPath("$.owner.name", is(notNullValue())))
                .andExpect(jsonPath("$.owner.landingPage", equalTo("path_to_user_page")));


    }
}
