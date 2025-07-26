package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.services.JournalEntryService;
import com.preetamtech.Journal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @PutMapping
    public ResponseEntity<?> updateUser (@RequestBody User updatedUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User userInDB = userService.findByUserName(userName);
        if(userInDB != null) {
            userInDB.setUsername(updatedUser.getUsername());
            userInDB.setPassword(updatedUser.getPassword());
            userService.saveUserEntryV2(userInDB);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User deletedUser = userService.findByUserName(userName);

        if(deletedUser != null) {
            List <JournalEntry> allJournalEntries = deletedUser.getJournalEntryList();
            allJournalEntries.forEach(journalEntry -> journalEntryService.deleteJournalEntry(String.valueOf(journalEntry.getId())));
            userService.deleteUserEntry(deletedUser.getId());
            return new ResponseEntity<>(deletedUser, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
