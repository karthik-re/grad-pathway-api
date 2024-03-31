package org.example.gradpathway.controller;

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
    public ResponseEntity<String> addUserData(@RequestBody UserDataDTO userDataDTO) {
        userDataService.addUserData(userDataDTO);
        return ResponseEntity.ok("User data added successfully");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUserData(@RequestBody UserDataDTO userDataDTO, @PathVariable int id) {
        userDataService.updateUserData(userDataDTO, id);
        return ResponseEntity.ok("User data updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserData(@PathVariable int id) {
        userDataService.deleteUserData(id);
        return ResponseEntity.ok("User data deleted successfully");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserDataByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userDataService.getUserDataByUserId(id));
    }



}
