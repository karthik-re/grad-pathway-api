package org.example.gradpathway.controller;

import jakarta.validation.Valid;
import org.example.gradpathway.DTO.UserDataDTO;
import org.example.gradpathway.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userdata")
public class UserController {

    private final UserDataService userDataService;

    @Autowired
    public UserController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUserData(@Valid @RequestBody UserDataDTO userDataDTO) {
        try{
            userDataService.addUserData(userDataDTO);
            return ResponseEntity.ok("User data added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserData(@Valid @RequestBody UserDataDTO userDataDTO, @PathVariable int id) {
        try {
            userDataService.updateUserData(userDataDTO, id);
            return ResponseEntity.ok("User data updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserData(@PathVariable int id) {
        try {
            userDataService.getUserDataByUserId(id);
            return ResponseEntity.ok("User data deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDataByUserId(@PathVariable int id) {
        try{
            return ResponseEntity.ok(userDataService.getUserDataByUserId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
