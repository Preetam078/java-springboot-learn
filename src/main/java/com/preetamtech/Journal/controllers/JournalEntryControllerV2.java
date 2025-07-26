package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.services.JournalEntryService;
import com.preetamtech.Journal.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User activeUser = userService.findByUserName(userName);

        if(activeUser != null) {
            List<JournalEntry> allJournalEntries = activeUser.getJournalEntryList();
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // --- POST method ---

    @PostMapping
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry journalEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        JournalEntry savedEntry = journalEntryService.saveJournalEntry(journalEntry, userName);
        return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
    }


    // --- DELETE method ---

    @DeleteMapping("/{id}/{userName}") // **CHANGED PATH**
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String id, @PathVariable String userName) {
        User activeUser = userService.findByUserName(userName);
        if(activeUser != null) {
            activeUser.getJournalEntryList().removeIf(journalEntry -> journalEntry.getId().equals(id));
            userService.saveUserEntry(activeUser);
            journalEntryService.deleteJournalEntry(id);
            return new ResponseEntity<>(HttpStatus.GONE);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // --- PUT method ---

    @PutMapping("/{id}/{userName}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String id, @RequestBody JournalEntry updatedEntry, @PathVariable String userName) {
        try {
            User currentUser = userService.findByUserName(userName);
            if(currentUser != null) {
                journalEntryService.updateJournalEntry(id, updatedEntry);
                Optional<JournalEntry> newUpdatedEntry = journalEntryService.getJournalEntryById(id);
                if (newUpdatedEntry.isPresent()) {
                    return new ResponseEntity<>(newUpdatedEntry.get(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>("An error occurred while deleting the journal entry: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}