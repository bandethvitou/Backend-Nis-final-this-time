package com.sarana_NIS.newTown_backend.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/public/debug")
public class DebugController {

    private final PasswordEncoder passwordEncoder;

    public DebugController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hash")
    public Map<String, Object> hash(@RequestParam String password) {
        String hash = passwordEncoder.encode(password);
        return Map.of(
                "password", password,
                "hash", hash
        );
    }

    @GetMapping("/verify")
    public Map<String, Object> verify(
            @RequestParam String password,
            @RequestParam String hash) {
        return Map.of(
                "password", password,
                "hash", hash,
                "matches", passwordEncoder.matches(password, hash)
        );
    }
}