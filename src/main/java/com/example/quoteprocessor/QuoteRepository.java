package com.example.quoteprocessor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

    @Query(value = "SELECT * FROM QUOTE ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Quote> getRandomQuote();

    @Query(value = "SELECT * FROM QUOTE ORDER BY total_votes DESC LIMIT 10", nativeQuery = true)
    List<Quote> getTop10RatedQuotes();

    @Query(value = "SELECT * FROM QUOTE ORDER BY total_votes ASC LIMIT 10", nativeQuery = true)
    List<Quote> getWorst10RatedQuotes();
}
