package com.preetamtech.Journal.services;

import com.preetamtech.Journal.entity.JournalEntry;
import com.preetamtech.Journal.entity.User;
import com.preetamtech.Journal.repositories.JournalEntryRepository;
import com.preetamtech.Journal.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    //Achieve atomicity....
    @Transactional
    public JournalEntry saveJournalEntry(JournalEntry journalEntry, String userName) {
            User user = userService.findByUserName(userName);
            if (user == null) {
                // Handle case where user is not found, e.g., throw a custom exception
                throw new RuntimeException("User not found with username: " + userName);
            }
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntryList().add(savedJournalEntry);
            userService.saveUserEntry(user);
            return savedJournalEntry; // <-- Return the saved entity
    }

    public List<JournalEntry>getAllJournalEntry() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(String id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalEntry(String id) {
        journalEntryRepository.deleteById(String.valueOf(id));
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
