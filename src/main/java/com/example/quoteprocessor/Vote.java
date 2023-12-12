package com.example.quoteprocessor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Integer id;

    @NotNull
    @Column(name = "user_id")
    @JsonProperty(value = "userId")
    private Integer uerId;

    @NotNull
    @Column(name = "quote_id")
    private Integer quoteId;

    @JsonProperty(value = "created_time", access = JsonProperty.Access.READ_ONLY)
    private Instant set_time;

    @Column(name = "rate")
    private Integer rate;
}
