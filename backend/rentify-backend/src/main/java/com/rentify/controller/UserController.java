package com.rentify.controller;

import com.rentify.model.Users;
import com.rentify.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/api/auth/registerNewUser")
    public Users registerUser(@RequestBody Users users)
    {
        return userService.registerNewUser(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(
            @PathVariable Long id,
            @RequestBody Users userRequest) {
        Users updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }


}
