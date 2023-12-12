package com.example.quoteprocessor;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

@RestController
@AllArgsConstructor
public class VoteController extends CommonController {

    private final VoteService voteService;

   @PostMapping(value = "/vote", consumes = "application/json")
    public ResponseEntity<?> postVote(@RequestBody Vote vote) {
       voteService.saveVote(vote, 1);
        return ResponseEntity.ok("Voted successfully");
    }

    @PostMapping(value = "/devote", consumes = "application/json")
    public ResponseEntity<?> postDeVote(@RequestBody Vote vote) {
        voteService.saveVote(vote, -1);
        return ResponseEntity.ok("Voted successfully");
    }

    @ResponseBody
    @GetMapping(path ="/votes", produces="application/json")
    public Iterable<Vote> getAllQuotes() { return voteService.findAll(); }

    @ResponseBody
    @GetMapping(path ="/votes/quote/{id}", produces="application/json")
    public Iterable<Vote> getAllVotesByQuoteId(@PathVariable("id") Integer id) {

       var votes = voteService.findByQuoteId(id);
       votes.sort(Comparator.comparing(Vote::getSet_time));
       int x = 0;
       for (var vote : votes) {
           vote.setRate(vote.getRate() + x);
           x = vote.getRate();
       }
       return votes;
   }

    @ResponseBody
    @GetMapping(path ="/votes/rmByQuote/{id}", produces="application/json")
    @Transactional
    public ResponseEntity<String> removeQuotesByQuoteId(@PathVariable("id") Integer id) {
       voteService.deleteByQuoteId(id);
        return ResponseEntity.ok("Quotes were removed successfully");
   }
}
