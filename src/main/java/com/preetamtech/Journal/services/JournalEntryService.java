package com.preetamtech.Journal.services;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.repositories.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry>getAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalEntry(String id) {
        journalEntryRepository.deleteById(id);
    }

    public void updateJournalEntry(String id, JournalEntry newEntry) {
        Optional<JournalEntry> optionalOldEntry = journalEntryRepository.findById(id);

        if (optionalOldEntry.isPresent()) {
            JournalEntry oldEntry = optionalOldEntry.get();

            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty() &&
                    !newEntry.getContent().equals(oldEntry.getContent())) {
                oldEntry.setContent(newEntry.getContent());
            }

            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() &&
                    !newEntry.getTitle().equals(oldEntry.getTitle())) {
                oldEntry.setTitle(newEntry.getTitle());
            }

            journalEntryRepository.save(oldEntry);
        }
    }
}
