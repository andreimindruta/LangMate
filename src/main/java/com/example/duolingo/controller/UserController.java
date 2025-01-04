package com.example.duolingo.controller;

import com.example.duolingo.controller.payload.request.LoginDto;
import com.example.duolingo.controller.payload.request.RegisterDto;
import com.example.duolingo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duolingo/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDto loginDto) {
        String jwt = userService.processLogin(loginDto);
        return ResponseEntity.ok().body(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto registerDto) {
        userService.register(registerDto);
        return ResponseEntity.ok().body("User registered successfully");
    }



}
