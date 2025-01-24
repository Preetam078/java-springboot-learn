package com.preetamtech.Journal.controllers;

import com.preetamtech.Journal.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private final HashMap<Long, JournalEntry>journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllJournalEntry() {
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    @GetMapping("/{Id}")
    public JournalEntry getJournalEntryById(@PathVariable Long Id) {
        return journalEntries.get(Id);
    }

    @DeleteMapping("/{Id}")
    public boolean deleteJournalEntry(@PathVariable Long Id) {
        journalEntries.remove(Id);
        return true;
    }

    @PutMapping("/{Id}")
    public boolean updateJournalEntry(@PathVariable Long Id, @RequestBody JournalEntry updatedEntry) {
        journalEntries.put(Id, updatedEntry);
        return true;
    }
}
