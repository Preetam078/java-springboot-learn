package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }

    @GetMapping("/user")
    public List<User> getAllUsers () {
        return userService.getAllUserEntry();
    }

    @PostMapping("/user")
    public void createNewUser (@RequestBody User newUser) {
        userService.saveUserEntryV2(newUser);
    }
}
