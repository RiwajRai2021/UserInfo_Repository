package com.capstoneproject.fooddeliveryapp.userInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import com.capstoneproject.fooddeliveryapp.userInfo.model.Users;
import com.capstoneproject.fooddeliveryapp.userInfo.model.UserLogin;
import com.capstoneproject.fooddeliveryapp.userInfo.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Users user) {
        Map<String, String> response = new HashMap<>();
        try {
            Users registeredUser = userService.registerUser(user);
            if (registeredUser != null) {
                response.put("message", "Registration successful");
                return ResponseEntity.ok(response);
            } else {
                response.put("error", "Registration failed");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            response.put("error", "Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLogin user, HttpSession session) throws Exception {
        Map<String, String> response = new HashMap<>();
        Users existingUser = userService.findByUserName(user.getUsername());
        if (existingUser != null) {
            String token = userService.generateToke(user.getUsername());
        	session.setAttribute("user", existingUser);
        	response.put("name", existingUser.getUserName());
        	response.put("role", existingUser.getRole());
            response.put("message", "Login successful");
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    
    

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }
}