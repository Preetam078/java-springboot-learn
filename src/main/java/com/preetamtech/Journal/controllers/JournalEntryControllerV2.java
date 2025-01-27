package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.services.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournals() {
        List<JournalEntry>journalEntries = journalEntryService.getAllJournalEntry();
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry journalEntry) {
        journalEntryService.saveJournalEntry(journalEntry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String Id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getJournalEntryById(Id);
        if(journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable String Id) {
        journalEntryService.deleteJournalEntry(Id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable String Id, @RequestBody JournalEntry updatedEntry) {
        journalEntryService.updateJournalEntry(Id, updatedEntry);
        Optional<JournalEntry> newUpdatedEntry = journalEntryService.getJournalEntryById(Id);
        if(newUpdatedEntry.isPresent()) {
            return new ResponseEntity<>(newUpdatedEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
