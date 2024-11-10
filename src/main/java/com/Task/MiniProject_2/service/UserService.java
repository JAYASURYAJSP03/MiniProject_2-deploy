package com.Task.MiniProject_2.service;


import com.Task.MiniProject_2.entity.securityEntity.User;
import com.Task.MiniProject_2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Register new user with default role "ROLE_PATIENT"
    public void registerUser(String username, String email, String password) {
        if (userRepository.findByUsernameOrEmail(username, email).isPresent()) {
            throw new IllegalArgumentException("Username or Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        // Set default role as ROLE_PATIENT for new users
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRole("ROLE_PATIENT");

        userRepository.save(newUser);
    }
}

