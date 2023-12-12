package com.example.quoteprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VotesRepositoryTest {

    @Autowired private VotesRepository repository;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(repository);
    }

    @Test
    void testFindByQuoteId() {
        // there is the predefined set of samples in the H2 database
        // see the data.sql script for details
        assertEquals(2, repository.findByQuoteId(2).size());
        assertEquals(18, repository.findByQuoteId(1).size());
    }

    @Test
    void deleteByQuoteId() {
        repository.deleteByQuoteId(2);
        var quote = repository.findByQuoteId(2);
        assertNotNull(quote);
        assertEquals(0, quote.size());
    }
}
