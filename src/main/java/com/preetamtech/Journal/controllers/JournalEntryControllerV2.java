package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.repositories.JournalEntryRepository;
import com.preetamtech.Journal.services.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry>getAllJournals() {
        return journalEntryService.getAllJournalEntry();
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry journalEntry) {
        journalEntryService.saveJournalEntry(journalEntry);
        return true;
    }

    @GetMapping("/{Id}")
    public Optional<JournalEntry> getJournalEntryById(@PathVariable String Id) {
        return journalEntryService.getJournalEntryById(Id);
    }

    @DeleteMapping("/{Id}")
    public boolean deleteJournalEntry(@PathVariable String Id) {
        journalEntryService.deleteJournalEntry(Id);
        return true;
    }

    @PutMapping("/{Id}")
    public boolean updateJournalEntry(@PathVariable String Id, @RequestBody JournalEntry updatedEntry) {
        journalEntryService.updateJournalEntry(Id, updatedEntry);
        return true;
    }
}
