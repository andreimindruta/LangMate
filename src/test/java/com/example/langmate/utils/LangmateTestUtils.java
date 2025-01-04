package com.example.langmate.utils;

import com.example.langmate.domain.entities.User;

import java.util.Optional;

public class LangmateTestUtils {
    public static Optional<User> mockNewUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Alice Maria");
        user.setPass("alice");
        user.setUsername("alice");

        return Optional.of(user);
    }
}
