package com.example.quoteprocessor;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path="/users",
        produces="application/json")
public class UserController extends CommonController {
    @NonNull
    private final UserService userService;

    @ResponseBody
    @GetMapping(produces="application/json")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ResponseBody
    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

}
