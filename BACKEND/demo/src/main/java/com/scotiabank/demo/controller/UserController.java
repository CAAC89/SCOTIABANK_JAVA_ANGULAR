package com.scotiabank.demo.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scotiabank.demo.model.User;
import com.scotiabank.demo.service.UserService;
import com.scotiabank.demo.util.JwtUtil;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<User>> getUsers() {
        logger.info("Fetching all users");
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        logger.info("Fetching user with id: {}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User with id {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        logger.info("Fetching user with email: {}", email);
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("User with id {} not found", email);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("Attempting to register user: {}", user.getEmail());
        try {
            User savedUser = userService.createUser(user);
            logger.info("User registered successfully: {}", savedUser.getEmail());
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            logger.error("Error registering user: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        logger.info("Attempting login for email: {}", loginUser.getEmail());
        Optional<User> user = userService.findByEmail(loginUser.getEmail());

        if (user.isPresent() && passwordEncoder.matches(loginUser.getPassword(), user.get().getPassword())) {
            String token = jwtUtil.generateToken(user.get().getEmail());
            logger.info("Login successful for email: {}", loginUser.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            logger.warn("Invalid credentials for email: {}", loginUser.getEmail());
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        logger.info("Attempting to delete user with id: {}", id);
        userService.deleteUser(id);
        logger.info("User with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/email/{email}")
    public ResponseEntity<?> updateUserByEmail(@PathVariable String email, @RequestBody User updatedUser) {
        logger.info("Attempting to update user with email: {}", email);
        try {
            User user = userService.updateUser(email, updatedUser);
            logger.info("User with email {} updated successfully", email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            logger.error("Error updating user with email {}: {}", email, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}