package com.example.quoteprocessor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@Service
public class VoteService {

    private final VotesRepository votesRepository;
    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;
    private final Validator validator;


    public VoteService( VotesRepository historicalVoteRepo,
                       QuoteRepository quoteRepo, UserRepository userRepo, Validator validator) {

        this.votesRepository = historicalVoteRepo;
        this.quoteRepository = quoteRepo;
        this.userRepository = userRepo;
        this.validator = validator;
    }

    public boolean saveVote(Vote vote, int voteRate ) {
        checkConstrainViolations(vote);
        if (!userRepository.existsById(vote.getUerId())) {
            throw new NotFoundException("User is not found : " + vote.getUerId());
        }

        var votedQuote = quoteRepository.findById(vote.getQuoteId()).orElseThrow(() -> {
            throw new NotFoundException("Quote is not found : " + vote.getQuoteId());
        });
        if (votesRepository.existsById(vote.getUerId())) {
            // the user has already voted, this vote will be ignored
            return false;
        }
        vote.setSet_time(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        vote.setRate(voteRate);

        //check constrain violations for the vote instance
        checkConstrainViolations(vote);

        //save the vote to the historical vote table
        votesRepository.save(vote);

        // summarize all votes regarding the selected quote and save it inside the quote
        votedQuote.setLastVoted(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        votedQuote.setTotalVotes(votedQuote.getTotalVotes() + voteRate);
        quoteRepository.save(votedQuote);

        return true;

    }

    public List<Vote> findAll() {
        return votesRepository.findAll();
    }

    public List<Vote> findByQuoteId(Integer quoteId) {
        return votesRepository.findByQuoteId(quoteId);
    }

    public void deleteByQuoteId(Integer quoteId) {
        votesRepository.deleteByQuoteId(quoteId);
    }

    private void checkConstrainViolations(Vote vote) {
        Set<ConstraintViolation<Vote>> violations = validator.validate(vote);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
    }
}

