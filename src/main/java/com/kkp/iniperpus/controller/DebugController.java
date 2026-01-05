package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DebugController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/api/debug/check-password")
    public String checkPassword(@RequestParam String username, @RequestParam String rawPassword) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found: " + username;
        }
        
        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
        return "Username: " + user.getUsername() + "\n" +
               "Full Name: " + user.getFullName() + "\n" +
               "Password Hash: " + user.getPassword() + "\n" +
               "Raw Password: " + rawPassword + "\n" +
               "Matches: " + matches + "\n" +
               "Roles: " + (user.getRoles() != null ? user.getRoles().size() : "null");
    }
}
