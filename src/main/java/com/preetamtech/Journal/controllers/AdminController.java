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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {


    @Autowired
    private UserService userService;

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/all-users")
    public <T> ResponseEntity<T> getAllUsers () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User adminUser = userService.findByUserName(userName);

        if(adminUser != null) {
            List<User> allUsers = userService.getAllUserEntry();
            return (ResponseEntity<T>) new ResponseEntity<>(allUsers, HttpStatus.FOUND);
        }
        return (ResponseEntity<T>) new ResponseEntity<>("Not an admin",HttpStatus.FORBIDDEN);
    }

    @GetMapping("/all-journals")
    public <T> ResponseEntity<T> getAllJournal () {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User adminUser = userService.findByUserName(userName);

        if(adminUser != null) {
            List<JournalEntry> allJournalEntries = journalEntryService.getAllJournalEntry();
            return (ResponseEntity<T>) new ResponseEntity<>(allJournalEntries, HttpStatus.FOUND);
        }
        return (ResponseEntity<T>) new ResponseEntity<>("Not an admin",HttpStatus.FORBIDDEN);
    }
}
