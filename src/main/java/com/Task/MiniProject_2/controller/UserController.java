package com.Task.MiniProject_2.controller;

import com.Task.MiniProject_2.entity.securityEntity.User;
import com.Task.MiniProject_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password,
                               Model model) {
        try {
            userService.registerUser(username, email, password);
            return "redirect:/login"; // Redirect to login page after successful registration
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // Return to register page if there is an error
        }
    }
}
