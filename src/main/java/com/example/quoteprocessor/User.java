package com.example.quoteprocessor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="webuser")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @JsonProperty("name")
    @Size(min = 6, max = 32) @NotNull @NotEmpty
    private String name;

    @JsonProperty(value = "email", access = JsonProperty.Access.WRITE_ONLY)
    @Email @NotNull @NotEmpty
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, max = 32) @NotNull @NotEmpty
    private String password;
    @JsonProperty(value = "created_time", access = JsonProperty.Access.READ_ONLY)
    private Instant createdTime;

    @JsonProperty(value = "landingPage", access = JsonProperty.Access.READ_ONLY)
    private String landingPage;

    public User(String name, String email, String password, String landingPage) {
        this(null,name, email, password, Instant.now().truncatedTo(ChronoUnit.SECONDS), landingPage);
    }

    public void initCreatedDate() {
        createdTime = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
