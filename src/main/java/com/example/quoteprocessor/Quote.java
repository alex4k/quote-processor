package com.example.quoteprocessor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "QUOTE")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quote_id")
    private Integer quoteId;

    @Size(max = 1024)
    @NotNull @NotEmpty
    private String content;
    private Instant createdTime;

    @Column(name = "total_votes")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer totalVotes;

    @Column(name = "last_voted")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant lastVoted;

    @ManyToOne
    @NotNull
    @JoinColumn(name="user_id")
    @JsonProperty("owner")
    @JsonIgnoreProperties({"created_time"})
    private User user;



}
