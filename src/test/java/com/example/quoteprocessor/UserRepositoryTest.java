package com.example.quoteprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired private UserRepository repository;

    @Test
    void injectedComponentIsNotNull(){
        assertNotNull(repository);
    }

    @Test
    void findAllUsersInRepository() {
        // there is the predefined set of samples in the H2 database
        // see the data.sql script for details
        // there are 39 users in the sample database
        assertEquals(39, repository.findAll().size());
    }

    @Test
    void findAllUsersById() {
       assertTrue(repository.findById(1).isPresent());
    }

}
