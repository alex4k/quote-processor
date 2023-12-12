package com.example.quoteprocessor;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path="/quotes",
        produces="application/json")
public class QuoteController extends CommonController {

    @NonNull
    private final QuoteService quoteService;

    @ResponseBody
    @GetMapping(produces="application/json")
    public Iterable<Quote> getAllQuotes() { return quoteService.findAll(); }

    @ResponseBody
    @GetMapping("{id}")
    public Quote getQuote(@PathVariable("id") Integer id) {
        return quoteService.getQuoteById(id);
    }

    @ResponseBody
    @GetMapping("random")
    public Optional<Quote> getRandom() {
        return quoteService.getRandom();
    }

    @ResponseBody
    @GetMapping("top10")
    public List<Quote> getTop10() {
        return quoteService.getTop10Quotes();
    }

    @ResponseBody
    @GetMapping("worst10")
    public List<Quote> getWorst10() {
        return quoteService.getWorst10Quotes();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Quote postQuote(@RequestBody Quote quote) {
        return quoteService.saveQuote(quote);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Quote putQuote(@PathVariable("id") Integer id, @RequestBody Quote quote) {
        return quoteService.updateQuoteForContent(id, quote);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteQuote(@PathVariable("id") Integer id) {
        quoteService.deleteQuoteById(id);
        return ResponseEntity.ok("Quote removed successfully");
    }
}
