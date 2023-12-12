package com.example.quoteprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class QuoteRepositoryTest {

    @Autowired private QuoteRepository repository;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(repository);
    }

    @Test
    void testFindByQuoteId() {
        // there is the predefined set of samples in the H2 database
        // see the data.sql script for details
        assertTrue(repository.findById(1).isPresent());
    }

    @Test
    void testGetRandomQuote() {
        assertTrue(repository.getRandomQuote().isPresent());
    }

    @Test
    void testGetRTop10Quotes() {
        assertEquals(10, repository.getTop10RatedQuotes().size());
    }

    @Test
    void testGetWorst10Quotes() {
        assertEquals(10, repository.getWorst10RatedQuotes().size());
    }


}
