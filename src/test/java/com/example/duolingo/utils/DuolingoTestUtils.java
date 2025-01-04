package com.example.duolingo.utils;

import com.example.duolingo.domain.entities.User;

import java.util.Optional;

public class DuolingoTestUtils {
    public static Optional<User> mockNewUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice Maria");
        user.setPass("alice");
        user.setUsername("alice");

        return Optional.of(user);
    }
}
