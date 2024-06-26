package org.example.gradpathway.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.example.gradpathway.DTO.RegisterDTO;
import org.example.gradpathway.DTO.UserResDTO;
import org.example.gradpathway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO registerDTO) {
        if(userService.userExists(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        userService.addUser(registerDTO);
        return ResponseEntity.ok("New User Registered");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("User Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        UserResDTO user;
        try {
            user = userService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addCompany/{companyId}")
    public ResponseEntity<String> addCompanyToUser(@PathVariable int companyId) {
        try {
            userService.addCompanyToUser(companyId);
            return ResponseEntity.ok("Company added to user");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getMyData() {
        try{
            return ResponseEntity.ok(userService.getMyData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login Successful");
    }
//
}
