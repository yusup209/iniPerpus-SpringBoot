package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.User;
import com.kkp.iniperpus.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Page mappings are provided by BaseController to avoid duplicate routes

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String fullName, Model model) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setFullName(fullName);
        try {
            userService.create(u);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}
