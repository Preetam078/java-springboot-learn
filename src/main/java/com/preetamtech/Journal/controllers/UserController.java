package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers () {
        return userService.getAllUserEntry();
    }

    @PostMapping
    public void createNewUser (@RequestBody User newUser) {
        userService.saveUserEntry(newUser);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser (@RequestBody User updatedUser, @PathVariable String userName) {
        User userInDB = userService.findByUserName(userName);
        if(userInDB != null) {
            userInDB.setUsername(updatedUser.getUsername());
            userInDB.setPassword(updatedUser.getPassword());
            userService.saveUserEntry(userInDB);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUserByName(@PathVariable String userName) {
        User deletedUser = userService.findByUserName(userName);
        if(deletedUser != null) {
            userService.deleteUserEntry(deletedUser.getId());
            return new ResponseEntity<>(deletedUser, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
