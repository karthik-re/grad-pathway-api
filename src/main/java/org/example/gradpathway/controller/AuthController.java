package org.example.gradpathway.controller;

import org.example.gradpathway.DTO.RegisterDTO;
import org.example.gradpathway.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if(userService.userExists(registerDTO.getEmail())) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        userService.addUser(registerDTO);
        return ResponseEntity.ok("New User Registered");
    }

    //implement login logic
//    @GetMapping("/login")
//    public ResponseEntity<String> login() {
//        return ResponseEntity.ok("User Logged In");
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
