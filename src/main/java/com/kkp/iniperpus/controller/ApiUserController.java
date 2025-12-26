package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private final UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> list() {
        List<User> users = userService.findAll();
        // sanitize password before serialization
        users.forEach(u -> u.setPassword(null));
        return users;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User payload) {
        try {
            var saved = userService.create(payload);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }
}
