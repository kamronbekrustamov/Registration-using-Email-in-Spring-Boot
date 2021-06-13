package com.kamronbek.auth.controller;

import com.kamronbek.auth.model.RegistrationRequest;
import com.kamronbek.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup/")
    public String registerUser(@Valid @RequestBody RegistrationRequest request) {
        return userService.signUp(request);
    }

    @GetMapping("/confirm/")
    public String activateUser(@RequestParam String token) {
        userService.activateUser(token);
        return "confirmed";
    }
}
