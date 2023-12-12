package com.example.quoteprocessor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;
    private final Validator validator;

    @SneakyThrows
    public UserService(UserRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }
    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return Optional.ofNullable(repository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("User not found : " + id);
        }));
    }

    public User saveUser(User user) {
        if (user.getCreatedTime() == null) {
            user.initCreatedDate();
        }
        //here should be a logic for path generation to the user page
        // now I'm using a constant string
        user.setLandingPage("path_to_user_page");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (violations.size() > 0) {
            throw new ConstraintViolationException(violations);
        }
        return repository.save(user);
    }

}
