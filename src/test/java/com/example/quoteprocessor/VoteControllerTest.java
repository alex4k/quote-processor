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
public class VoteControllerTest {

    // there is the predefined set of samples in the H2 database
    // see the data.sql script for details
    // all tests are running against that data

    @Autowired private MockMvc mvc;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(mvc);
    }

    @Test
    public void makeVoteThroughREST() throws Exception {

        String makeVoteBody = "{\"userId\":21,\"quoteId\":1}";

        mvc.perform(post("/vote")
                        .content(makeVoteBody)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Voted successfully")));

        mvc.perform(get("/votes/quote/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((jsonPath("$", hasSize(19))));

    }

    @Test
    public void makeDeVoteThroughREST() throws Exception {

        String makeVoteBody = "{\"userId\":22,\"quoteId\":1}";

        mvc.perform(post("/vote")
                        .content(makeVoteBody)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Voted successfully")));

        mvc.perform(get("/votes/quote/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((jsonPath("$", hasSize(20))));

    }

    @Test
    public void getGraphVotesForQuoteThroughREST() throws Exception {

        mvc.perform(get("/votes/quote/1").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect((jsonPath("$", hasSize(18))))
                .andExpect(jsonPath("$[0].quoteId", equalTo(1)))
                .andExpect(jsonPath("$[0].rate", equalTo(1)))
                .andExpect(jsonPath("$[1].quoteId", equalTo(1)))
                .andExpect(jsonPath("$[1].rate", equalTo(2)));

    }

}
