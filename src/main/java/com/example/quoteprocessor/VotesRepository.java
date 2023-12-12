package com.example.quoteprocessor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VotesRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findByQuoteId(Integer quoteId);
    void deleteByQuoteId(Integer quoteId);

}
