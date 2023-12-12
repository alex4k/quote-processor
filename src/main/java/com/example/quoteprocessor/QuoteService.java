package com.example.quoteprocessor;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuoteService {

    private final QuoteRepository repository;
    private final UserRepository userRepository;
    private final VotesRepository votesRepository;


    private final Validator validator;

    public QuoteService(QuoteRepository repository, UserRepository userRepository, VotesRepository votesRepository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
        this.userRepository = userRepository;
        this.votesRepository = votesRepository;
    }

    public Quote getQuoteById(Integer id) {
        return repository.findById(id).orElseThrow( ()-> {
            throw new NotFoundException("Quote is not found : " + id);
        });
    }

    public List<Quote> findAll() {
        return repository.findAll();
    }

    public Quote saveQuote(Quote quote) {

        Integer userId = quote.getUser().getUserId();
        var user = userRepository.findById(userId).orElseThrow(() -> {
                    throw new NotFoundException("User is not found : " + userId);
        });
        quote.setCreatedTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        quote.setLastVoted(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        quote.setTotalVotes(0);
        quote.setUser(user);
        return checkViolationsAndSaveQuote(quote);
    }

    public Optional<Quote> getRandom() {
        return repository.getRandomQuote();
    }

    public List<Quote> getTop10Quotes() {
        return repository.getTop10RatedQuotes();
    }

    public List<Quote> getWorst10Quotes() {
        return repository.getWorst10RatedQuotes();
    }

    @Transactional
    public void deleteQuoteById(Integer id) {
        if (repository.existsById(id)) {
            votesRepository.deleteByQuoteId(id);
            repository.deleteById(id);
        } else
            throw new NotFoundException("Quote is not found : " + id);
    }

    public Quote updateQuoteForContent(Integer id, Quote updatedQuote) {

        var quote = repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Quote is not found : " + id);
        });
        quote.setContent(updatedQuote.getContent());
        quote.setCreatedTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        return checkViolationsAndSaveQuote(quote);
    }

    private Quote checkViolationsAndSaveQuote(Quote quote) {
        Set<ConstraintViolation<Quote>> violations = validator.validate(quote);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        return repository.save(quote);
    }
}
